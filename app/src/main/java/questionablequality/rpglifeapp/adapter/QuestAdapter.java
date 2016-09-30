package questionablequality.rpglifeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import questionablequality.rpglifeapp.R;
import questionablequality.rpglifeapp.data.Quest;
import questionablequality.rpglifeapp.provider.QuestProvider;

/**
 * Created by Tobi on 30-Sep-16.
 */

public class QuestAdapter extends ArrayAdapter<Quest> {
    private Context context;
    private List<Quest> quests;


    public QuestAdapter(Context context, List<Quest> quests) {
        super(context, R.layout.quest_list_item, quests);
        this.context = context;
        this.quests = quests;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Quest requestedQuest = quests.get(position);

        View view = convertView;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.quest_list_item, null);
        }

        TextView tv = (TextView)view.findViewById(R.id.textView);
        tv.setText(requestedQuest.description);

        return view;
    }
}
