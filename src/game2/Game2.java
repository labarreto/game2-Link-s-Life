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
    int kills;
    int bombN;
    

    WorldImage background;
    WorldImage background2;
    LinkedList<Rupees> rupees;
    LinkedList<Enemy> enemies;
    LinkedList<Bomb> bombs;
    LinkedList<Explosion> explosions;

    public Game2(int lives, int score, int money, int kills, Hero hero,
            LinkedList<Enemy> enemies, LinkedList<Rupees> rupees) {

        this.hero = hero;
        this.rupees = rupees;
        this.enemies = enemies;
        this.lives = lives;
        this.score = score;
        this.money = money;
        this.kills = kills;
        this.bombN = 15;
        background = new FromFileImage(new Posn(screenWIDTH / 2, screenHEIGHT / 2), backFileName);
        background2 = new RectangleImage(new Posn(0,0), screenWIDTH, screenHEIGHT, new White());
    }
    
        public Game2(int score, Hero hero,
            LinkedList<Enemy> enemies, LinkedList<Rupees> rupees) {
        this.hero = hero;
        this.rupees = rupees;
        this.enemies = enemies;
        this.lives = 10;
        this.score = score;
        this.money = 0;
        this.kills = 0;
        this.bombN = 15;
        background = new FromFileImage(new Posn(screenWIDTH / 2, screenHEIGHT / 2), backFileName);
        background2 = new RectangleImage(new Posn(0,0), screenWIDTH, screenHEIGHT, new White());
    }
    
    public Game2(Hero hero, 
            LinkedList<Bomb> bombs, 
            LinkedList<Explosion> explosions, 
            LinkedList<Rupees> rupees,
            LinkedList<Enemy> enemies, 
            int score, 
            int money, 
            int kills, 
            int bombN) {

        
        
    }

    public World onKeyEvent(String ke) {

        Iterator<Enemy> en = enemies.listIterator(0);

        if (ke.equals("up") || ke.equals("left") || ke.equals("right") || ke.equals("down")) {

            boolean canMove = true;
            Hero extra = this.hero.moveLink(ke);
            
            while (en.hasNext()) {
                if (en.next().collisionHuh(hero) && en.next().collisionHuh(extra)) {
                    canMove = false;
                }
                
              

            }
            if (canMove) {
                // hero = hero.moveLink(ke); //avoiding mutation
                return new Game2(this.lives,
                        this.score, this.money, this.kills, extra/*hero.moveLink(ke)*/, this.enemies, rupees);
            } else {
                return this;
            }

        } else if (ke.equals("b") && (bombs.size() < bombN)){
            return this;
        }
        return this;
    }

    public Game2 onTick() {
        LinkedList<Enemy> enList = new LinkedList<Enemy>();
        LinkedList<Rupees> rupList = new LinkedList<Rupees>();
        
        Enemy enemy = new Enemy();
        Rupees rupee = new Rupees();
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
        
        if (kills % 10 == 0) { //if num of kills is divisible by 10, add a rupee
            rupList.add(rupee);
        }

        while (yay.hasNext()) { //while yay still has next, 
            //(should always be true until world end
            Enemy enn = yay.next();
            if (enn.collisionHuh(hero)) {
                lives--;
                
            } 
            
        }
        
        while (rup.hasNext()) {
            Rupees r = rup.next();
            if (r.collectedHuh( hero )) {

                score++;
                money++;
            }
            else {
                rupList.add( r );
            }
        }
        return new Game2(this.lives,
                this.score, this.money, this.kills, this.hero, enList, rupList);
        
        // if pin.x > 1000, return bosslevel
    }

    public WorldImage makeImage() {

        Iterator<Enemy> yay = enemies.listIterator(0);
        Iterator<Rupees> rup = rupees.listIterator(0);
        WorldImage world = new OverlayImages(background,
                new OverlayImages(
                        new TextImage(new Posn(50, 20), "Lives:  " + lives,
                                20, new Black()),
                        new OverlayImages(
                                new TextImage(new Posn(150, 20), "Score:  "
                                        + score, 20, new Black()), 
                                new OverlayImages(
                                        new TextImage(new Posn(250, 20), "Money:  " + money, 20, new Black()),
                                        new OverlayImages(
                                                new TextImage(new Posn(350, 20), "Kills:  " + kills, 20, new Black()),
                                                hero.linkImage())))));


        while (yay.hasNext()) {
            world = new OverlayImages(world,
                   yay.next().enemyImage());
        }
        
        
        while (rup.hasNext()) {
            world = new OverlayImages(world,
                    rup.next().rupeeImage());
        }
        world = new OverlayImages(world, hero.linkImage());
        
        if (money == 20 && kills == 20) {
            world = new OverlayImages(world, key.keyImage());
        }
       
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
        yayRupees.add(new Rupees());
        yayRupees.add(new Rupees());
        yayRupees.add(new Rupees());
        yayRupees.add(new Rupees());
        Game2 game = new Game2(15, 0, 0, 0,
                new Hero(new Posn(screenWIDTH / 2, screenHEIGHT / 2), "linkDOWN.png"), yayNora, yayRupees);
        game.bigBang(screenWIDTH, screenHEIGHT, 0.2);
    }

}
