package questionablequality.rpglifeapp.data;

/**
 * Created by Tobi on 30-Sep-16.
 */

public class Quest {
    private String name;
    private String description;
    private int goal;

    private int progress;
    private int rewardxp;


    /**
     * Deprecated
     * @param description deprecated
     */
    public Quest(String description) {
        this.description = description;
    }

    public Quest(String name, String description, int goal) {
        this.name = name;
        this.description = description;
        this.goal = goal;

        this.progress = 0;
        this.rewardxp = this.goal*10;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getGoal() {
        return goal;
    }

    public int getRewardxp() {
        return rewardxp;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void Increase(){
        progress++;
    }
}
