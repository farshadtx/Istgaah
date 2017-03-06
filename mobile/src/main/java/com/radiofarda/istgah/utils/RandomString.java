package com.radiofarda.istgah.utils;

import java.util.Random;

/**
 * Created by Farshad on 3/5/17.
 *
 */

public class RandomString {
    static final String AB = "abcdefghijklmnopqrstuvwxyz";
    static Random rnd = new Random();

    static public String create() {
        StringBuilder sb = new StringBuilder(10);
        for( int i = 0; i < 10; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }
}
