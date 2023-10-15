package ui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

import entities.Player;

import util.Ui;

public class Hud {

    private Player player;

    public Hud(Player player) {
        this.player = player;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Point start = new Point((Ui.GetPercentX(3) + 150), (Ui.GetPercentY(3) + 8));
        Point end = new Point(Ui.GetPercentX(3) + 150, Ui.GetPercentY(3) + 16);
        GradientPaint gradientPaint = new GradientPaint(start, new Color(46, 182, 44), end,
                new Color(131, 212, 117));
        g2d.setPaint(gradientPaint);
        g2d.fill(new Rectangle2D.Double(Ui.GetPercentX(3), Ui.GetPercentY(3), (int) (player.getHp() * 3), 16));

        Point Mpstart = new Point((Ui.GetPercentX(3) + 150), (Ui.GetPercentY(6) + 8));
        Point Mpend = new Point(Ui.GetPercentX(3) + 150, Ui.GetPercentY(3) + 16);
        GradientPaint MpgradientPaint = new GradientPaint(Mpstart, new Color(26, 132, 184), Mpend,
                new Color(26, 148, 184));
        g2d.setPaint(MpgradientPaint);
        g2d.fill(new Rectangle2D.Double(Ui.GetPercentX(3), Ui.GetPercentY(6), (int) (player.getMp() * 3), 16));

        g.setColor(Color.GREEN);
        g.drawRect(Ui.GetPercentX(3), Ui.GetPercentY(3), 300, 16);
        g.setColor(Color.BLUE);
        g.drawRect(Ui.GetPercentX(3), Ui.GetPercentY(6), 300, 16);
    }
}
