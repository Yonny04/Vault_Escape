package vaultescape.reward;
import java.util.Random;

public class RegularReward extends Reward {

    private static final int[] POSSIBLE_VALUES = {10, 20, 30, 40, 50};
    private static final Random random = new Random();
    public RegularReward(int x, int y) {
        super(x,y, getRandomPoints());
    }
    public static int getRandomPoints(){
        return POSSIBLE_VALUES[random.nextInt(POSSIBLE_VALUES.length)];
    }

}
