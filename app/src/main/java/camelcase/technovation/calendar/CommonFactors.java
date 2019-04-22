package camelcase.technovation.calendar;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class CommonFactors
{
    private EntryStorage entryStorage;
    private HashMap<String, Object> sadCommonFactors;
    private HashMap<String, Object> happyCommonFactors;
    private ArrayList<Entry> happyEntries;
    private ArrayList<Entry> sadEntries;

    public static final int SAD_MOOD_VALUE_CUTOFF = 3;
    public static final int ZERO = 0;
    public static final int NOT_ENOUGH_DATA_CUTOFF = 10;

    public CommonFactors(EntryStorage es)
    {
        entryStorage = es;
        sadCommonFactors = new HashMap<>();
        happyCommonFactors = new HashMap<>();
        happyEntries = new ArrayList<>();
        sadEntries = new ArrayList<>();

        createEntryLists();
    }

    //sorts through the entries and sort them into sad and happy list
    private void createEntryLists()
    {
        Collection<Entry> entries = entryStorage.getEntryStorage().values();

        for (Entry currentEntry : entries)
        {
            if (currentEntry.getMood().getMoodValue() < SAD_MOOD_VALUE_CUTOFF) //moodvalue < 3 is sad
            {
                sadEntries.add(currentEntry);
            }
            else
            {
                happyEntries.add(currentEntry);
            }
        }
    }

    //reusable code, finds the common factors that leads to a mood
    private HashMap<String, Object> findCommonFactors(ArrayList<Entry> entryArrayList)
    {
        ArrayList<Integer> sleepDuration = new ArrayList<>();
        ArrayList<Boolean> medication = new ArrayList<>();
        ArrayList<Integer> mCycle = new ArrayList<>();

        for (Entry entry: entryArrayList)
        {
            sleepDuration.add(entry.getSleepDuration());
            medication.add(entry.getMedication());
            mCycle.add(entry.getmCycle());
        }

        HashMap<String, Object> commonFactors = new HashMap<>();

        commonFactors.put("Sleep Duration", findAverageInt(sleepDuration));
        commonFactors.put("Medication", findAverageBoolean(medication));
        commonFactors.put("Menstrual Cycle", findAverageInt(mCycle));

        return commonFactors;
    }

    //reusable code; finds the average int
    private int findAverageInt(ArrayList<Integer> intList)
    {
        int count = ZERO;
        int sum = ZERO;

        for (Integer integer: intList)
        {
            sum += integer;
            count++;
        }

        if (count == ZERO) //to prevent mathematical error of dividing by 0 when the list is empty
        {
            count = 1;
        }
        return sum/count;
    }

    //reusable code; finds most common boolean
    private boolean findAverageBoolean(ArrayList<Boolean> boolList)
    {
        int trueCount = ZERO;
        int falseCount = ZERO;

        for (Boolean bool: boolList)
        {
            if (bool)
            {
                trueCount++;
            }
            else
            {
                falseCount++;
            }
        }

        if (trueCount > falseCount)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //returns Hashmap of factors causing sadness
    public HashMap<String, Object> getSadCommonFactors()
    {
        sadCommonFactors = findCommonFactors(sadEntries);
        return sadCommonFactors;
    }

    //returns Hashmap of factors causing happiness
    public HashMap<String, Object> getHappyCommonFactors()
    {
        happyCommonFactors = findCommonFactors(happyEntries);
        return happyCommonFactors;
    }

    //returns false if insufficient data
    public boolean enoughData()
    {
        if (entryStorage.getEntryStorage().size() < NOT_ENOUGH_DATA_CUTOFF)
        {
            return false;
        }
        return true;
    }
}
