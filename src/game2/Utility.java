/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game2;

import java.util.Random;

/**
 *
 * @author ldbruby95
 */
public class Utility {

    public static boolean coinToss() {
        Random randomInts = new Random();
        int scene = randomInts.nextInt();
        Math.abs(scene);
        int remainder = scene % 2;
        return (remainder == 1 || remainder == 3);
    }

    public static int randInt(int min, int max) {

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
    
    public static int randInt2(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max-min) +1) + min;
        return randomNum;
    }
    
    
    

    
}
