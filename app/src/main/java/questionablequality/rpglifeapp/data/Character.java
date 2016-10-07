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

    public Character(Context c, int charnumber){
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;

        this.bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.character_spritesheet, opts);
        this.charnumber = charnumber;
        quests = new ArrayList<Quest>();
    }

    /**
     * return the cropped version of the spritesheet that corresponds with the charindex.
     * @return a Bitmap representing the character.
     */
    public Bitmap getCharacterSprite(){
        return Bitmap.createBitmap(bmp, 113*charnumber, 0, 113, 110);
    }
}
