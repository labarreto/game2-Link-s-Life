/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game2;

import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldimages.*;
import java.util.*;
import java.awt.Color;

/**
 *
 * @author ldbruby95
 */
public class Enemy {

    Posn pin;
    String enemyFileName;
    WorldImage enemy;
    int width;
    int height;
    static int SCREENWIDTH = 700;
    static int SCREENHEIGHT = 500;
    boolean firstCallHuh;

    public Enemy() {
        this(new Posn(Utility.randInt(0, SCREENWIDTH), 0), true);

    }

    public Enemy(Posn pin, boolean firstCallHuh) {
        this.pin = pin;
        enemyFileName = "enemy.png";
        enemy = new FromFileImage(pin, enemyFileName);
        this.width = enemy.getWidth();
        this.height = enemy.getHeight();
        this.firstCallHuh = firstCallHuh;
    }

    public WorldImage enemyImage() {
        return enemy.getMovedTo(pin);
    }

    public Enemy moveEnemy() {
        int x = this.pin.x;
        int y = this.pin.y;
        //down first, (+10 y)
        if (this.firstCallHuh) {
            return new Enemy(new Posn(x, y + 10), false);
            //remembering the last action with the boolean
        } else {
            //turn left, (-10 x)

            return new Enemy(new Posn(x - 10, y), true);

        }
    }
    
        public boolean collisionHuh(Hero hero) {
        int a = this.pin.x;
        int b = hero.pin.x;
        int c = this.pin.y;
        int d = hero.pin.y;

        int halfFishWidth = hero.width / 2;
        int halfFishHeight = hero.height / 2;
        
        if (Math.abs(a - b) < (halfFishWidth)
                && (Math.abs(c - d) < (halfFishHeight))) {
            return true;
        } else {
            return false;
        }
    }

}
