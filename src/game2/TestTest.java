/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game2;

import tester.*;
import javalib.worldimages.*;
import java.util.*;

public class TestTest {

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
                        hero3, "test hero up out of bounds" + "\n")
                && t.checkExpect(hero4.moveLink("down"),
                        hero4, "test hero down out of bounds" + "\n");
    }

    //testing laying bombs in Game2 World
    public boolean testLayBomb(Tester t) {
        // link is at 350, 250. 

        LinkedList<Bomb> bomblist0 = new LinkedList();
        Game2 game0 = new Game2(15, 0, 0, link, enemiesMT, heartsMT, bomblist0,
                explosionMT, keyMT, false, false, true);

        LinkedList<Bomb> bomblist1 = new LinkedList();
        bomblist1.add(new Bomb(new Posn(350, 250)));

        Game2 game1 = new Game2(15, 0, 0, link, enemiesMT, heartsMT, bomblist1,
                explosionMT, keyMT, false, false, true);

        return t.checkExpect(game0.onKeyEvent("b"),
                game1, "testing bomb drop in Game2" + "\n");
    }

    //testing laying bombs in BossLevel World
    public boolean testLayBomb2(Tester t) {
        LinkedList<Bomb> bomblist0 = new LinkedList();
        BossLevel boss0 = new BossLevel(15, 0, 50, randB, link, heartsMT,
                bomblist0, explosionMT);
        LinkedList<Bomb> bomblist1 = new LinkedList();
        bomblist1.add(new Bomb(new Posn(350, 250)));
        BossLevel boss1 = new BossLevel(15, 0, 50, randB, link, heartsMT,
                bomblist1, explosionMT);

        return t.checkExpect(boss0.onKeyEvent("b"),
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

        Game2 gameRestart = new Game2(15, 0, 0, link, Enemies, Hearts,
                bombsMT, explosionMT, keyMT, false, false, true);

        return //checks to see if the keypress returns a game2 instanceof game2
                t.checkExpect(gameOver.onKeyEvent("s") instanceof Game2,
                        gameRestart instanceof Game2, "test change from game over to game2" + "\n");
        // can't compare the game created by gameOver.onKeyEvent("s") because it 
        // creates two linked lists, one of enemies, and one of hearts, 
        // in which the items each time a game is
        // created will rarely be the same because hearts and enemies are placed randomly
        // on the screen
    }

    public boolean testGame2toGameOver(Tester t) {

        Game2 game = new Game2(0, 15, 15, link, enemiesMT, heartsMT,
                bombsMT, explosionMT, keyMT, false, false, true);
        GameOver gameOver = new GameOver(0, link, "GAME OVER!");

        return t.checkExpect(game.onTick(),
                gameOver, "test if game over is created with Game2 lose" + "\n");

    }

    public boolean testBossLevelLosetoGameOver(Tester t) {

        BossLevel boss1 = new BossLevel(0, 0, 50, randB, link, heartsMT,
                bombsMT, explosionMT);
        GameOver gameOver = new GameOver(0, link, "GAME OVER!");

        return t.checkExpect(boss1.onTick(),
                gameOver, "test if game over is created with BossLevel lose" + "\n");

    }

    public boolean testBossLevelWintoGameOver(Tester t) {

        BossLevel boss1 = new BossLevel(15, 0, 0, randB, link, heartsMT,
                bombsMT, explosionMT);
        GameOver gameOver = new GameOver(0, link, "YOU WIN! CONGRATS!");

        return t.checkExpect(boss1.onTick(),
                gameOver, "test if game over is created with BossLevel win" + "\n");

    }

    //test that the Boss does not move out of bounds
    public boolean testBossOutofBounds(Tester t) {
        Boss boss = new Boss();
        BossLevel bossL = new BossLevel(10, 10, 10, boss, link, heartsMT, bombsMT,
                explosionMT);
        return t.checkExpect(((BossLevel) bossL.onTick()).boss.outOfBounds(),
                false, "test if boss is out of bounds ever" + "\n");
    }

//    public boolean checkIfCanMakeMoreHearts(Game2 game) {
//        if (game.kills %25 == 0) {
//            return true;
//        } else
//            return false;
//    }
    // test that the heart list increases when kills is divisible by certain amount
    public boolean testheartIncList(Tester t) {
        //making hero be in the corner so there is no possibility for hero to be eating a heart. 
        Game2 game = new Game2(15, 25, 25, new Hero(new Posn(0, 0)), enemiesMT, heartsMT,
                bombsMT, explosionMT, keyMT, false, false, true);
        // on tick should make the boolean representing makeMoreHearts to 
        // be true since kills can be divided by 25 with 0 remainder.
        Game2 game1 = (Game2) game.onTick();
        return t.checkOneOf(game.kills % 20 == 0,
                game1.makeMoreHearts, "check if make more hearts works" + "\n")
                && t.checkExpect(game.hearts.size() <= game1.hearts.size(),
                        // list of hearts should either stay the same size or 
                        // increase. It stays the same size with biasCoinToss
                        // is false. 
                        true, "check if hearts are being added." + "\n");
    }

    // test that the bomb's timer increases on tick
    public boolean testbombIncTestGame(Tester t) {
        LinkedList<Bomb> bomblist1 = new LinkedList();

        Bomb bomb1 = new Bomb(new Posn(100, 200), 0);

        bomblist1.add(bomb1);

        Game2 game1 = new Game2(15, 0, 0, link, enemiesMT, heartsMT,
                bomblist1, explosionMT, keyMT, false, false, true);

        Bomb bombB = game1.bombs.element();
        Bomb bombA = ((Game2) game1.onTick()).bombs.element();
        int timeB = bombB.time + 1;
        int timeA = bombA.time;

        return t.checkExpect(timeB, timeA);
    }

//    public boolean testbombIncTestBL(Tester t) {
//        LinkedList<Bomb> bomblist1 = new LinkedList();
//        Bomb bomb1 = new Bomb(new Posn(100, 200), 0);
//
//        bomblist1.add(bomb1);
//
//        Boss boss = new Boss(new Posn(100, 100));
//
//        BossLevel bl = new BossLevel(15, 0, 50, boss, link, heartsMT, bomblist1, explosionMT);
//
//        Bomb bombB = bl.bombs.element();
//        Bomb bombA = ((BossLevel) bl.onTick()).bombs.element();
//        int timeB = bombB.time + 1;
//        int timeA = bombA.time;
//
//        return t.checkExpect(timeB, timeA);
//    }

    // test that the explosion's timer increases on tick
    public boolean testexplIncGameTest(Tester t) {
        LinkedList<Explosion> explosionlist1 = new LinkedList();
        LinkedList<Explosion> explosionlist2 = new LinkedList();
        LinkedList<Explosion> explosionlist3 = new LinkedList();

        Explosion explosion1 = new Explosion(new Posn(100, 200), 0);

        explosionlist1.add(explosion1);

        Game2 game1 = new Game2(15, 0, 0, link, enemiesMT, heartsMT,
                bombsMT, explosionlist1, keyMT, false, false, true);

        Explosion expB = game1.explosions.element();
        int timeB = expB.time + 1;
        Explosion expA = ((Game2) game1.onTick()).explosions.element();
        int timeA = expA.time;

        return t.checkExpect(timeB, timeA);
    }

//    public boolean testexplIncBLTest(Tester t)  {
//        LinkedList<Explosion> explosionlist1 = new LinkedList();
//
//        Explosion explosion1 = new Explosion(new Posn(100, 200), 0);
//
//        explosionlist1.add(explosion1);
//
//        Boss boss = new Boss(new Posn(100, 100));
//
//        BossLevel bl = new BossLevel(15, 50, 50, boss, link, heartsMT, bombsMT, explosionlist1);
//        Explosion expB = bl.explosions.element();
//        int timeB = expB.time + 1;
//        Explosion expA = ((BossLevel) bl.onTick()).explosions.element();
//        int timeA = expA.time;
//
//        return t.same(timeB, timeA);
//    }



//    public boolean testbombToExplGameTest(Tester t) {
//        LinkedList<Bomb> bomblist1 = new LinkedList();
//        LinkedList<Bomb> bomblist2 = new LinkedList();
//        LinkedList<Bomb> bomblist3 = new LinkedList();
//        LinkedList<Bomb> bomblist4 = new LinkedList();
//
//        Bomb bomb1 = new Bomb(new Posn(100, 200), 0);
//        Bomb bomb2 = new Bomb(new Posn(100, 200), 1);
//        Bomb bomb3 = new Bomb(new Posn(100, 200), 2);
//        Bomb bomb4 = new Bomb(new Posn(100, 200), 10);
//        bomblist1.add(bomb1);
//        bomblist2.add(bomb2);
//        bomblist3.add(bomb3);
//        bomblist4.add(bomb4);
//
//        LinkedList<Explosion> explosionlist4 = new LinkedList();
//
//        LinkedList<Explosion> explosionlist1 = new LinkedList();
//
//        Explosion explosion4 = new Explosion(new Posn(100, 200), 0);
//        Explosion explosion1 = new Explosion(new Posn(100, 200), 15);
//        explosionlist1.add(explosion1);
//        explosionlist4.add(explosion4);
//
//        Game2 game1 = new Game2(15, 0, 0, link, enemiesMT, heartsMT,
//                bomblist4, explosionMT, keyMT, false, false, true);
//        Game2 game2 = new Game2(15, 0, 0, link, enemiesMT, heartsMT,
//                bombsMT, explosionlist4, keyMT, false, false, true);
//
//
//        return t.checkExpect(game1.onTick(),
//                game2, "test if bomb lists and explosion lists act correctly" + "\n");
//    }
    
    
    

    // test that the enemy moves on tick
    public boolean testenemyList1(Tester t) {
        Enemy enemy1 = new Enemy();
        Enemy enemy2 = enemy1.moveEnemy();

        LinkedList<Enemy> enemyList1 = new LinkedList();
        enemyList1.add(enemy1);
        LinkedList<Enemy> enemyList2 = new LinkedList();
        enemyList2.add(enemy2);

        Game2 game1 = new Game2(15, 15, 15, link, enemyList1, heartsMT, bombsMT, explosionMT, keyMT,
                false, false, true);
        Game2 game2 = new Game2(15, 15, 15, link, enemyList2, heartsMT, bombsMT, explosionMT, keyMT,
                false, false, true);

        return t.checkExpect(game1.onTick(),
                game2, "testing the movement of an enemy object" + "\n");
    }

    public boolean testHeroHitGame(Tester t) {
        LinkedList<Enemy> enemyList1 = new LinkedList();
        Enemy enemy1 = new Enemy(new Posn(350, 250), true);
        enemyList1.add(enemy1);
        Game2 game1 = new Game2(15, 15, 15, link, enemyList1, heartsMT, bombsMT, explosionMT, keyMT,
                false, false, true);

        Game2 game2 = new Game2(14, 15, 15, link, enemyList1, heartsMT, bombsMT, explosionMT, keyMT,
                false, false, true);

        return t.checkExpect(enemy1.collisionHuh(link),
                true, "checking if collision with enemy is true" + "\n")
                && t.checkExpect(((Game2) game1.onTick()).lives == game2.lives,
                        true, "checking that lives decrease when hero hits enemy" + "\n");
    }
    

    public boolean testHeroHitBL(Tester t) {
        Boss boss = new Boss(new Posn(345, 245));
        BossLevel bl1 = new BossLevel(15, 0, 50, randB, link, heartsMT,
                bombsMT, explosionMT);
        return t.checkExpect(boss.collisionHuh(link),
                true, "checking if collision with boss is true" + "\n")
                && t.checkExpect(bl1.lives >= ((BossLevel) bl1.onTick()).lives,
                        true, "checking if lives before tick is more than lives after" + "\n");

    }
    
//    public boolean testBombToExplBL(Tester t) {
//        LinkedList<Bomb> bomblist1 = new LinkedList();
//        Bomb bomb1 = new Bomb(new Posn(100, 200), 10);
//        bomblist1.add(bomb1);
//        Hero hero = new Hero(new Posn(40,80));
//
//        LinkedList<Explosion> explosionlist1 = new LinkedList();
//        Explosion explosion1 = new Explosion(new Posn(100, 200), 0);
//        explosionlist1.add(explosion1);
//
//        Boss boss = new Boss(new Posn(100, 100));
//        Boss boss2 = new Boss(new Posn(125, 100), 0);
//        Boss boss3 = new Boss(new Posn(125, 75), 1);
//        Boss boss4 = new Boss(new Posn(100, 75), 2);
//        Boss boss5 = new Boss(new Posn(75, 75), 3);
//        Boss boss6 = new Boss(new Posn(75, 100), 4);
//        Boss boss7 = new Boss(new Posn(75, 125), 5);
//        Boss boss8 = new Boss(new Posn(100, 125), 6);
//        Boss boss9 = new Boss(new Posn(125, 125), 7);
//
//        BossLevel bl = new BossLevel(15, 50, 50, boss, hero, heartsMT, bomblist1, explosionMT);
//
//        BossLevel bl0 = new BossLevel(15, 50, 50, boss2, hero, heartsMT,
//                bombsMT, explosionlist1);
//
//        BossLevel bl1 = new BossLevel(15, 50, 50, boss3, hero, heartsMT,
//                bombsMT, explosionlist1);
//
//        BossLevel bl2 = new BossLevel(15, 50, 50, boss4, hero, heartsMT,
//                bombsMT, explosionlist1);
//
//        BossLevel bl3 = new BossLevel(15, 50, 50, boss5, hero, heartsMT,
//                bombsMT, explosionlist1);
//        BossLevel bl4 = new BossLevel(15, 50, 50, boss6, hero, heartsMT,
//                bombsMT, explosionlist1);
//        BossLevel bl5 = new BossLevel(15, 50, 50, boss7, hero, heartsMT,
//                bombsMT, explosionlist1);
//        BossLevel bl6 = new BossLevel(15, 50, 50, boss8, hero, heartsMT,
//                bombsMT, explosionlist1);
//        BossLevel bl7 = new BossLevel(15, 50, 50, boss9, hero, heartsMT,
//                bombsMT, explosionlist1);
//
//        return t.checkOneOf("check to see if changed to explosion", bl.onTick(),
//                bl1, bl2, bl3, bl4, bl5, bl6, bl7);
//    }

    // test that the enemy list decreases when being killed
    public boolean testenemyList2(Tester t) {
        LinkedList<Explosion> explosionL = new LinkedList();
        Explosion explosion = new Explosion(new Posn(250, 200));
        LinkedList<Enemy> enemyL = new LinkedList();
        Enemy enemy = new Enemy(new Posn(247, 197), true);
        Boolean enemyCollideExplosion = enemy.explodingHuh(explosion);

        Game2 game1 = new Game2(15, 15, 15, link, enemyL, heartsMT, bombsMT, explosionL, keyMT,
                false, false, true);
        // also checks that kills and score increases by 1
        Game2 game2 = new Game2(15, 16, 16, link, enemiesMT, heartsMT, bombsMT, explosionL, keyMT,
                false, false, true);
        return t.checkExpect(enemyCollideExplosion, true, "check if colliding enemy and explosion" + "\n")
                && t.checkExpect(((Game2) game1.onTick()).enemies.size(),
                        game2.enemies.size(), "checking if enemy collision with explosion removes enemy" + "\n");
    }

    public boolean testheartCollectedGame(Tester t) {
        LinkedList<Heart> heartList = new LinkedList();
        Heart heart = new Heart(new Posn(350, 250));
        heartList.add(heart);
        
        Game2 game1 = new Game2(15, 15, 15, link, enemiesMT, heartList, bombsMT, explosionMT, keyMT,
                false, false, true);
        Game2 game2 = new Game2(16, 15, 15, link, enemiesMT, heartsMT, bombsMT, explosionMT, keyMT,
                false, false, true);
        return t.checkExpect(((Game2)game1.onTick()).hearts.size(),
                game2.hearts.size(), "checking if heart was collected" + "\n");
    }

    public static void main(String[] args) {

        Tester.runReport(new TestTest(), false, false);

    }

}
