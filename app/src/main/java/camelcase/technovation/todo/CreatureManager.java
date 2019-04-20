package camelcase.technovation.todo;

import android.content.Context;
import android.database.Cursor;

import java.io.Serializable;
import java.util.HashMap;

//Class for managing creatures.
public class CreatureManager implements Serializable {
    //Has a database helper for the creatures.
    private CreatureHelper creatureHelper;

    //HashMap to store the creatures.
    private HashMap<String, Creature> creatures;

    //Default constructor.
    public CreatureManager(Context context) {
        //Set up variables.
        creatures = new HashMap<>();
        creatureHelper = new CreatureHelper(context);
    }

    //Get all the creatures from the database.
    public void addAllCreatures() {
        Cursor cursor = creatureHelper.getAllData();

        //For each item in the cursor, retrieve the information.
        if(cursor.getCount() != 0) {
            while(cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int cost = cursor.getInt(cursor.getColumnIndex("cost"));
                int status = cursor.getInt(cursor.getColumnIndex("unlocked"));

                //Create a new creature from the information.
                Creature creature = new Creature(name, cost);

                //If the creature is unlocked, set its unlocked value to true.
                if(status == 1) {
                    creature.setUnlocked(true);
                }

                //Add new creature to the HashMap.
                creatures.put(name, creature);
            }
        }
    }

    //Method that returns the hashMap.
    public HashMap<String, Creature> getCreatures() {
        return creatures;
    }

    //Method for storing the creatures into the database.
    public void storeCreatures() {
        creatureHelper.addData("smallFox", 0, true);
        creatureHelper.addData("smallDragon", 0, true);
        creatureHelper.addData("grownFox", 100, false);
        creatureHelper.addData("grownDragon", 100, false);
    }

    //Method for setting the unlocked value of the creatures in the database.
    public void unlockCreature(String name) {
        creatureHelper.unlockCreature(name);
    }

    //Method to check if the database exists.
    public boolean databaseExists() {
        return creatureHelper.rowExists();
    }
}
