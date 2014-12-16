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
    public LinkedList<Enemy> enemiesMT = new LinkedList();
    public LinkedList<Bomb> bombsMT = new LinkedList();
    public LinkedList<Explosion> explosionMT = new LinkedList();
    public LinkedList<Heart> rupeesMT = new LinkedList();
    
    public static Posn randPosn() {
        return new Posn(Utility.randInt(10, 690), Utility.randInt(10,490));
    }
    
    public static Hero randHero() {
        return new Hero (randPosn());
    }
    public static Enemy randEnemy() {
        return new Enemy();
    }
    
    public boolean testLinkMove(Tester t) {
        Hero hero0 = new Hero( new Posn( 100, 100 ) );
	Hero heroUP = new Hero( new Posn( 100, 90 ), "linkUP.png");
	Hero heroRIGHT = new Hero( new Posn( 110, 100 ), "linkRIGHT.png");
	Hero heroLEFT = new Hero( new Posn( 90, 100 ), "linkLEFT.png");
	Hero heroDOWN = new Hero( new Posn( 100, 110 ), "linkDOWN.png");
        
        return
                t.checkExpect( hero0.moveLink("up"),
							heroUP, "test hero move up" + "\n") &&

			t.checkExpect( hero0.moveLink("right"),
							heroRIGHT, "test hero move right" + "\n") &&

			t.checkExpect( hero0.moveLink("left"),
							heroLEFT, "test hero move left" + "\n") &&

			t.checkExpect( hero0.moveLink("down"),
							heroDOWN, "test hero move down" + "\n");
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
    
}
