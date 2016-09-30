package questionablequality.rpglifeapp.provider;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import questionablequality.rpglifeapp.R;
import questionablequality.rpglifeapp.data.Quest;

/**
 * Created by Tobi on 30-Sep-16.
 */

public class QuestProvider {

    private Context context;

    public QuestProvider(Context context){
        this.context = context;
    }

    public List<Quest> ReturnQuests(){
        ArrayList<Quest> quests;
        quests = new ArrayList<>();
        for(String S : context.getResources().getStringArray(R.array.Quest)){
            quests.add(new Quest(S));
        }

        return quests;
    }
}
