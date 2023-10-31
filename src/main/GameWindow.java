package main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow {
    private JFrame jframe;

    public void visible() {
        jframe.setVisible(true);
    }

    GameWindow(GamePanel gp) {
        jframe = new JFrame();

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gp);
        jframe.setResizable(false);
        // jframe.setLocationRelativeTo(null);
        jframe.setLocation(0, 0);
        jframe.pack();
        jframe.setTitle("Sorcerer's Odysey");

        ImageIcon icon = new ImageIcon("src/res/asset/icond.png");
        jframe.setIconImage(icon.getImage());
        // keep last
        // jframe.setVisible(true);

        jframe.addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowGainedFocus(WindowEvent e) {
                gp.getGame().windowFocusLost();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gp.getGame().windowFocusLost();
            }
        });
    }
}
