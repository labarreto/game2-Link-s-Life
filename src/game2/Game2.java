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
    String backFileName = new String("background.png");
    Hero hero;
    Key key;
    int lives;
    int score;
    int money;

    WorldImage background;
    LinkedList<Rupees> rupees;
    LinkedList<Enemy> enemies;
    LinkedList<Bomb> bombs;

    public Game2(int width, int height, int lives, int score, Hero hero,
            LinkedList<Enemy> enemies, LinkedList<Rupees> rupees) {
        this.screenWIDTH = width;
        this.screenHEIGHT = height;
        this.hero = hero;
        this.rupees = rupees;
        this.enemies = enemies;
        this.lives = lives;
        this.score = score;
        background = new FromFileImage(new Posn(screenWIDTH / 2, screenHEIGHT / 2), backFileName);
    }

    public World onKeyEvent(String ke) {

        Iterator<Enemy> en = enemies.listIterator(0);

        if (ke.equals("up") || ke.equals("left") || ke.equals("right") || ke.equals("down")) {

            boolean canMove = true;
            Hero extra = this.hero.moveLink(ke);
            while (en.hasNext()) {

                if (extra.collisionHuh(en.next())) {
                    canMove = false;
                }
            }
            if (canMove) {
                // hero = hero.moveLink(ke); //avoiding mutation
                return new Game2(this.screenWIDTH, this.screenHEIGHT, this.lives,
                        this.score, hero.moveLink(ke), this.enemies, rupees);
            } else {
                return this;
            }

        } else {
            return this;
        }
    }

    public Game2 onTick() {
        LinkedList<Enemy> enList = new LinkedList<Enemy>();

        Enemy enemy = new Enemy();
        if (Utility.biasCoinToss()) {
            enList.add(new Enemy());
        }

        Iterator<Enemy> yay = enemies.listIterator(0);
        Iterator<Rupees> rup = rupees.listIterator(0);

        while (yay.hasNext()) {
            Enemy newEn = yay.next().moveEnemy();
            enList.add(newEn);
            //moves through list. 
        }

        yay = enList.listIterator(0);

        while (yay.hasNext()) { //while yay still has next, 
            //(should always be true until world end
            Enemy enn = yay.next();
            if (enn.collisionHuh(hero)) {
                lives--;
                yay.remove();
            }
        }
        
        while (rup.hasNext()) {
            Rupees r = rup.next();
            if (hero.collectingRupees(r)) {
                money++;
                rup.remove();
            }
        }
        return new Game2(this.screenWIDTH, this.screenHEIGHT, this.lives,
                this.score, this.hero, enList, rupees);
    }

    public WorldImage makeImage() {

        Iterator<Enemy> yay = enemies.listIterator(0);
        Iterator<Rupees> rup = rupees.listIterator(0);
        WorldImage world = new OverlayImages(background,
                new OverlayImages(
                        new TextImage(new Posn(400, 20), "Lives:  " + lives,
                                3, new Black()),
                        new OverlayImages(
                                new TextImage(new Posn(400, 40), "Score:  "
                                        + score, 20, new Black()), hero.linkImage())));

        while (yay.hasNext()) {
            world = new OverlayImages(world,
                   yay.next().enemyImage());
        }
        
        
        while (rup.hasNext()) {
            world = new OverlayImages(world,
                    rup.next().rupeeImage());
        }
        world = new OverlayImages(world, hero.linkImage());
       
        return world;
    }

    public WorldEnd worldEnds() {
        if (lives < 1) {
            return new WorldEnd(true,
                    new OverlayImages(background,
                            new OverlayImages(new TextImage(new Posn(screenWIDTH / 2, screenHEIGHT / 2),
                                            "GAME OVER!!!!", 30, 1, new Black()),
                                    new TextImage(new Posn(screenWIDTH / 2, screenHEIGHT / 2 + 20),
                                            "Final Score:   " + score,
                                            20, 1, new Black()))));
        } else {
            return new WorldEnd(false, this.makeImage());
        }
    }

    public static void main(String[] args) {
        LinkedList yayNora = new LinkedList();
        yayNora.add(new Enemy());
        LinkedList yayRupees = new LinkedList();
        yayRupees.add(new Rupees());
        yayRupees.add(new Rupees());
        yayRupees.add(new Rupees());
        yayRupees.add(new Rupees());
        Game2 game = new Game2(screenWIDTH, screenHEIGHT, 15, 0,
                new Hero(new Posn(screenWIDTH / 2, screenHEIGHT / 2), "linkDOWN.png"), yayNora, yayRupees);
        game.bigBang(screenWIDTH, screenHEIGHT, 0.2);
    }

}
