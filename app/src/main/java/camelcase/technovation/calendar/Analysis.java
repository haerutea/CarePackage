package camelcase.technovation.calendar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Analysis
{
    EntryStorage entryStorage;
    LinkedHashMap<String, Entry> entryLinkedHashMap;

    public Analysis(EntryStorage es)
    {
        entryStorage = es;
        entryLinkedHashMap = entryStorage.getEntryStorage();
    }

    public int findNoOfSelectedMood (int moodValue)
    {
        System.out.println(entryLinkedHashMap.size()); //todo delete testing
        int count = 0;

        //this section is preserved for future development (limiting the number of datapoints to the last 30 days)
//        List<String> alKeys = new ArrayList<String>(entryLinkedHashMap.keySet());
//
//        // reverse order of keys
//        Collections.reverse(alKeys);
//
//        for (int index = 0; index < 30; index++)
//        {
//            entryLinkedHashMap.get(alKeys.get(index));
//        }

        for (Map.Entry<String, Entry> entry : entryLinkedHashMap.entrySet())
        {
            if (entry.getValue().getMood().getMoodValue() == moodValue)
            {
                count++;
            }
        }
        return count;
    }
}
