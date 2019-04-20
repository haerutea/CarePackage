package camelcase.technovation.todo;

//Creature class creates Creature objects.
public class Creature {
    private String name;
    private int cost;
    private boolean unlocked;

    //Constructor needs a name and an unlock cost.
    public Creature(String name, int cost) {
        this.name = name;
        this.cost = cost;
        unlocked = false;
    }

    //Method that gets the name.
    public String getName() {
        return name;
    }

    //Method that sets the name.
    public void setName(String name) {
        this.name = name;
    }

    //Method that gets the cost.
    public int getCost() {
        return cost;
    }

    //Method that sets the cost amount.
    public void setCost(int cost) {
        this.cost = cost;
    }

    //Method that says if the creature is unlocked.
    public boolean isUnlocked() {
        return unlocked;
    }

    //Method that sets the creature's locked/unlocked status.
    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
}
