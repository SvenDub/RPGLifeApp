package questionablequality.rpglifeapp.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import questionablequality.rpglifeapp.R;

/**
 * Created by Tobi on 06-Oct-16.
 */

public class Character {
    private Bitmap bmp;
    private int charnumber;

    public Character(Context c, int charnumber){
        this.bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.character_spritesheet);
        this.charnumber = charnumber;
    }

    /**
     * return the cropped version of the spritesheet that corresponds with the charindex.
     * @return a Bitmap representing the character.
     */
    public Bitmap getCharacterSprite(){
        return Bitmap.createBitmap(bmp, 222*charnumber, 0, 222, 220);
    }
}
