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
public class BossLevel extends World {

    static int screenWIDTH = 700;
    static int screenHEIGHT = 500;
    String backFileName = new String("background.png");
    WorldImage background;
    int lives;
    int score;
    int kills;
    int bosslives;
    int bombN;

    Boss boss;
    Hero hero;

    LinkedList<Heart> hearts;
    LinkedList<Enemy> enemies;
    LinkedList<Bomb> bombs;
    LinkedList<Explosion> explosions;
    LinkedList<Key> key;

    Boolean makeMoreHearts;

    Boolean shouldKeyAppear;
    Boolean keyGrabbed;

    public BossLevel(int lives, int score, int bosslives, Boss boss, Hero hero,
            LinkedList<Heart> hearts, LinkedList<Bomb> bombs,
            LinkedList<Explosion> explosions, LinkedList<Key> key,
            Boolean makeMoreHearts, Boolean shouldKeyAppear, Boolean keyGrabbed) {

        this.lives = lives;
        this.score = score;
        this.bosslives = bosslives;
        this.boss = boss;
        this.hero = hero;
        this.hearts = hearts;
        this.bombs = bombs;
        this.explosions = explosions;
        this.key = key;
        this.makeMoreHearts = makeMoreHearts;
        this.shouldKeyAppear = shouldKeyAppear;
        this.keyGrabbed = keyGrabbed;
    }

    public World onKeyEvent(String ke) {

        if (ke.equals("up") || ke.equals("left") || ke.equals("right") || ke.equals("down")) {

            boolean canMove = true;
            Hero extra = this.hero.moveLink(ke);

            if (boss.collisionHuh(extra)) {
                canMove = false;

            }
            if (canMove) {
                // hero = hero.moveLink(ke); //avoiding mutation

                return new BossLevel(this.lives, this.score, this.bosslives,
                        boss, extra, hearts, bombs, explosions, key,
                        makeMoreHearts, keyGrabbed, shouldKeyAppear);

            } else {
                return this;
            }

        } else if (ke.equals("b") && (bombs.size() < bombN)) {

            bombs.add(new Bomb(hero.pin));
            return new BossLevel(lives, score, bosslives, boss, hero,
                    hearts, bombs, explosions, key, makeMoreHearts, keyGrabbed,
                    shouldKeyAppear);
        }
        return this;
    }

    public World onTick() {
        LinkedList<Heart> heartList = new LinkedList();

        LinkedList<Explosion> nExplosionList = new LinkedList();

        LinkedList<Bomb> newBombList = new LinkedList();
        Iterator<Bomb> bi = bombs.listIterator(0);

        Iterator<Heart> hrt = hearts.listIterator(0);
        Heart heart = new Heart();

        Iterator<Explosion> ei = explosions.listIterator(0);

        LinkedList<Key> k = new LinkedList();
        Iterator<Key> ki = key.listIterator(0);
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
        Boss newBoss = boss.randomMove(50);

        //set explosion iterator back to 0 index
        ei = nExplosionList.listIterator(0);

        if (boss.collisionHuh(hero)) {
            lives--;
        }
        while (ei.hasNext()) {
            Explosion e = ei.next();
            if (e.explodingBoss(boss)) {

                kills++;
                score++;
                bosslives--;
            }
        }

        ei = nExplosionList.listIterator(0);

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

        if (bosslives == 0 && shouldKeyAppear) {

            shouldKeyAppear = false;
            k.add(new Key());
            System.out.println("created key");

        }

        while (ki.hasNext()) {
            Key key0 = ki.next();

            k.add(key0);

            if (key0.collectedHuh(hero)) {
                System.out.println("removed key");
                k.remove(key0);
                keyGrabbed = true;
            }

        }
//       int outBoundsRight = 700;
//       if (hero.pin.x >= outBoundsRight && keyGrabbed) {
//           return new BossLevel(. . . .to be filled out soon);
//       } else { 
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

        if (hero.pin.x < 0 && keyGrabbed) {
            return new Game2(this.lives, this.score, 0, hero,
                    Enemies, Hearts, Bombs, Explosions, Key, false, false, true);
        } else {

            return new BossLevel(this.lives, this.score, this.bosslives, this.boss,
                    this.hero, heartList, newBombList, nExplosionList, k,
                    makeMoreHearts, this.shouldKeyAppear, this.keyGrabbed);

        }
    }

    public WorldImage makeImage() {

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
                                        new TextImage(new Posn(250, 20),
                                                "Has Key:  " + keyGrabbed,
                                                20, new Black()),
                                        new OverlayImages(
                                                new TextImage(new Posn(350, 20),
                                                        "Kills:  " + kills,
                                                        20, new Black()),
                                                
                                                        new TextImage(new Posn(450, 20),
                                                                "BossLives:  " + bosslives,
                                                                20, new Black()))))));

        world = new OverlayImages(world,
                boss.bossImage());

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
            world = new OverlayImages(world,
                    k.next().keyImage());
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

}
