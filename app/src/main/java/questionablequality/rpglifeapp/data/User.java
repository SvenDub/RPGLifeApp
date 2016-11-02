package questionablequality.rpglifeapp.data;

import android.content.Context;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Created by Tobi on 29-Sep-16.
 */

public class User implements Serializable {

    private int id;
    private String username;
    private Character character;

    private Guild guild;

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
        String returnstring = "";

        Iterator it = character.getAttributes();
        while(it.hasNext()){
            returnstring += character.getAttributeString(it.next().toString()) + "\n";
        }
        return returnstring;
    }

    public boolean hasGuild(){
        return (guild != null);
    }

    public Guild getGuild() {
        return guild;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public int getId() {
        return id;
    }
}
