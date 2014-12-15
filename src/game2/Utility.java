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
        if (randInt(0,1) == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static int randInt(int min, int max) {

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
    
    
    public static boolean biasCoinToss() {
        Random randomInts = new Random();
        int scene = randomInts.nextInt();
        Math.abs(scene);
        int remainder = scene % 2;
        return (remainder == 1);
    }

    public static int dieRoll(int numSides) {
        Random randomInts = new Random();
        int side = randomInts.nextInt();
        Math.abs(side);
        int remainder = side % numSides;
        return (Math.abs(remainder));
    }
    
    public static void main(String[] args) {
        System.out.println(dieRoll(8));
    }

    
    

    
}
