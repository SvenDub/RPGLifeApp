package questionablequality.rpglifeapp.data;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tobi on 29-Sep-16.
 */

public class User implements Serializable {
    private String username;
    private Character character;

    public User(String username){
        this.username = username;
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

    /**
     * returns the username.
     * @return the username.
     */
    public String getUsername(){
        return username;
    }

    public String getInfo(){
        String returnstring = username.substring(0, username.indexOf('@'));

        for(String s : Arrays.asList("Strength", "Dexterity", "Charisma")){
            returnstring += "\n" + character.getAttributeString(s);
        }
        return returnstring;
    }
}
