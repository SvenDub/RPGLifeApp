package questionablequality.rpglifeapp.provider;

import android.content.Context;

import com.koushikdutta.ion.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import questionablequality.rpglifeapp.ApiController;
import questionablequality.rpglifeapp.data.Quest;
import questionablequality.rpglifeapp.data.User;

/**
 * Created by Tobi on 01-Nov-16.
 */

public class GuildMemberProvider {

    private Context context;
    private ApiController mApiController;

    public GuildMemberProvider(Context context){
        this.context = context;
        mApiController = new ApiController(context);
    }

    /**
     * Returns a dummy array of quests.
     * @return an array of quests.
     */
    public List<User> ReturnMembers(){
        //TODO
        return null;
    }
}
