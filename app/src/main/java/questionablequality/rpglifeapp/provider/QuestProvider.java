package questionablequality.rpglifeapp.provider;

import android.content.Context;

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
            return mApiController.getQuests().get().getResult();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
