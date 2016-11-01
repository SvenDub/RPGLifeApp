package questionablequality.rpglifeapp.provider;

import android.content.Context;

import com.koushikdutta.ion.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import questionablequality.rpglifeapp.ApiController;
import questionablequality.rpglifeapp.data.Quest;

/**
 * Created by Tobi on 01-Nov-16.
 */

public class GuildQuestProvider {

    private Context context;
    private ApiController mApiController;

    public GuildQuestProvider(Context context){
        this.context = context;
        mApiController = new ApiController(context);
    }

    /**
     * Returns a dummy array of quests.
     * @return an array of quests.
     */
    public List<Quest> ReturnQuests(){
        //TODO
        return null;
    }

    public boolean addQuest(Quest quest) {
        //TODO
        return false;
    }
}
