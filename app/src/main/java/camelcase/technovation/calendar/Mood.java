package camelcase.technovation.calendar;

import android.graphics.drawable.Drawable;

public class Mood
{
    private int moodValue;
    private String colour; //future dev

    public Mood(int mv)
    {
        moodValue = mv;
        setColour(mv);
    }

    //resets the colour depending on the mood value (future development)
    private void setColour(int mv)
    {
        switch (mv)
        {
            case 1:
        }
    }

    public void setMoodValue(int mv)
    {
        moodValue = mv;
        setColour(mv);
    }

    public int getMoodValue()
    {
        return moodValue;
    }
}
