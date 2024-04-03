package nl.novi.screensaverfun.ScreenSavers;

import nl.novi.screensaverfun.ScreenSavers.drawables.Direction;
import nl.novi.screensaverfun.ScreenSavers.drawables.Line;
import nl.novi.screensaverfun.ScreenSavers.drawables.Position;
import nl.novi.screensaverfun.interfaces.Drawable;
import nl.novi.screensaverfun.interfaces.GraphicsManagerBase;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SimpleLinesManager implements GraphicsManagerBase {
    private final List<Line> lines = new ArrayList<Line>();
    private int maxX;
    private int maxY;
    private Color currentColor = Color.red;
    private int numberOfLines = 100;
    private float thickness = 10;
    private int lineInterval = 10;
    private Direction startDirection;
    private Direction endDirection;

    public SimpleLinesManager(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
        startDirection = new Direction();
        endDirection = new Direction();
        calculateGraphics();
    }


    public void handleKeyPress(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                restart();
                break;
        }
    }


    @Override
    public void calculateGraphics() {
        addLine();
        while (lines.size() >= numberOfLines) {
            lines.remove(0);
        }
    }

    private void addLine() {

        if (lines.size() == 0) {
            lines.add(new Line(new Position(), new Position(), thickness));
        }
        var lastLine = getLastLine();
        var newStartPosition = GetPosition(lastLine.getPositionStart(), startDirection);
        var newEndPosition = GetPosition(lastLine.getPositionEnd(), endDirection);
        lines.add(new Line(newStartPosition, newEndPosition, thickness));
    }

    @Override
    public List<Drawable> getDrawables() {
        return new ArrayList<Drawable>(getcomputedLines()); //needed for polymorphism
    }


    private ArrayList<Line> getcomputedLines() {
        int counter = 0;
        currentColor = getNextColor();
        var result = new ArrayList<Line>();
        for (Line line : lines) {
            addComputedLine(line, counter, result);
            counter++;
        }
        return result;
    }

    private void addComputedLine(Line line, int counter, ArrayList<Line> result) {
        if (counter % lineInterval == 0) {
            line.setColor(currentColor);
            result.add(line);
        }
    }

    private Color getNextColor() {
        float[] hsb = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null);
        float hue = hsb[0];
        hue = (hue + 0.0005f) % 1.0f; // Adjust this value to change color variation
        int rgb = Color.HSBtoRGB(hue, hsb[1], hsb[2]);
        return new Color(rgb);
    }

    private Line getLastLine() {
        return lines.get(lines.size() - 1);
    }

    @Override
    public void setSize(int x, int y) {
        maxX = x;
        maxY = y;
    }

    private Position GetPosition(Position currentPosition, Direction direction) {
        var x = currentPosition.getX() + direction.getX();
        var y = currentPosition.getY() + direction.getY();
        if (x < 0) {
            direction.changeXDirection();
            x = x * -1;
        }
        if (y < 0) {
            direction.changeYDirection();
            y = y * -1;
        }
        if (x > maxX) {
            x = x - (x % maxX);
            direction.changeXDirection();
        }
        if (y > maxY) {
            y = y - (y % maxY);
            direction.changeYDirection();
        }
        return new Position(x, y);
    }

    public void restart() {
        startDirection = new Direction();
        endDirection = new Direction();
    }
}