package camelcase.technovation.todo.notifications;

//AnimalNotifications are custom notifications that will be used to store the user's set notifications.
public class AnimalNotification {
    //Instantiate variables.
    private int id;
    private int difficulty;

    private boolean completed;

    private String name;
    private String description;
    private String dateTime;

    //Default constructor.
    public AnimalNotification(int id, String name, String description, int difficulty, String dateTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;

        //Completed is always initially set to false.
        completed = false;

        this.dateTime = dateTime;
    }

    //Method for setting the notification's completed status.
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    //Method for checking if the notification is completed or not.
    public boolean isCompleted() {
        return completed;
    }

    //Method for setting the name of the notification.
    public void setName(String name) {
        this.name = name;
    }

    //Method for getting the name of the notification.
    public String getName() {
        return name;
    }

    //Method for setting the difficulty of the notification.
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    //Method for getting the difficulty of the notification.
    public int getDifficulty() {
        return difficulty;
    }

    //Method for setting the description of the notification.
    public void setDescription(String description) {
        this.description = description;
    }

    //Method for getting the description of the notification.
    public String getDescription() {
        return description;
    }

    //Method for getting the date and time of the notification.
    public String getDateTime() {
        return dateTime;
    }

    //Method for setting the date and time of the notification.
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    //Method for getting the notification ID.
    public int getId() {
        return id;
    }
}
