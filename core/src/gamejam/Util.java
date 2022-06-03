package gamejam;

import gamejam.levels.LevelConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Util {

    /**
     * From: https://stackoverflow.com/questions/22380890/generate-n-random-numbers-whose-sum-is-m-and-all-numbers-should-be-greater-than
     */
    public static List<Integer> n_random(int targetSum, int numberOfDraws) {
        Random r = new Random(LevelConfiguration.SEED);
        List<Integer> load = new ArrayList<>();

        //random numbers
        int sum = 0;
        for (int i = 0; i < numberOfDraws; i++) {
            int next = r.nextInt(targetSum) + 1;
            load.add(next);
            sum += next;
        }

        //scale to the desired target sum
        double scale = 1d * targetSum / sum;
        sum = 0;
        for (int i = 0; i < numberOfDraws; i++) {
            load.set(i, (int) (load.get(i) * scale));
            sum += load.get(i);
        }

        //take rounding issues into account
        while(sum++ < targetSum) {
            int i = r.nextInt(numberOfDraws);
            load.set(i, load.get(i) + 1);
        }

        return load;
    }

}
