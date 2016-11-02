package questionablequality.rpglifeapp.provider;

import android.content.Context;

import com.koushikdutta.ion.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import questionablequality.rpglifeapp.ApiController;
import questionablequality.rpglifeapp.data.Guild;
import questionablequality.rpglifeapp.data.Quest;
import questionablequality.rpglifeapp.data.User;

/**
 * Created by Tobi on 01-Nov-16.
 */

public class GuildProvider {

    private Context context;
    private ApiController mApiController;

    public GuildProvider(Context context){
        this.context = context;
        mApiController = new ApiController(context);
    }

    /**
     * Returns a dummy array of quests.
     * @return an array of quests.
     */
    public List<Guild> ReturnGuilds() {
        try {
            Response<List<Guild>> listResponse = mApiController.getGuilds().get();
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

    public List<User> ReturnGuildMembers(User user) {
        return user.getGuild().getMembers();
    }
}
