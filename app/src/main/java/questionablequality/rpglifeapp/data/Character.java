package questionablequality.rpglifeapp.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

import questionablequality.rpglifeapp.R;

public class Character {
    private Bitmap bmp;
    private int charnumber;
    private List<Quest> quests;
    private Map<String, Integer> attributes;

    public Character(Context c, int charnumber){
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;

        this.bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.character_spritesheet, opts);
        this.charnumber = charnumber;
        quests = new ArrayList<Quest>();
        attributes = new HashMap();
        generateAttributes(c);
    }

    /**
     * return the cropped version of the spritesheet that corresponds with the charindex.
     * @return a Bitmap representing the character.
     */
    public Bitmap getCharacterSprite(){
        return Bitmap.createBitmap(bmp, 113*charnumber, 0, 113, 110);
    }

    /**
     * adds a quest to the character's questlist, and returns a bool if it works.
     * @param quest the to-add quest.
     * @return confirmation boolean.
     */
    public boolean addQuest(Quest quest){
        quests.add(quest);
        return true;
    }

    /**
     * retrieves an attribute from the attributes-map.
     * @param attributename name(key) of the attribute.
     * @return the attribute value.
     */
    public int getAttribute(String attributename){
        return attributes.get(attributename);
    }

    public String getAttributeString(String attributename){
        return attributename + ": " + attributes.get(attributename);
    }

    private void generateAttributes(Context c){
        Random r = new Random();
        for(String S : c.getResources().getStringArray(R.array.attributes)){
            attributes.put(S, r.nextInt(5) + 1);
        }
    }

}
