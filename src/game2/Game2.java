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

/**
 *
 * @author ldbruby95
 */
// based off of legend of zelda
public class Game2 extends World {

    static int screenWIDTH = 700;
    static int screenHEIGHT = 500;
    Hero hero;
    int lives;
    int score;

    WorldImage background = new RectangleImage( new Posn(0,0), screenWIDTH, screenHEIGHT, new White());
    LinkedList<Enemy> enemies;

    public Game2(int width, int height, int lives, int score, Hero hero,
            LinkedList<Enemy> enemies) {
        this.screenWIDTH = width;
        this.screenHEIGHT = height;
        this.hero = hero;
        this.enemies = enemies;
        this.lives = lives;
        this.score = score;
    }

    public World onKeyEvent(String ke) {
        hero.moveLink(ke);
        return new Game2(this.screenWIDTH, this.screenHEIGHT, this.lives,
                this.score, hero, this.enemies);
    }

    public Game2 onTick() {
        Enemy enemy = new Enemy();
        if (Utility.coinToss()) {
            enemies.add(new Enemy());
        }

        Iterator<Enemy> yay = enemies.listIterator(0);

        while (yay.hasNext()) {
            yay.next().moveEnemy();
            //moves through list. 
        }

        yay = enemies.listIterator(0);

        while (yay.hasNext()) { //while yay still has next, 
            //(should always be true until world end
            Enemy listNourishment = yay.next();
            if (listNourishment.collisionHuh(hero)) {
                lives--;
                yay.remove();
            }
        }
        return new Game2(this.screenWIDTH, this.screenHEIGHT, this.lives,
                this.score, this.hero, this.enemies);
    }

    
    
    
    public WorldImage makeImage() {


        Iterator<Enemy> yay = enemies.listIterator(0);
        WorldImage world = new OverlayImages(background,
                new OverlayImages(
                        new TextImage(new Posn(400, 20), "Lives:  " + lives,
                                3, new Black()),
                        new OverlayImages(
                                new TextImage(new Posn(400, 40), "Score:  "
                                        + score, 20, new Black()), hero.linkImage())));
        
        while (yay.hasNext()) {
            world = new OverlayImages(world, yay.next().enemyImage());
        }

        return world;
    }
    
        public WorldEnd worldEnds() {
        if (lives < 1) {
            return new WorldEnd(true,
                    new OverlayImages(background,
                            new OverlayImages(new TextImage(new Posn(250, 300),
                                            "GAME OVER!!!!", 30, 1, new Black()),
                                            new TextImage(new Posn(250, 400), 
                                                    "Final Score:   " + score, 
                                                    20, 1, new Black()))));
        } else {
            return new WorldEnd(false, this.makeImage());
        }
    }

    public static void main(String[] args) {
               LinkedList yayNora = new LinkedList();
        yayNora.add(new Enemy());

        Game2 game = new Game2(screenWIDTH, screenHEIGHT, 15, 0, new Hero(), yayNora);
        game.bigBang(screenWIDTH, screenHEIGHT, 0.2);
    }

}
