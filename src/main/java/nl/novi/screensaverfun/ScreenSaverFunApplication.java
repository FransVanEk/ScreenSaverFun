package nl.novi.screensaverfun;

import nl.novi.screensaverfun.ScreenSavers.LinesManager;
import nl.novi.screensaverfun.ScreenSavers.SimpleLinesManager;
import nl.novi.screensaverfun.interfaces.Drawable;
import nl.novi.screensaverfun.interfaces.GraphicsManagerBase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ScreenSaverFunApplication extends JPanel implements ActionListener {
    private final Timer timer;
    private final GraphicsManagerBase graphicsManager;

    public ScreenSaverFunApplication(int width, int height) {
        graphicsManager = new SimpleLinesManager(width, height); // change to your preference
        timer = new Timer(1, this);
        timer.start();
        setFocusable(true); // Make sure the JPanel can gain focus
        setKeyStrokes();
    }

    private void setKeyStrokes() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                graphicsManager.handleKeyPress(e);
            }
        });
    }

    public static void main(String[] args) {
        int width = 1200;
        int height = 800;

        JFrame frame = new JFrame("Screen saver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        var myApp = new ScreenSaverFunApplication(width, height - 30);
        frame.add(myApp);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // This method will be called when the panel is resized
                // You can react to the size change here
                int newWidth = frame.getWidth();
                int newHeight = frame.getHeight() - 30;

                // You can update your panel or take other actions here
                myApp.setScreen(newWidth,newHeight);
            }
        });
        frame.setVisible(true);
    }

    private void setScreen(int x, int y) {
        graphicsManager.setSize(x,y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (Drawable graphic : graphicsManager.getDrawables()) {
            graphic.draw(g2d);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        graphicsManager.calculateGraphics();
        repaint();
    }
}
