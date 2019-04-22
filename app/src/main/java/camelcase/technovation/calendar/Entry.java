package camelcase.technovation.calendar;

import java.util.Date;

public class Entry
{
    private Mood mood;
    private int energyLevel;
    private int sleepDuration;
    private boolean medication;
    private int mCycle;
    private boolean mCycleBool;
    private String note;
    private Date date;

    public Entry()
    {
        date = new Date();
        mood = new Mood(0);
        energyLevel = 0;
        sleepDuration = 0;
        medication = false;
        mCycle = 0;
        mCycleBool = false;
        note = "";
    }

    public void setMood(Mood m)
    {
        mood = m;
    }

    public Mood getMood()
    {
        return mood;
    }

    public void setEnergyLevel(int e)
    {
        energyLevel = e;
    }

    public int getEnergyLevel()
    {
        return energyLevel;
    }

    public void setSleepDuration(int s)
    {
        sleepDuration = s;
    }

    public int getSleepDuration()
    {
        return sleepDuration;
    }

    public void setMedication (boolean m)
    {
        medication = m;
    }

    public boolean getMedication()
    {
        return medication;
    }

    public void setmCycleBool(boolean m) {mCycleBool = m;}

    public boolean getmCycleBoolean() {return mCycleBool;}

    public void setmCycle(int c)
    {
        mCycle = c;
    }

    public int getmCycle()
    {
        return mCycle;
    }

    public void setNote(String n)
    {
        note = n;
    }

    public String getNote()
    {
        return note;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public Date getDate()
    {
        return date;
    }
}

