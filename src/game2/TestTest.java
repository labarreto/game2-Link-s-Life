/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game2;

import tester.*;
import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldimages.*;
import java.util.*;
import java.awt.Color;

public class TestTest {

    public Enemy enemy;
    public Hero hero;
    public Boss boss;
    public int score;
    public int lives;
    public int money;
    public int kills;
    public int screenWIDTH = 700;
    public int screenHEIGHT = 500;
    public LinkedList<Enemy> enemiesMT = new LinkedList();
    public LinkedList<Bomb> bombsMT = new LinkedList();
    public LinkedList<Explosion> explosionMT = new LinkedList();
    public LinkedList<Heart> rupeesMT = new LinkedList();

    public static Posn randPosn() {
        return new Posn(Utility.randInt(50, 650), Utility.randInt(50, 450));
        // to ensure that random position will not be out of bounds
    }

    public static Hero randHero() {
        return new Hero(randPosn());
    }

    public static Enemy randEnemy() {
        return new Enemy();
    }

    public static Boss randBoss() {
        return new Boss(randPosn());
    }

    public static Heart randHeart() {
        return new Heart();
    }

    public static Key randKey() {
        return new Key();
    }

    public static Bomb randBomb() {
        return new Bomb(randPosn());
    }

    public boolean testLinkMove(Tester t) {
        int x = 350;
        int y = 250;
        //all the same item with initial starting point
        //basically to test move with the reset.
        //before, when i only had one hero1, when I tested the move, it wouldactually
        //change the value of hero1, so when i tested the following directions, it would
        //not reset the hero1, but would rather look to, lets say, the down of the heroUP
        //as opposed to the down of the Hero1. 
        Hero hero1 = new Hero(new Posn(x, y));
        Hero hero2 = new Hero(new Posn(x, y));
        Hero hero3 = new Hero(new Posn(x, y));
        Hero hero4 = new Hero(new Posn(x, y));

        Hero heroUP = new Hero(new Posn(x, y - 20), "linkUP.png");
        Hero heroDOWN = new Hero(new Posn(x, y + 20), "linkDOWN.png");
        Hero heroLEFT = new Hero(new Posn(x - 20, y), "linkLEFT.png");
        Hero heroRIGHT = new Hero(new Posn(x + 20, y), "linkRIGHT.png");

        return t.checkExpect(hero1.moveLink("up"),
                heroUP, "test hero move up" + "\n")
                && t.checkExpect(hero2.moveLink("down"),
                        heroDOWN, "test hero move down" + "\n")
                && t.checkExpect(hero3.moveLink("right"),
                        heroRIGHT, "test hero move right" + "\n")
                && t.checkExpect(hero4.moveLink("left"),
                        heroLEFT, "test hero move left" + "\n");
    }

    // testing to make sure Link cannot move outofBounds (except on the right)
    public boolean testLinkOutOfBoundsMove(Tester t) {
        Hero hero1 = new Hero(new Posn(700, 250));
        Hero hero2 = new Hero(new Posn(0, 250));
        Hero hero3 = new Hero(new Posn(350, 10));
        Hero hero4 = new Hero(new Posn(350, 485));
        return t.checkExpect(hero1.moveLink("right"),
                hero1, "test hero right out of bounds" + "\n")
                && t.checkExpect(hero2.moveLink("left"),
                        hero2, "test hero left out of bounds" + "\n")
                && t.checkExpect(hero3.moveLink("up"),
                        hero3, "test hero up out of bounds"+ "\n")
                && t.checkExpect(hero4.moveLink("down"),
                        hero4, "test hero down out of bounds" + "\n");
    }

    public boolean testOnKeyEventGame2(Tester t) {
        return true;
    }

    public boolean testOnKeyEventBossLevel(Tester t) {
        return true;
    }

    //test that the Boss does not move out of bounds
    public boolean testBossOutofBounds(Tester t) {
        return true;
    }

    // test that the heart list increases when kills is divisible by certain amount
    public boolean heartIncList(Tester t) {
        return true;
    }

    // test that the bomb's timer increases on tick
    public boolean bombIncTest(Tester t) {
        return true;
    }

    // test that the explosion's timer increases on tick
    public boolean explIncTest(Tester t) {
        return true;
    }

    // test that the enemy list increases on tick when not being killed
    public boolean enemyList1(Tester t) {
        return true;
    }

    // test that the enemy list decreases when being killed
    public boolean enemyList2(Tester t) {
        return true;
    }

    // test that lives decrease when hitting an enemy
    public boolean livesDecEn(Tester t) {
        return true;
    }

    // test that lives decrease when hitting a boss
    public boolean livesDecBo(Tester t) {
        return true;
    }

    // test that lives decrease when hitting an explosion
    public boolean livesDecExplo(Tester t) {
        return true;
    }

    //test that lives increase when collecting a heart
    public boolean livesIncHeart(Tester t) {
        return true;
    }

    // test that boolean keyGrab is set to true when hero collects key
    public boolean keyGrab(Tester t) {
        return true;
    }

    // test that the boss changes direction when hitting a wall
    public boolean bossChange(Tester t) {
        return true;
    }

    // test that the bosslives decrease when hit by a bomb; 
    public boolean bossLives(Tester t) {
        return true;
    }

    // test to see if link is able to change worlds
    public boolean changingWorlds(Tester t) {
        return true;
    }

    // test to see if the size of BombList is always less than the BombNum indicated. 
    // test for Game2
    public boolean sizeofBombList(Tester t) {
        return true;
    }

    // test to see if the size of BombList is always less than the BombNum indicated. 
    // test for BossLevel
    public boolean sizeofBombList2(Tester t) {
        return true;
    }

    public static void main(String[] args) {

        Tester.runReport(new TestTest(), false, false);

    }

}
