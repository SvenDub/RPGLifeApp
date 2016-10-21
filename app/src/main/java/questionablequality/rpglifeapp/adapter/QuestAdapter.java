package questionablequality.rpglifeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import questionablequality.rpglifeapp.ApiController;
import questionablequality.rpglifeapp.R;
import questionablequality.rpglifeapp.data.Quest;
import questionablequality.rpglifeapp.provider.QuestProvider;

/**
 * Created by Tobi on 30-Sep-16.
 */

public class QuestAdapter extends ArrayAdapter<Quest> {
    private ApiController api;

    private Context context;
    private List<Quest> quests;


    public QuestAdapter(Context context, List<Quest> quests) {
        super(context, R.layout.quest_list_item, quests);
        this.context = context;
        this.quests = quests;
        this.api = new ApiController(this.context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Quest requestedQuest = quests.get(position);

        View view = convertView;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.quest_list_item, null);
        }

        TextView tv = (TextView)view.findViewById(R.id.textView);
        tv.setText(requestedQuest.getName());
        final ProgressBar pb = (ProgressBar)view.findViewById(R.id.PbProgress);
        pb.setMax(requestedQuest.getGoal());
        pb.setProgress(requestedQuest.getProgress());

        Button btn = (Button)view.findViewById(R.id.BtnIncreaseButton);
        final View finalView = view;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestedQuest.Increase();
                try {
                    api.saveQuest(requestedQuest).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                pb.setProgress(requestedQuest.getProgress());
            }
        });

        return view;
    }
}
