package clases;

import java.util.Random;

/**
 *
 * @author Jonathan
 */
public class l {

    public int[] getr() {

        int[] numbers = new int[16];
        int count = 0;

        while (count < 16) {
            Random r = new Random();
            int na = r.nextInt(8) + 1;
            int nvr = 0;

            for (int i = 0; i < 16; i++) {
                if (numbers[i] == na) {
                    nvr++;
                }
            }
            if (nvr < 2) {
                numbers[count] = na;
                count++;
            }
        }
        return numbers;
    }
}
