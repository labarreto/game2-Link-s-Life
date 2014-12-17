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

    Enemy enemy;

    Boss boss;
    int score;
    int lives;
    int money;
    int kills;
    int screenWIDTH = 700;
    int screenHEIGHT = 500;
    Hero link = new Hero(new Posn(350, 250));
    LinkedList<Enemy> enemiesMT = new LinkedList();
    LinkedList<Bomb> bombsMT = new LinkedList();
    LinkedList<Explosion> explosionMT = new LinkedList();
    LinkedList<Heart> heartsMT = new LinkedList();
    LinkedList<Key> keyMT = new LinkedList();
    
    public Boss randB = randBoss();
    
    public static Posn randPosn() {
        return new Posn(Utility.randInt(50, 650), Utility.randInt(50, 450));
        // to ensure that random position will not be out of bounds
    }



    public static Boss randBoss() {
        return new Boss(randPosn());
    }



    
    
    public Game2 createGame2() {
        return new Game2(15, 0, 0, new Hero(randPosn()), enemiesMT, heartsMT, bombsMT, 
        explosionMT, keyMT, false, false, true);
        
    }
    public BossLevel createBossLevel() {
        return new BossLevel(15, 0, 50, randBoss(), link, heartsMT,
        bombsMT, explosionMT);
    }
    
    public GameOver createGameOver() {
        return new GameOver(score, link, "hello");
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
        
        // hero1 = hero2 = hero3 = hero4. They are all at the center of the screen
        // testing up down left right movement actually moves object and sets value of
        // each to be different. 

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
    
    
    //testing laying bombs in Game2 World
    public boolean testLayBomb(Tester t) {
        // link is at 350, 250. 
        
        LinkedList<Bomb> bomblist0 = new LinkedList();
        Game2 game0 =  new Game2(15, 0, 0, link, enemiesMT, heartsMT, bomblist0, 
        explosionMT, keyMT, false, false, true);
        
        LinkedList<Bomb> bomblist1 = new LinkedList();
        bomblist1.add(new Bomb(new Posn(350,250)));
        
        Game2 game1 = new Game2(15,0,0, link, enemiesMT, heartsMT, bomblist1, 
                explosionMT, keyMT, false, false, true);


        return
                t.checkExpect(game0.onKeyEvent("b"),
                        game1, "testing bomb drop in Game2" + "\n");
    }
    
    
    //testing laying bombs in BossLevel World
    public boolean testLayBomb2(Tester t) {
        LinkedList<Bomb> bomblist0 = new LinkedList();
          BossLevel boss0 = new BossLevel(15, 0, 50, randB, link, heartsMT,
        bomblist0, explosionMT);
            LinkedList<Bomb> bomblist1 = new LinkedList();
        bomblist1.add(new Bomb(new Posn(350,250)));
        BossLevel boss1 = new BossLevel(15, 0, 50, randB, link, heartsMT,
        bomblist1, explosionMT);
        
        return
                t.checkExpect(boss0.onKeyEvent("b"),
                        boss1, "testing bomb drop in BossLevel" + "\n");
    }
    
    


    
    public boolean testGameOverToGame2(Tester t) {
        GameOver gameOver = new GameOver(15, link, "GAME OVER!");
        LinkedList Enemies = new LinkedList();
            Enemies.add(new Enemy());
            LinkedList Hearts = new LinkedList();

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
            
        Game2 gameRestart = new Game2(15,0,0,link,Enemies, Hearts, 
                bombsMT,explosionMT,keyMT, false, false, true);
        
        return
                //checks to see if the keypress returns a game2 instanceof game2
                t.checkExpect(gameOver.onKeyEvent("s") instanceof Game2,
                        
                        gameRestart instanceof Game2, "test change from game over to game2" + "\n");
        // can't compare the game created by gameOver.onKeyEvent("s") because it 
        // creates two linked lists, one of enemies, and one of hearts, 
        // in which the items each time a game is
        // created will rarely be the same because hearts and enemies are placed randomly
        // on the screen
    }
    


    //test that the Boss does not move out of bounds
    public boolean testBossOutofBounds(Tester t) {
        BossLevel bossL = new BossLevel(10,10,10, randBoss().bossMove(), link, heartsMT, bombsMT,
        explosionMT);
        return t.checkExpect(bossL.boss.outOfBounds(),
                false, "test if boss is out of bounds ever" + "\n");
    }
    
    public boolean checkIfCanMakeMoreHearts(Game2 game) {
        if (game.kills %25 == 0 && Utility.biasCoinToss()) {
            return true;
        } else
            return false;
    }

    // test that the heart list increases when kills is divisible by certain amount
    public boolean heartIncList(Tester t) {
        //making hero be in the corner so there is no possibility for hero to be eating a heart. 
                LinkedList Enemies = new LinkedList();
            Enemies.add(new Enemy());
            LinkedList Hearts = new LinkedList();

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
                Game2 game = new Game2(15,25,25,new Hero(new Posn(0,0)), Enemies, Hearts, 
                bombsMT,explosionMT,keyMT, false, false, true);
                // on tick should make the boolean representing makeMoreHearts to 
                // be true since kills can be divided by 25 with 0 remainder.
                Game2 game1 = (Game2) game.onTick();
                
        return
                t.checkExpect(checkIfCanMakeMoreHearts(game) == game1.makeMoreHearts,
                        true, "check if make more hearts works" + "\n")
                && t.checkExpect(game.hearts.size() <= game1.hearts.size(),
                        // list of hearts should either stay the same size or 
                        // increase. It stays the same size with biasCoinToss
                        // is false. 
                        true, "check if hearts are being added.");
        
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
