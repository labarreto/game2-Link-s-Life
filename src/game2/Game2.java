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

    WorldImage background = new FromFileImage(new Posn(screenWIDTH / 2, screenHEIGHT / 2), backFileName);
    ;
    WorldImage background2;
    LinkedList<Rupees> rupees;
    LinkedList<Enemy> enemies;
    LinkedList<Bomb> bombs;
    LinkedList<Explosion> explosions;

    public Game2(int lives, int score, int money, int kills, Hero hero,
            LinkedList<Enemy> enemies, LinkedList<Rupees> rupees,
            LinkedList<Bomb> bombs, LinkedList<Explosion> explosions) {

        this.hero = hero;
        this.rupees = rupees;
        this.enemies = enemies;
        this.bombs = bombs;
        this.explosions = explosions;
        this.lives = lives;
        this.score = score;
        this.money = money;
        this.kills = kills;
        this.bombN = 15;

    }

    public Game2(Hero hero,
            LinkedList<Enemy> enemies, LinkedList<Rupees> rupees,
            LinkedList<Bomb> bombs, LinkedList<Explosion> explosions) {
        this.hero = hero;
        this.rupees = rupees;
        this.bombs = bombs;
        this.explosions = explosions;
        this.enemies = enemies;
        this.score = 0;
        this.lives = 15;
        this.money = 0;
        this.kills = 0;
        this.bombN = 15;
    }

    public Game2(Hero hero,
            LinkedList<Bomb> bombs,
            LinkedList<Explosion> explosions,
            LinkedList<Rupees> rupees,
            LinkedList<Enemy> enemies,
            int lives,
            int score,
            int money,
            int kills,
            int bombN) {

        this.hero = hero;
        this.bombs = bombs;
        this.explosions = explosions;
        this.rupees = rupees;
        this.enemies = enemies;
        this.lives = lives;
        this.score = score;
        this.money = money;
        this.kills = kills;
        this.bombN = bombN;

    }

    public World onKeyEvent(String ke) {
        
        Iterator<Enemy> en = enemies.listIterator(0);

        if (ke.equals("up") || ke.equals("left") || ke.equals("right") || ke.equals("down")) {

            boolean canMove = true;
            Hero extra = this.hero.moveLink(ke);

            while (en.hasNext() && enemies.size() > 0) {
                if (en.next().collisionHuh(extra)) {
                    canMove = false;
                }

            }
            if (canMove) {
                // hero = hero.moveLink(ke); //avoiding mutation
                return new Game2(this.lives,
                        this.score, this.money, this.kills, extra/*hero.moveLink(ke)*/, this.enemies, rupees, this.bombs, this.explosions);
            } else {
                return this;
            }

        } else if (ke.equals("b") && (bombs.size() < bombN)) {

            bombs.add(new Bomb(hero.pin));
            return new Game2(hero, bombs, explosions, rupees, enemies,
                    lives, score, money, kills, bombN);
        }
        return this;
    }

    public Game2 onTick() {
        LinkedList<Enemy> enList = new LinkedList<Enemy>();
        LinkedList<Rupees> rupList = new LinkedList<Rupees>();
        LinkedList<Bomb> bombList = new LinkedList<Bomb>();
        LinkedList<Explosion> explosionList = new LinkedList<Explosion>();

        Hero hero2 = hero;

        LinkedList<Enemy> EnemyList = new LinkedList();
        LinkedList<Enemy> nEnemyList = new LinkedList();
        Iterator<Enemy> en = enemies.listIterator(0);
        LinkedList<Explosion> nExplosionList = new LinkedList();
        LinkedList<Bomb> bb = new LinkedList();

        Enemy enemy = new Enemy();
        Rupees rupee = new Rupees();
        
        Iterator<Bomb> bi = bombs.listIterator(0);
        Iterator<Explosion> ei = explosions.listIterator(0);
        LinkedList<Bomb> newBombList = new LinkedList();
        
        
        while (bi.hasNext()) {
            
            Bomb b = bi.next();
            Bomb s = b.incTime();
            newBombList.add(b.incTime());
            
        }
        
        Iterator<Bomb> nbi = newBombList.listIterator(0);
         
        while (nbi.hasNext()) {
            Bomb b = nbi.next();

            if (b.canIExplode()) {
                
                //Posn bp = bombList.removeFirst().pin;
                
                
                nExplosionList.add(new Explosion(b.pin)); 
                
            } else
                bb.add(b);
        }
       
        ei = explosionList.listIterator(0);
        bi = bombList.listIterator(0);
        LinkedList<Bomb> nBombList = new LinkedList();
        ei = explosionList.listIterator(0);


        while (bi.hasNext()) {
            Bomb bomb0 = bi.next();
            nBombList.add(bomb0.incTime());

            while (ei.hasNext()) {
                Explosion e = ei.next();

                if (e.explodingBomb(bomb0)) {
                    nBombList.removeLast();
                    nBombList.add(new Bomb(bomb0.pin, bomb0.time + 10));
                }
            }
            ei = explosionList.listIterator(0);
        }

        if (Utility.biasCoinToss()) {
            enList.add(new Enemy());
        }

        Iterator<Enemy> yay = enemies.listIterator(0);
        Iterator<Rupees> rup = rupees.listIterator(0);

        while (yay.hasNext()) {
            Enemy newEn = yay.next().moveEnemy();
            enList.add(newEn);

        }

        yay = enList.listIterator(0);

        if (kills % 10 == 0 && kills != 0) { //if num of kills is divisible by 10, add a rupee
            rupList.add(rupee);
        }

        while (yay.hasNext()) { //while yay still has next, 
            //(should always be true until world end
            Enemy enn = yay.next();
            EnemyList.add(enn);
            if (enn.collisionHuh(hero)) {
                lives--;
            }
            while (ei.hasNext()) {
                Explosion e = ei.next();
                if (enn.explodingHuh(e)) {
                 
                    kills++;
                    score++;
                    EnemyList.remove(enn);

                }
            }
            ei = explosionList.listIterator(0);

        }

        while (rup.hasNext()) {
            Rupees r = rup.next();
            if (r.collectedHuh(hero)) {

                money++;
            } else {
                rupList.add(r);
            }
            
            
        }
        
        while (nExplosionList.size() > 0 && (nExplosionList.element().time >= 5)) {
            nExplosionList.removeFirst();
        }
        return new Game2(this.lives,
                this.score, this.money, this.kills, this.hero, enList, rupList, bb, nExplosionList);

        // if pin.x > 1000 && key collected, return bosslevel
    }

    public WorldImage makeImage() {

        Iterator<Enemy> yay = enemies.listIterator(0);
        Iterator<Rupees> rup = rupees.listIterator(0);
        Iterator<Bomb> b = bombs.listIterator(0);
        Iterator<Explosion> e = explosions.listIterator(0);
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

        while (b.hasNext()) {
            world = new OverlayImages(world,
                    b.next().bombImage());
        }

        while (e.hasNext()) {
            world = new OverlayImages(world,
                    e.next().explosionImage());
        }
        world = new OverlayImages(world, hero.linkImage());

        if (money == 20 && kills == 20) {
            world = new OverlayImages(world, key.keyImage());
        }

        return world;
    }

    public WorldEnd worldEnds() {
        if (lives < 1) {
            System.out.println("lives:  " + lives);
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
        LinkedList yayEn = new LinkedList();
        yayEn.add(new Enemy());
        LinkedList yayRupees = new LinkedList();
        LinkedList yayBombs = new LinkedList();
        LinkedList yayExplosions = new LinkedList();
        yayRupees.add(new Rupees());
        yayRupees.add(new Rupees());
        yayRupees.add(new Rupees());
        yayRupees.add(new Rupees());
        yayRupees.add(new Rupees());
        yayRupees.add(new Rupees());
        yayRupees.add(new Rupees());
        yayRupees.add(new Rupees());
        Game2 game = new Game2(15, 0, 0, 0,
                new Hero(new Posn(screenWIDTH / 2, screenHEIGHT / 2), "linkDOWN.png"), yayEn, yayRupees, yayBombs, yayExplosions);
        game.bigBang(screenWIDTH, screenHEIGHT, 0.1);
    }

}
