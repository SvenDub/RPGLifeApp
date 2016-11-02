package questionablequality.rpglifeapp.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import questionablequality.rpglifeapp.R;

public class Character {
    private Bitmap bmp;
    private int charnumber;
    private List<Quest> quests;
    private Map<String, Integer> attributes;
    private int xp;

    public Character(Context context, int charnumber, int xp) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;

        this.bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.character_spritesheet, opts);
        this.charnumber = charnumber;
        this.xp = xp;
        quests = new ArrayList<>();
        attributes = new LinkedHashMap<>();
        generateAttributes(context);
    }

    /**
     * return the cropped version of the spritesheet that corresponds with the charindex.
     *
     * @return a Bitmap representing the character.
     */
    public Bitmap getCharacterSprite() {
        return Bitmap.createBitmap(bmp, 113 * charnumber, 0, 113, 110);
    }

    /**
     * adds a quest to the character's questlist, and returns a bool if it works.
     *
     * @param quest the to-add quest.
     * @return confirmation boolean.
     */
    public boolean addQuest(Quest quest) {
        return quests.add(quest);
    }

    /**
     * retrieves an attribute from the attributes-map.
     *
     * @param attributename name(key) of the attribute.
     * @return the attribute value.
     */
    public int getAttribute(String attributename) {
        return attributes.get(attributename);
    }

    public String getAttributeString(String attributename) {
        return attributename + ": " + attributes.get(attributename);
    }

    public Iterator getAttributes() {
        return attributes.keySet().iterator();
    }

    private void generateAttributes(Context context) {
        Random r = new Random();
        attributes.put("Experience", xp);
        for (String attribute : context.getResources().getStringArray(R.array.attributes)) {
            attributes.put(attribute, Math.round((r.nextInt(5) + 1) * ((xp + 10) / 10f)));
        }
    }

}
