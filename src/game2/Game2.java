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
    int lives;
    int score;
    int money;
    int kills;
    int bombN;
    Boolean keyAppear;
    Boolean makeMoreHearts;
    WorldImage background = new FromFileImage(new Posn(screenWIDTH / 2, screenHEIGHT / 2), backFileName);
    ;
    WorldImage background2;
    LinkedList<Heart> hearts;
    LinkedList<Enemy> enemies;
    LinkedList<Bomb> bombs;
    LinkedList<Explosion> explosions;
    LinkedList<Key> key; // should only ever be size 1 or 0. 

    public Game2(int lives, int score, int money, int kills, Hero hero,
            LinkedList<Enemy> enemies, LinkedList<Heart> hearts,
            LinkedList<Bomb> bombs, LinkedList<Explosion> explosions, LinkedList<Key> key, Boolean keyAppear, Boolean makeMoreHearts) {

        this.hero = hero;
        this.hearts = hearts;
        this.enemies = enemies;
        this.bombs = bombs;
        this.explosions = explosions;
        this.lives = lives;
        this.score = score;
        this.money = money;
        this.kills = kills;
        this.bombN = 15;
        this.key = key; //linked list
        this.keyAppear = keyAppear;
        this.makeMoreHearts = makeMoreHearts;

    }

    public Game2(Hero hero,
            LinkedList<Bomb> bombs,
            LinkedList<Explosion> explosions,
            LinkedList<Heart> hearts,
            LinkedList<Enemy> enemies,
            int lives,
            int score,
            int money,
            int kills,
            int bombN,
            LinkedList<Key> key,
            Boolean keyAppear,
            Boolean makeMoreHearts) {

        this.hero = hero;
        this.bombs = bombs;
        this.explosions = explosions;
        this.hearts = hearts;
        this.enemies = enemies;
        this.lives = lives;
        this.score = score;
        this.money = money;
        this.kills = kills;
        this.bombN = bombN;
        this.key = key;
        this.keyAppear = keyAppear;
        this.makeMoreHearts = makeMoreHearts;

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
                        this.score, this.money, this.kills, extra/*hero.moveLink(ke)*/,
                        this.enemies, hearts, this.bombs, this.explosions, this.key,
                        this.keyAppear, this.makeMoreHearts);
            } else {
                return this;
            }

        } else if (ke.equals("b") && (bombs.size() < bombN)) {

            bombs.add(new Bomb(hero.pin));
            return new Game2(hero, bombs, explosions, hearts, enemies,
                    lives, score, money, kills, bombN, key, keyAppear, makeMoreHearts);
        }
        return this;
    }

    public Game2 onTick() {

        LinkedList<Heart> heartList = new LinkedList();

        LinkedList<Explosion> nExplosionList = new LinkedList();

        LinkedList<Bomb> newBombList = new LinkedList();
        Iterator<Bomb> bi = bombs.listIterator(0);

        LinkedList<Enemy> enList = new LinkedList();
        Iterator<Enemy> origEn = enemies.listIterator(0);

        Iterator<Heart> hrt = hearts.listIterator(0);
        Heart heart = new Heart();

        Iterator<Explosion> ei = explosions.listIterator(0);

        LinkedList<Key> k = new LinkedList();
        Iterator<Key> ki = key.listIterator(0);
        Key ke = new Key();
        //iterating through "bombs" linked list to increase the time on them. 
        while (bi.hasNext()) {

            Bomb b = bi.next();

            if (b.canIExplode()) {
                nExplosionList.add(new Explosion(b.pin));

            } else {

                newBombList.add(b.incTime());
            }
        }

        bi = bombs.listIterator(0);

        while (ei.hasNext()) {
            Explosion explosion0 = ei.next();
            // increase the time of the explosion so that once the time is greater
            // than 20, the explosion will disappear in the while loop following
            // this while loop. 
            nExplosionList.add(explosion0.incTime());
        }

        while (nExplosionList.size() > 0 && (nExplosionList.element().time >= 15)) {
            nExplosionList.removeFirst();
        }

        ei = nExplosionList.listIterator(0);
        while (ei.hasNext()) {
            if (ei.next().explodingHero(hero)) {
                lives--;
            }
        }
        if (Utility.biasCoinToss()) {
            enList.add(new Enemy());
        }

        while (origEn.hasNext()) {
            Enemy newEn = origEn.next().moveEnemy();
            enList.add(newEn);

        }
        //set explosion iterator back to 0 index
        ei = nExplosionList.listIterator(0);
        Iterator<Enemy> enemI = enList.listIterator(0);

        LinkedList<Enemy> nme = new LinkedList();

        Boolean isExploding;
        //checking if enemy is exploding
        while (enemI.hasNext()) {
            //while yay still has next, 
            //(should always be true until world end

            Enemy enn = enemI.next();

            isExploding = false;

            if (enn.collisionHuh(hero)) {
                lives--;
            }
            while (ei.hasNext()) {
                Explosion e = ei.next();
                if (enn.explodingHuh(e)) {
                    isExploding = true;
                    kills++;
                    score++;
                }
            }

            if (!isExploding) {
                nme.add(enn);
            }

            ei = nExplosionList.listIterator(0);

        }

        //checking when to add heart to screen
        if ((kills % 25 == 0) && (kills != 0)) {
            //if num of kills is divisible by 10, add a heart
            makeMoreHearts = true;
            if (Utility.biasCoinToss()) {
                heartList.add(heart);

            }
        }
        makeMoreHearts = false;
        while (hrt.hasNext()) {
            Heart r = hrt.next();
            if (r.collectedHuh(hero)) {

                lives++;
            } else {
                heartList.add(r);
            }

        }

        if (kills >= 1 && score >= 1) {
            keyAppear = true;
            if (!(k.size() > 0)) {
                k.add(ke);

            }
        }

        while (ki.hasNext()) {
            Key key0 = ki.next();

            if (key0.collectedHuh(hero)) {

                k.remove(key0);
            }

        }

        return new Game2(this.lives,
                this.score, this.money, this.kills, this.hero,
                nme, heartList, newBombList, nExplosionList, k, keyAppear,
                makeMoreHearts);

        // if pin.x > 1000 && key collected, return bosslevel
    }

    public WorldImage makeImage() {

        Iterator<Enemy> en = enemies.listIterator(0);
        Iterator<Heart> hrt = hearts.listIterator(0);
        Iterator<Bomb> b = bombs.listIterator(0);
        Iterator<Explosion> e = explosions.listIterator(0);
        Iterator<Key> k = key.listIterator(0);
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

        while (en.hasNext()) {
            world = new OverlayImages(world,
                    en.next().enemyImage());
        }

        while (hrt.hasNext()) {
            world = new OverlayImages(world,
                    hrt.next().heartImage());
        }

        while (b.hasNext()) {
            world = new OverlayImages(world,
                    b.next().bombImage());
        }

        while (e.hasNext()) {
            world = new OverlayImages(world,
                    e.next().explosionImage());
        }

        while (k.hasNext()) {
            world = new OverlayImages(world, k.next().keyImage());
        }

        world = new OverlayImages(world, hero.linkImage());

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
        LinkedList Enemies = new LinkedList();
        Enemies.add(new Enemy());
        LinkedList Hearts = new LinkedList();
        LinkedList Bombs = new LinkedList();
        LinkedList Explosions = new LinkedList();
        LinkedList Key = new LinkedList();
        Hearts.add(new Heart());
        Hearts.add(new Heart());
        Hearts.add(new Heart());
        Hearts.add(new Heart());
        Hearts.add(new Heart());
        Hearts.add(new Heart());
        Hearts.add(new Heart());
        Hearts.add(new Heart());
        Hearts.add(new Heart());
        Hearts.add(new Heart());
        Game2 game = new Game2(15, 0, 0, 0,
                new Hero(new Posn(screenWIDTH / 2, screenHEIGHT / 2),
                        "linkDOWN.png"), Enemies, Hearts, Bombs,
                Explosions, Key, false, false);

        game.bigBang(screenWIDTH, screenHEIGHT, 0.1);
    }

}
