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

public class GameOver extends World {

    static int screenWIDTH = 700;
    static int screenHEIGHT = 500;
    String backFileName = "background.png";
    WorldImage background2 = new FromFileImage(new Posn(screenWIDTH / 2, screenHEIGHT / 2), backFileName);
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
    String gameOverText;

    public GameOver(int lives, int score, Hero hero, String gameOverText) {

        this.lives = lives;
        this.score = score;
        this.hero = hero;
        this.gameOverText = gameOverText;
    }

    public World onKeyEvent(String ke) {
        
        
        if (ke.equals("s")) {
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

            return new Game2(15, 0, 0, hero,
                    Enemies, Hearts,
                    new LinkedList(), // bombs
                    new LinkedList(), // explosions
                    new LinkedList(), // key
                    false, false, true);
        } else {
            return this;
        }
    }
//        return new Game2(this.lives, this.score, 0, hero,
//                Enemies, Hearts,
//                new LinkedList(), // bombs
//                new LinkedList(), // explosions
//                new LinkedList(), // key
//                false, false, true);

    

    public WorldImage makeImage() {
       WorldImage world = background2;
       
       world = new OverlayImages(background2,
                            new OverlayImages(new TextImage(new Posn(screenWIDTH / 2, screenHEIGHT / 2),
                                            gameOverText, 30, 1, new Black()),
                                    new OverlayImages(
                                            new TextImage(
                                                    new Posn(screenWIDTH/2, screenHEIGHT/2 +25),
                                                    "press S to start over! ", 30, 1, new Black()),
                                            new TextImage(new Posn(screenWIDTH / 2, screenHEIGHT / 2 + 50),
                                            "Final Score:   " + score,
                                            20, 1, new Black()))));
       
       return world;
    }

}
