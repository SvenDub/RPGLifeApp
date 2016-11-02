package questionablequality.rpglifeapp.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import questionablequality.rpglifeapp.ApiController;
import questionablequality.rpglifeapp.R;
import questionablequality.rpglifeapp.data.Quest;

/**
 * Created by Tobi on 30-Sep-16.
 */

public class QuestAdapter extends ArrayAdapter<Quest> {
    private ApiController api;

    private Context context;
    private List<Quest> quests;

    private Filter filter;

    public QuestAdapter(Context context, List<Quest> quests) {
        super(context, R.layout.quest_list_item, quests);
        this.context = context;
        this.quests = quests;
        this.api = new ApiController(this.context);
    }

    @Override
    public int getCount() {
        return quests.size();
    }

    @Nullable
    @Override
    public Quest getItem(int position) {
        return quests.get(position);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final Quest requestedQuest = getItem(position);

        View view = convertView;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.quest_list_item, null);
        }

        TextView tv = (TextView)view.findViewById(R.id.textView);
        tv.setText(requestedQuest.getName());

        ImageView imgLocation = (ImageView) view.findViewById(R.id.img_location);
        imgLocation.setVisibility(requestedQuest.getPlace() == null ? View.GONE : View.VISIBLE);

        final ProgressBar pb = (ProgressBar)view.findViewById(R.id.PbProgress);
        pb.setMax(requestedQuest.getGoal());
        pb.setProgress(requestedQuest.getProgress());

        Button btn = (Button)view.findViewById(R.id.BtnIncreaseButton);
        final View finalView = view;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp1 = MediaPlayer.create(parent.getContext(), R.raw.coinsone);
                MediaPlayer mp2 = MediaPlayer.create(parent.getContext(), R.raw.coinsmany);

                requestedQuest.increase();
                if(requestedQuest.getProgress() == requestedQuest.getGoal()){
                    mp2.start();
                    Toast.makeText(parent.getContext(), "Good Job!", Toast.LENGTH_LONG).show();
                }else{
                    mp1.start();
                }
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

    @NonNull
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new QuestFilter(quests);
    }
        return filter;
    }

    private class QuestFilter extends Filter {
        private List<Quest> sourceObjects;

        public QuestFilter(List<Quest> objects) {
            sourceObjects = new ArrayList<>();
            synchronized (this) {
                sourceObjects.addAll(objects);
            }
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterSeq = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (filterSeq != null && filterSeq.length() > 0) {
                List<Quest> filter = new ArrayList<>();

                for (Quest object : sourceObjects) {
                    // the filtering itself:
                    if (object.getName().toLowerCase().contains(filterSeq) || object.getDescription().toLowerCase().contains(filterSeq)) {
                        filter.add(object);
                    }
                }
                result.count = filter.size();
                result.values = filter;
            } else {
                // add all objects
                synchronized (this) {
                    result.values = sourceObjects;
                    result.count = sourceObjects.size();
                }
            }
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<Quest> filtered = (List<Quest>) results.values;
            notifyDataSetChanged();
            clear();
            for (int i = 0, l = filtered.size(); i < l; i++) {
                add(filtered.get(i));
            }
            notifyDataSetInvalidated();
        }
    }
}
