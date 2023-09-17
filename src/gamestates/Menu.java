package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import helperClass.Coordinate;
import main.Game;
import util.LoadSave;
import util.Constants.Config;
import util.Ui;

public class Menu extends State implements Statemethods {
    Font font;
    FontMetrics fm;
    int selectedChoice = 0;
    ArrayList<String> text = new ArrayList<>();
    int maxChoice;

    private void initTextArr() {
        text.add("START");
        text.add("OPTION");
        text.add("CREDIT");
        text.add("EXIT");
        maxChoice = text.size();
    }

    private void submit() {
        switch (selectedChoice) {
            case 0:
                Gamestate.state = Gamestate.PLAYING;
                break;
            case 3:
                System.exit(0);
                break;
        }
    }

    private void circleChoice(int amount) {
        if (amount == -1 && selectedChoice == 0) {
            selectedChoice = maxChoice;
        }
        selectedChoice = (selectedChoice + amount) % maxChoice;
    }

    public Menu(Game game) {
        super(game);
        font = LoadSave.GetFont(40);
        initTextArr();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics g) {
        fm = g.getFontMetrics(font);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        g.setFont(font);
        g.setColor(Color.WHITE);
        int fontHeight = fm.getHeight();
        Coordinate[] coordinates = Ui.DrawArrayLineMiddle(g, text);
        g.drawLine(Ui.GetPercentX(20), (int) (coordinates[selectedChoice].y - fontHeight), Ui.GetPercentX(80),
                (int) (coordinates[selectedChoice].y - fontHeight));
        g.drawLine(Ui.GetPercentX(20), (int) (coordinates[selectedChoice].y + 7.5), Ui.GetPercentX(80),
                (int) (coordinates[selectedChoice].y + 7.5));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Gamestate.state = Gamestate.PLAYING;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Gamestate.state = Gamestate.PLAYING;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                circleChoice(1);
                break;
            case KeyEvent.VK_S:
                circleChoice(1);
                break;
            case KeyEvent.VK_W:
                circleChoice(-1);
                break;
            case KeyEvent.VK_UP:
                circleChoice(-1);
                break;
            case KeyEvent.VK_ENTER:
                submit();
                break;
            case KeyEvent.VK_E:
                submit();
                break;
            case KeyEvent.VK_SPACE:
                submit();
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
