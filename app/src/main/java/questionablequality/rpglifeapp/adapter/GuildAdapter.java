package questionablequality.rpglifeapp.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutionException;

import questionablequality.rpglifeapp.ApiController;
import questionablequality.rpglifeapp.R;
import questionablequality.rpglifeapp.data.Quest;
import questionablequality.rpglifeapp.data.User;

/**
 * Created by Tobi on 01-Nov-16.
 */

public class GuildAdapter extends ArrayAdapter<User>{
    private ApiController api;

    private Context context;
    private List<User> members;

    public GuildAdapter(Context context, List<User> members) {
        super(context, R.layout.guild_list_item, members);
        this.context = context;
        this.members = members;
        this.api = new ApiController(this.context);
    }

    @Override
    public int getCount() {
        return members.size();
    }

    @Nullable
    @Override
    public User getItem(int position) {
        return members.get(position);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final User requestedMember = getItem(position);

        View view = convertView;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.guild_list_item, null);
        }

        TextView tv = (TextView)view.findViewById(R.id.txtMemberName);
        tv.setText(requestedMember.getUsername());

        return view;
    }
}
