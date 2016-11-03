package questionablequality.rpglifeapp.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import questionablequality.rpglifeapp.ApiController;

/**
 * Created by Tobi on 01-Nov-16.
 */

public class Guild {
    private String name;
    private int guildLeader;
    private List<Integer> members;

    private List<Quest> quests;

    private String code;

    public Guild(String name) {
        this.name = name;
        code = generateCode();
    }

    public Guild(String name, List<Integer> members, List<Quest> quests) {
        this.name = name;
        this.members = members;
        this.quests = quests;

        code = generateCode();
    }

    private String generateCode(){
        Random r = new Random();
        String c = "";
        for (int i = 0; i < 6; i++){
            c += r.nextInt(10);
        }
        return c;
    }

    public User getGuildLeader(Context context) {
        ApiController api = new ApiController(context);
        try {
            return api.getUserById(guildLeader).get().getResult();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }


    public String getGuildLeaderString(Context context){
        return "Guildleader: " + getGuildLeader(context).getUsername();
    }

    public String getCodeString(){
        if(code == null) {
            code = generateCode();
        }
        return "Code: " + code;
    }

    public String getName() {
        return name;
    }

    public List<User> getMembers(Context context) {
        ApiController api = new ApiController(context);
        ArrayList<User> list= new ArrayList<>();
        for(Integer u : members){
            try {
                list.add(api.getUserById(u).get().getResult());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public boolean hasGuildLeader(){
        return (guildLeader != -1);
    }
}
