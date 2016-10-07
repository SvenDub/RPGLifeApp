package questionablequality.rpglifeapp.data;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tobi on 29-Sep-16.
 */

public class User implements Serializable {
    private String username;
    private Character character;
    private List<Quest> quests;
    private Map attributes;

    public User(String username){
        this.username = username;
        quests = new ArrayList<>();
        attributes = new HashMap();
    }

    /**
     * Creates a character and returns it.
     * @param c Context, used for retrieving the spritesheet.
     * @param charIndex the location of the character on the spritesheet. 0-10 is recommended.
     * @return the created character.
     */
    public Character makeCharacter(Context c, int charIndex){
        return character = new Character(c, charIndex);
    }

    /**
     * returns the character.
     * @return the character.
     */
    public Character getCharacter(){
        return character;
    }

    public boolean addQuest(Quest quest){
        quests.add(quest);
        return true;
    }
}
