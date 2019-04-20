package camelcase.technovation.todo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import camelcase.technovation.todo.Creature;
import camelcase.technovation.todo.CreatureManager;
import camelcase.technovation.R;

import java.util.HashMap;
import java.util.Map;

//Activity for selecting the creature the user wants displayed.
public class CreatureSelectActivity extends AppCompatActivity {
    //Instantiate activity elements and activities.
    private ImageView smallFox, smallDragon, grownFox, grownDragon;
    private Button go;
    private TextView starCount;
    private CreatureViewActivity creatureView;

    //CreatureManager creates the creatures and stores them into the database.
    private CreatureManager creatureManager;

    //Fields for holding the creatures, the selected creature and amount of stars.
    private String selectedCreature;
    private HashMap<String, Creature> creatures;
    private int stars;

    //On create method for the creation of the activity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creature_select);

        //Set up elements and activities.
        stars = getIntent().getIntExtra("money", 0);

        //https://stackoverflow.com/questions/9468482/resources-notfoundexception
        starCount = findViewById(R.id.star_count);
        starCount.setText(String.valueOf(stars));

        go = findViewById(R.id.btn_see_creature);

        smallFox = findViewById(R.id.image_fox_small);
        grownFox = findViewById(R.id.image_fox_grown);
        smallDragon = findViewById(R.id.image_dragon_small);
        grownDragon = findViewById(R.id.image_dragon_grown);

        creatureView = new CreatureViewActivity();
        creatureManager = new CreatureManager(getApplicationContext());

        //If the database does not exist yet, add the creatures to the database.
        if(!creatureManager.databaseExists()) {
            creatureManager.storeCreatures();
        }

        //Retrieve the creatures from the database.
        creatureManager.addAllCreatures();
        creatures = creatureManager.getCreatures();

        //Set the images for the creatures.
        setCreatureImages();

        //Default the selected creature the baby fox.
        selectedCreature = "smallFox";
    }

    //Method for checking if buttons are clicked.
    public void onClick(View v) {
        int id = v.getId();

        //If clicked ID is the go button, start the activity for viewing the creature.
        if(id == go.getId()) {
            Intent creatureIntent = new Intent(getApplicationContext(), creatureView.getClass());
            //Add the creature's name to the intent.
            creatureIntent.putExtra("creatureName", selectedCreature);

            startActivity(creatureIntent);
        }

        //If the creature is clicked and the creature is unlocked or was always unlocked, change the
        //selected creature to that creature. Toast the user as well.
        if(id == smallFox.getId())
        {
            selectedCreature = "smallFox";
            toastMessage("Your creature will be a baby fox!");
        }
        else if(id == smallDragon.getId())
        {
            selectedCreature = "smallDragon";
            toastMessage("Your creature will be a baby dragon!");
        }
        else if(id == grownFox.getId())
        {
            Creature clickedCreature = creatures.get("grownFox");

            if(clickedCreature.isUnlocked()) {
                selectedCreature = "grownFox";
                toastMessage("Your creature will be an adult fox!");
            }
            //If the creature is locked but the user has the amount of stars needed to unlock it,
            //unlock the creature by changing the image and setting selectedCreature to the newly
            //unlocked creature.
            else if(!clickedCreature.isUnlocked() && stars >= clickedCreature.getCost()) {
                selectedCreature = "grownFox";
                grownFox.setImageResource(R.mipmap.grown_fox_sprite);
                toastMessage("You have unlocked the adult fox!");
                updateStars(clickedCreature.getCost());
                creatureManager.unlockCreature("grownFox");
            }
            //Otherwise, tell the user the amount of stars necessary to unlock the creature.
            else {
                toastMessage("You need " + clickedCreature.getCost() + " stars to unlock this.");
            }
        }
        //If the creature's art is unreleased, tell the user this.
        else if(id == grownDragon.getId())
        {
            toastMessage("This creature is unreleased.");
        }
    }

    //Method for setting the images for the creatures.
    private void setCreatureImages() {
        //Iterate through the HashMap to each creature and depending on the name, change the image.
        //https://javatutorial.net/java-iterate-hashmap-example
        for (Map.Entry<String, Creature> entry : creatures.entrySet()) {
            Creature tempCreature = entry.getValue();

            switch (tempCreature.getName()) {
                case "smallFox":
                    smallFox.setImageResource(R.mipmap.fox_sprite);
                    break;
                case "smallDragon":
                    smallDragon.setImageResource(R.mipmap.dragon_sprite);
                    break;
                case "grownFox":
                    //If the creature was unlocked in the past, change the image to the unlocked one.
                    if(tempCreature.isUnlocked()) {
                        grownFox.setImageResource(R.mipmap.grown_fox_sprite);
                    }
                    //Otherwise, use the default mystery creature image.
                    else {
                        grownFox.setImageResource(R.mipmap.mystery_creature);
                    }
                    break;
                case "grownDragon":
                    grownDragon.setImageResource(R.mipmap.mystery_creature);
                    break;
            }
        }
    }

    //When the user buys a creature, update the amount of stars they have.
    private void updateStars(int cost) {
        stars -= cost;
        starCount.setText(String.valueOf(stars));
    }

    //Message that shows up to the UI.
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
