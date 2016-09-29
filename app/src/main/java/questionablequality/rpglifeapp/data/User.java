package questionablequality.rpglifeapp.data;

import java.io.Serializable;

/**
 * Created by Tobi on 29-Sep-16.
 */

public class User implements Serializable {
    public String name;

    public User(String name){
        this.name = name;
    }
}
