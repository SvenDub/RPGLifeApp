package questionablequality.rpglifeapp.data;

import java.util.List;
import java.util.Random;

/**
 * Created by Tobi on 01-Nov-16.
 */

public class Guild {
    private String name;
    private List<User> members;

    private List<Quest> quests;

    private final String code;

    public Guild(String name) {
        this.name = name;
        code = generateCode();
    }

    public Guild(String name, List<User> members, List<Quest> quests) {
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

    public String getName() {
        return name;
    }

    public List<User> getMembers() {
        return members;
    }

    public void addMember(User member) {
        members.add(member);
    }

    public List<Quest> getQuests() {
        return quests;
    }
}
