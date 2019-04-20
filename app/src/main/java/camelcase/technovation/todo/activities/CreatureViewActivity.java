package camelcase.technovation.todo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import camelcase.technovation.R;

//Activity for viewing the creatures.
public class CreatureViewActivity extends AppCompatActivity {
    //Instantiate activity elements and activities.
    private ImageView selectedCreature;

    //Name received from intent.
    private String creatureName;

    //On create method for the creation of the activity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creature_view);

        //Set up elements.
        selectedCreature = findViewById(R.id.imageView_creature);

        //Get received intent and get the name.
        Intent receivedIntent = getIntent();
        creatureName = receivedIntent.getStringExtra("creatureName");

        //Get image to display.
        getCreatureImage();
    }

    //Method for setting up the correct images.
    private void getCreatureImage() {
        switch (creatureName) {
            case "smallFox":
                selectedCreature.setImageResource(R.mipmap.fox_sprite);
                break;
            case "smallDragon":
                selectedCreature.setImageResource(R.mipmap.dragon_sprite);
                break;
            case "grownFox":
                selectedCreature.setImageResource(R.mipmap.grown_fox_sprite);
                break;
        }
    }
}
