package questionablequality.rpglifeapp.data;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by Tobi on 29-Sep-16.
 */

public class User implements Serializable {
    public String name;
    public Character character;

    public User(String name){
        this.name = name;
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
}
