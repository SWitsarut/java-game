package Magic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import Magic.Buff_Spell.ArcaneSurge;
import Magic.Buff_Spell.Buff;
import Magic.Buff_Spell.LightningBuff;
import Magic.Buff_Spell.Regeneration;
import Magic.Projectile_Spell.ArcaneBlast;
import Magic.Projectile_Spell.ArcaneMachineGun;
import Magic.Projectile_Spell.Bolt;
import Magic.Projectile_Spell.DivineOrb;
import Magic.Projectile_Spell.FireBall;
import entities.Player;
import entities.Projectile.Projectile;
import gamestates.Playing;
import helperClass.Coordinate;
import main.sound.SoundEffect;
import util.LoadSave;

public class Magic {

    public static final int Fire = 0;
    public static final int Arcane = 1;
    public static final int Lightning = 2;
    public static final int Holy = 3;

    public static int selectedElement = Magic.Fire;
    public static int selectedChoice = 0;

    private Player player;
    public ArrayList<Projectile> projectiles = new ArrayList<>();
    public ArrayList<Buff> buffs = new ArrayList<>();

    private BufferedImage lightningAni[];

    private SoundEffect cyclSoundEffect[] = new SoundEffect[2];
    private Random random = new Random(System.nanoTime());

    public Magic(Playing playing) {
        this.player = playing.getPlayer();
        lightningAni = LoadSave.LinearAnimationLoader("asset/effect/lightning.png", 32);
        cyclSoundEffect[0] = new SoundEffect("general/wav/handleSmallLeather.wav", 80);
        cyclSoundEffect[1] = new SoundEffect("general/wav/handleSmallLeather2.wav", 80);
    }

    public void cast(Coordinate targetCoor) {
        switch (selectedElement) {
            case Fire:
                switch (selectedChoice) {
                    case 0:
                        castFireBall(targetCoor);
                        break;
                }
                break;
            case Arcane:
                switch (selectedChoice) {
                    case 0:
                        castArcaneBullets(targetCoor);
                        break;
                    case 1:
                        castArcaneMachineGun(targetCoor);
                        break;
                    case 2:
                        castArcaneSurge();
                        break;
                }
                break;
            case Lightning:
                switch (selectedChoice) {
                    case 0:
                        castBolt(targetCoor);
                        break;
                    case 2:
                        // castLightningBuff();
                        castLightningBuff();
                        break;
                }
                break;
            case Holy:
                switch (selectedChoice) {
                    case 0:
                        castSmite(targetCoor);
                        break;
                    case 2:
                        castRegeneration();
                        break;
                }
                break;

        }
    }

    public void update() {
        for (int i = 0; i < buffs.size(); i++) {
            Buff buff = buffs.get(i);
            if (buff.isActive()) {
                buffs.get(i).update();
            } else {
                buffs.remove(i);
                i--;
            }
        }

        for (int i = 0; i < projectiles.size(); i++) {
            Projectile projectile = projectiles.get(i);
            if (!projectile.isActive()) {
                projectiles.remove(i);
                i--;
            } else {
                projectile.update();
            }

        }
    }

    public void draw(Graphics g, int xLvlOffset, int yLvlOffset) {
        for (Buff buff : buffs) {
            buff.draw(g, xLvlOffset, yLvlOffset);
        }
        for (Projectile projectile : projectiles) {
            projectile.draw(g, xLvlOffset, yLvlOffset);
        }
    }

    public void cycleLeft() {
        selectedElement = (selectedElement + 1) % 4;
        cyclSoundEffect[random.nextInt(2)].play();

    }

    public void cycleRight() {
        if (selectedElement - 1 == -1) {
            selectedElement = 3;
        } else {
            selectedElement = selectedElement - 1;
        }
        cyclSoundEffect[random.nextInt(2)].play();
    }

    public void castFireBall(Coordinate targetCoor) {
        if (player.castSpell(FireBall.cost)) {

            projectiles.add(new FireBall(player.getPlayerCenter(), targetCoor));

        }
    }

    public void castBolt(Coordinate targetCoor) {
        if (player.castSpell(Bolt.cost)) {
            projectiles.add(new Bolt(player.getPlayerCenter(), targetCoor));
        }
    }

    public void castLightningBuff() {
        if (player.castSpell(LightningBuff.cost)) {
            buffs.add(new LightningBuff(lightningAni, player));
        }
    }

    public void castSmite(Coordinate targetCoor) {
        if (player.castSpell(DivineOrb.cost)) {

            projectiles.add(new DivineOrb(player.getPlayerCenter(), targetCoor));
        }
    }

    public void castRegeneration() {
        if (player.castSpell(Regeneration.cost)) {
            buffs.add(new Regeneration(player));
        }
    }

    public void castArcaneBullets(Coordinate targetCoor) {
        if (player.castSpell(ArcaneBlast.cost)) {
            Coordinate left = new Coordinate(targetCoor.x - 20, targetCoor.y - 50);
            Coordinate right = new Coordinate(targetCoor.x - 20, targetCoor.y + 50);
            projectiles.add(new ArcaneBlast(player.getPlayerCenter(), left));
            projectiles.add(new ArcaneBlast(player.getPlayerCenter(), targetCoor));
            projectiles.add(new ArcaneBlast(player.getPlayerCenter(), right));

        }
    }

    public void castArcaneMachineGun(Coordinate targetCoor) {
        if (player.castSpell(ArcaneMachineGun.cost)) {
            projectiles.add(new ArcaneMachineGun(player.getPlayerCenter(), targetCoor));
            player.curSpellgap = player.maxSpellGap / 2 + player.maxSpellGap / 4;
        }
    }

    public void castArcaneSurge() {
        if (player.castSpell(ArcaneSurge.cost)) {
            buffs.add(new ArcaneSurge(player));
        }
    }

}