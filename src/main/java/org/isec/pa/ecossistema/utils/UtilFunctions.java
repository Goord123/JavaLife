package org.isec.pa.ecossistema.utils;

import java.util.Random;

public class UtilFunctions {

    public static int getRandomNumber(int max) {
        Random random = new Random();
        return random.nextInt(max + 1);
    }

}
