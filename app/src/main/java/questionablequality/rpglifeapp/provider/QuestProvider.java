package questionablequality.rpglifeapp.provider;

import android.content.Context;

import com.koushikdutta.ion.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import questionablequality.rpglifeapp.ApiController;
import questionablequality.rpglifeapp.data.Quest;

/**
 * Created by Tobi on 30-Sep-16.
 */

public class QuestProvider {

    private Context context;
    private ApiController mApiController;

    public QuestProvider(Context context){
        this.context = context;
        mApiController = new ApiController(context);
    }

    /**
     * Returns a dummy array of quests.
     * @return an array of quests.
     */
    public List<Quest> ReturnQuests(){
        try {
            Response<List<Quest>> listResponse = mApiController.getQuests().get();
            if (listResponse.getResult() != null && listResponse.getException() == null) {
                return listResponse.getResult();
            } else {
                return new ArrayList<>();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public List<Quest> ReturnGuildQuests(){
        //TODO

        return new ArrayList<>();
    }

    public boolean addQuest(Quest quest) {
        try {
            return mApiController.addQuest(quest).get().getResult() != null;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addGuildQuest(Quest quest) {
        //TODO
        return false;
    }
}
