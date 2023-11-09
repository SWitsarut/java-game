package interactable;

import java.awt.Graphics;
import java.util.ArrayList;

import Inventory.Item;
import Levels.Level;
import Levels.LevelManager;
import entities.Player;
import gamestates.Playing;
import helperClass.Coordinate;
import main.Manager;
import util.Helper;

public class InteractableManager implements Manager {

    ArrayList<Interactable> interactables;

    Playing playing;
    Player player;

    public InteractableManager(Playing playing) {
        this.playing = playing;
        interactables = new ArrayList<>();
        initPortal(LevelManager.curMapIndex);
        this.player = playing.getPlayer();
    }

    public void initPortal(int mapIndex) {
        interactables.clear();
        switch (mapIndex) {
            case LevelManager.LAVADUNGEON:
                spawnPotalatAtTile("go to roof", LevelManager.mapNameArr[LevelManager.ROOFTOP], LevelManager.LAVADUNGEON, 29, 2,
                        new Coordinate(1410, 2340), 3,
                        3);
                spawnPotalatAtTile("go outside", LevelManager.mapNameArr[LevelManager.FOREST], LevelManager.LAVADUNGEON, 78, 62,
                        new Coordinate(1046, 72), 4,
                        2);

                int[] requireRightGate = new int[2];
                requireRightGate[0] = Item.silver_key;
                requireRightGate[1] = Item.gold_ley;

                LockPotalatAtTile("go outside", LevelManager.mapNameArr[LevelManager.FOREST], LevelManager.LAVADUNGEON, 78, 62,
                        new Coordinate(1046, 72), 4,
                        2, requireRightGate, "need gold and silver key");
                break;
            case LevelManager.ROOFTOP:
                spawnPotalatAtTile("go to bridge", LevelManager.mapNameArr[LevelManager.BRIDGE], LevelManager.ROOFTOP, 31, 19,
                        new Coordinate(945, 3700), 5,
                        5);
                spawnPotalatAtTile("go to dungeon", LevelManager.mapNameArr[LevelManager.LAVADUNGEON], LevelManager.ROOFTOP, 28, 48,
                        new Coordinate(1455, 205), 3,
                        3);
                break;
            case LevelManager.BRIDGE:
                // top potal
                spawnPotalatAtTile("go to wolf Field", LevelManager.mapNameArr[LevelManager.DOG], LevelManager.BRIDGE, 12, 0,
                        new Coordinate(1655, 3050), 16,
                        8);
                // //bot potal
                spawnPotalatAtTile("go to roof", LevelManager.mapNameArr[LevelManager.ROOFTOP], LevelManager.BRIDGE, 12, 72,
                        new Coordinate(1450, 1000), 16,
                        8);
                break;
            case LevelManager.DOG:
                int[] dogRequire = new int[1];
                LockPotalatAtTile("warp to cave", LevelManager.mapNameArr[LevelManager.ROOFTOP], LevelManager.DOG, 30, 14,
                        new Coordinate(1410, 2340), 13, 10, dogRequire, "dog spirit blocking");

                Coordinate desPos = Helper.getPosFromTile(45, 45);
                spawnPotalatAtTile("go back to bridge", LevelManager.mapNameArr[LevelManager.BRIDGE], LevelManager.DOG, 27, 62,
                        desPos, 16, 8);
                break;
            case LevelManager.FOREST:
                spawnPotalatAtTile("get in", LevelManager.mapNameArr[LevelManager.LAVADUNGEON], LevelManager.FOREST, 17, 0,
                        new Coordinate(3820, 2922), 10, 2);
                break;
        }

    }

    public void spawnPotalatAtTile(String massage, String targetMap, int mapIndex, int xTile, int yTile, Coordinate des, int width,
                                   int height) {
        Coordinate pos = Helper.getPosFromTile(xTile, yTile);
        Potal potal = new Potal(playing, targetMap, mapIndex, massage);
        potal.initHitbox(pos.x, pos.y, width, height);
        potal.setDes(des);
        interactables.add(potal);
    }

    public void LockPotalatAtTile(String massage, String targetMap, int mapIndex, int xTile, int yTile, Coordinate des, int width,
                                  int height, int[] requireItem, String lockMsg) {
        Coordinate pos = Helper.getPosFromTile(xTile, yTile);
        LockPotal potal = new LockPotal(massage, playing, targetMap, mapIndex, requireItem, lockMsg);
        potal.initHitbox(pos.x, pos.y, width, height);
        potal.setDes(des);
        interactables.add(potal);
    }

    public void draw(Graphics g, int xLvlOffset, int yLvlOffset) {
        for (int i = 0; i < interactables.size(); i++) {
            Interactable interactable = interactables.get(i);
            interactable.draw(g, xLvlOffset, yLvlOffset);
        }
    }

    @Override
    public void draw(Graphics g) {

    }

    public void update() {
        for (int i = 0; i < interactables.size(); i++) {
            Interactable interactable = interactables.get(i);
            interactable.update();
            if (player.getHitbox().intersects(interactable.getHitbox())) {
                interactable.setShowMessage(true);
                if (player.interacting) {
                    interactable.interact();
                }
            } else {
                interactable.setShowMessage(false);
            }
        }
    }

}
