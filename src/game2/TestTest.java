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
        GameOver gameOver = new GameOver(15, link, "GAME OVER!");

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

    public boolean testGame2ToBossLevel(Tester t) {
        Hero hero = new Hero(new Posn(701, 250));
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

        Game2 game = new Game2(15, 15, 15, hero, enemiesMT, heartsMT,
                bombsMT, explosionMT, keyMT, false, true, false);
        BossLevel bossy = new BossLevel(15, 15, 5, new Boss(), new Hero(new Posn(10, 250)),
                Hearts, Bombs, Explosions);
        return //checks to see if the keypress returns a game2 instanceof game2
                t.checkExpect(game.onTick() instanceof BossLevel,
                        bossy instanceof BossLevel, "test change from Game2 to " + "\n");

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
        return t.checkOneOf(((game.kills % 25 == 0) && Utility.biasCoinToss()),
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

    public void testbombIncTestBL() throws Exception {
        LinkedList<Bomb> bomblist1 = new LinkedList();
        Bomb bomb1 = new Bomb(new Posn(100, 200), 0);

        bomblist1.add(bomb1);

        Boss boss = new Boss(new Posn(100, 100));

        BossLevel bl = new BossLevel(15, 0, 50, boss, link, heartsMT, bomblist1, explosionMT);

        if (!bomb1.canIExplode()) {
            if (!(bomb1.time < ((BossLevel) bl.onTick()).bombs.getFirst().time)) {
                throw new Exception("Bomb is not exploding, but time does not increase");
            } else {
                System.out.println("Bomb Timer BossLevel Test Successful");
            }
        }
    }

    // test that the explosion's timer increases on tick
    public boolean testexplIncGameTest(Tester t) {
        LinkedList<Explosion> explosionlist1 = new LinkedList();
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

    public void testbombToExplGameTest() throws Exception {

        LinkedList<Bomb> bomblist4 = new LinkedList();
        Bomb bomb4 = new Bomb(new Posn(100, 200), 10);
        bomblist4.add(bomb4);
        Game2 game1 = new Game2(15, 0, 0, link, enemiesMT, heartsMT,
                bomblist4, explosionMT, keyMT, false, false, true);
        if (bomb4.canIExplode()) {
            if (((Game2) game1.onTick()).explosions.isEmpty()) {
                throw new Exception("bomb can explode and in ontick, explosion is still empty");
            } else {
                System.out.println("Bomb to Explosion in Game2 Test Successful.");
            }
        }
    }

    public void testenemyList1() throws Exception { //
        Enemy enemy1 = new Enemy();

        LinkedList<Enemy> enemyList1 = new LinkedList();
        enemyList1.add(enemy1);

        Game2 game1 = new Game2(15, 15, 15, link, enemyList1, heartsMT, bombsMT, explosionMT, keyMT,
                false, false, true);

        if (!(game1.enemies.size() <= (((Game2) game1.onTick()).enemies.size()))) {
            throw new Exception("onTick, size of enemylist should either stay the same, or increase");
        } else {
            System.out.println("Size of EnemyList onTick in Game2 Test Successful");
        }
    }

    public boolean testHeroHitGame(Tester t) { //
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

    public boolean testHeroHitBL(Tester t) { //
        Boss boss = new Boss(new Posn(345, 245));
        BossLevel bl1 = new BossLevel(15, 0, 50, randB, link, heartsMT,
                bombsMT, explosionMT);
        return t.checkExpect(boss.collisionHuh(link),
                true, "checking if collision with boss is true" + "\n")
                && t.checkExpect(bl1.lives >= ((BossLevel) bl1.onTick()).lives,
                        true, "checking if lives before tick is more than lives after" + "\n");

    }


    public void testBombToExplBL() throws Exception { //
        LinkedList<Bomb> bomblist1 = new LinkedList();
        Bomb bomb1 = new Bomb(new Posn(100, 200), 10);
        bomblist1.add(bomb1);
        Hero hero = new Hero(new Posn(40, 80));

        LinkedList<Explosion> explosionlist1 = new LinkedList();
        Explosion explosion1 = new Explosion(new Posn(100, 200), 0);
        explosionlist1.add(explosion1);

        Boss boss = new Boss(new Posn(100, 100));

        BossLevel bl = new BossLevel(15, 50, 50, boss, hero, heartsMT, bomblist1, explosionMT);

        if (bl.bombs.element().canIExplode()) {
            if (!((BossLevel) bl.onTick()).bombs.isEmpty()) {
                throw new Exception("bomb can explode but on tick, the list does not become empty");
            }
        } else {
            System.out.println("Bomb to Explosion in BossLevel Test Successful");
        }
    }

    // test that the enemy list decreases when being killed
    public void testenemyList2() throws Exception { //
        LinkedList<Explosion> explosionL = new LinkedList();
        Explosion explosion = new Explosion(new Posn(250, 200));
        LinkedList<Enemy> enemyL = new LinkedList();
        Enemy enemy = new Enemy(new Posn(247, 197), true);
        Boolean enemyCollideExplosion = enemy.explodingHuh(explosion);

        Game2 game1 = new Game2(15, 15, 15, link, enemyL, heartsMT, bombsMT, explosionL, keyMT,
                false, false, true);
        // also checks that kills and score increases by 1
        if (enemyCollideExplosion) {
            if (game1.kills < ((Game2) game1.onTick()).kills) {
                throw new Exception("enemy being exploded and killed, but kills are not being increased");
            }
        } else {
            System.out.println("Kills After Enemy Dying in Game2 Test Successful");
        }

    }

    public void testKeyAppear() throws Exception {
        LinkedList<Key> keyl = new LinkedList();
        Game2 game1 = new Game2(30, 30, 30, link, enemiesMT, heartsMT, bombsMT, explosionMT, keyMT,
                false, false, true);

        if (game1.kills >= 30 && game1.score >= 30 && game1.shouldKeyAppear) {
            if (((Game2) game1.onTick()).key.isEmpty()) {
                throw new Exception("key list should not be empty once criteria for key appear is met");
            } else {
                System.out.println("Test KeyAppear is Successful");
            }
        }
    }

    public void testheartCollectedGame() throws Exception {
        LinkedList<Heart> heartList = new LinkedList();
        Heart heart = new Heart(new Posn(350, 250));
        heartList.add(heart);

        Game2 game1 = new Game2(30, 30, 30, link, enemiesMT, heartList, bombsMT, explosionMT, keyMT,
                false, false, true);

        if (link.collectingHeart(heart)) {
            if (game1.lives == (((Game2) game1.onTick()).lives)) {
                throw new Exception("heart collected but lives stay the same");
            }
            System.out.println("Heart Collection tested Successfully");
        }
    }

    public void testheartCollectedBL() throws Exception {
        LinkedList<Heart> heartList = new LinkedList();
        Heart heart = new Heart(new Posn(350, 250));
        heartList.add(heart);

        BossLevel bl = new BossLevel(15, 50, 50, new Boss(new Posn(100, 100), 0), link, heartList, bombsMT, explosionMT);
        if (link.collectingHeart(heart)) {
            if (bl.lives == (((BossLevel) bl.onTick()).lives)) {
                throw new Exception("heart collected but lives stay the same");
            }
            System.out.println("Heart Collection tested Successfully");
        }
    }

    public void testKeyCollectedGame() throws Exception {
        LinkedList<Key> keyList = new LinkedList();
        Key key = new Key(new Posn(350, 250));
        keyList.add(key);

        Game2 game1 = new Game2(30, 30, 30, link, enemiesMT, heartsMT, bombsMT, explosionMT, keyList,
                false, false, false);
        if (key.collectedHuh(link)) {
            if (!(((Game2) game1.onTick()).keyGrabbed)) {
                throw new Exception("key collected but keyGrabbed boolean stays the same");
            }
            System.out.println("Key Collection tested Successfully");

        }
    }

    public boolean testEnemyMove(Tester t) {
        Enemy enemy1 = new Enemy(new Posn(150, 150), true);

        Enemy enemyMove1 = new Enemy(new Posn(150, 170), false);
        Enemy enemyMove2 = new Enemy(new Posn(130, 150), true);
        Enemy enemyMove3 = new Enemy(new Posn(170, 150), true);

        LinkedList<Enemy> enlist1 = new LinkedList();
        LinkedList<Enemy> enlistMove1 = new LinkedList();
        LinkedList<Enemy> enlistMove2 = new LinkedList();
        LinkedList<Enemy> enlistMove3 = new LinkedList();
        enlist1.add(enemy1);
        enlistMove1.add(enemyMove1);
        enlistMove2.add(enemyMove2);
        enlistMove3.add(enemyMove3);

        Game2 game1
                = new Game2(30, 30, 30, link, enlist1, heartsMT, bombsMT, explosionMT, keyMT,
                        false, false, false);
        Game2 gameMove1
                = new Game2(30, 30, 30, link, enlistMove1, heartsMT, bombsMT, explosionMT, keyMT,
                        false, false, false);
        Game2 gameMove2
                = new Game2(30, 30, 30, link, enlistMove2, heartsMT, bombsMT, explosionMT, keyMT,
                        false, false, false);
        Game2 gameMove3
                = new Game2(30, 30, 30, link, enlistMove3, heartsMT, bombsMT, explosionMT, keyMT,
                        false, false, false);
        return t.checkOneOf("Checking enemy movement", (Game2) game1.onTick(),
                gameMove1, gameMove2, gameMove3);

    }


    public static void main(String[] args) throws Exception {

        Tester.runReport(new TestTest(), false, false);
        TestTest testtest = new TestTest();

        testtest.testenemyList1(); // successful
        testtest.testbombToExplGameTest(); // successful
        testtest.testbombIncTestBL(); // could not open file
        testtest.testBombToExplBL(); // successful
        testtest.testenemyList2();
        testtest.testKeyAppear();
        testtest.testheartCollectedGame();
        testtest.testKeyCollectedGame();

    }

}
