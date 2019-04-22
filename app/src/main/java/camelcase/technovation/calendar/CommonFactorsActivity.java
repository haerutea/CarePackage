package camelcase.technovation.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import camelcase.technovation.BaseActivity;
import camelcase.technovation.R;
import camelcase.technovation.chat.object_classes.Constants;

public class CommonFactorsActivity extends BaseActivity {

    private EntryStorage entryStorage;
    private CommonFactors commonFactors;

    private TextView happySleepDuration;
    private TextView happyMedication;
    private TextView happyMenstruation;
    private TextView sadSleepDuration;
    private TextView sadMedication;
    private TextView sadMenstruation;

    private static final String NOT_ENOUGH_DATA_MESSAGE = "Not enough data";

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.common_factors_activity, (ViewGroup) findViewById(R.id.contents));
        entryStorage = (EntryStorage) getIntent().getSerializableExtra(Constants.ENTRY_STORAGE_KEY);

        HashMap<String, TextView> happyFactorsHashMap = new HashMap<>();
        HashMap<String, TextView> sadFactorsHashMap = new HashMap<>();

        commonFactors = new CommonFactors(entryStorage);

        happySleepDuration = findViewById(R.id.HappySleepDuration);
        happyMedication = findViewById(R.id.HappyMedication);
        happyMenstruation = findViewById(R.id.HappyMenstruation);
        sadSleepDuration = findViewById(R.id.SadSleepDuration);
        sadMedication = findViewById(R.id.SadMedication);
        sadMenstruation = findViewById(R.id.SadMenstruation);

        happyFactorsHashMap.put("Sleep Duration", happySleepDuration);
        happyFactorsHashMap.put("Medication", happyMedication);
        happyFactorsHashMap.put("Menstrual Cycle", happyMenstruation);
        sadFactorsHashMap.put("Sleep Duration", sadSleepDuration);
        sadFactorsHashMap.put("Medication", sadMedication);
        sadFactorsHashMap.put("Menstrual Cycle", sadMenstruation);

        HashMap<String, TextView> hashMapInUse = new HashMap<>();
        HashMap<String, Object> commonFactorSourceInUse = new HashMap<>();

        for (int countMap = 1; countMap <= 2; countMap++) //1st time happy, 2nd time sad
        {
            switch (countMap)
            {
                case 1: // set textview of happy factors
                    hashMapInUse = happyFactorsHashMap;
                    commonFactorSourceInUse = commonFactors.getHappyCommonFactors();
                    break;
                case 2: // set textview of sad factors
                    hashMapInUse = sadFactorsHashMap;
                    commonFactorSourceInUse = commonFactors.getSadCommonFactors();
            }

            for (Map.Entry<String, TextView> entry : hashMapInUse.entrySet())
            {
                String key = entry.getKey();
                TextView value = entry.getValue();

                if (commonFactors.enoughData())//if there are enough data to do analysis
                {
                    if (key.equals("Medication")) //future dev: this can check for bool object type in commonFactorSourceInUse
                    {
                        value.setText(medicationStatus((boolean) commonFactorSourceInUse.get(key)));
                    }
                    else
                    {
                        value.setText(commonFactorSourceInUse.get(key).toString());
                    }
                }
                else //if not enough data
                {
                    value.setText(NOT_ENOUGH_DATA_MESSAGE);
                }
            }
        }
    }

    //if true returns "taken" else returns "not taken
    private String medicationStatus(boolean b)
    {
        if (b)
        {
            return "taken";
        }
        return  "not taken";
    }
}
