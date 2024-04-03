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

public class LinesManager implements GraphicsManagerBase {
    private  int maxX;
    private  int maxY;
    private final List<Line> lines = new ArrayList<Line>();
    private Color currentColor = Color.red;
    private int numberOfLines = 100;
    private float thickness = 2;
    private int lineInterval = 10;
    private Direction startDirection;
    private Direction endDirection;
    private boolean kaleidoscope = true;

    public LinesManager(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
        startDirection = new Direction();
        endDirection = new Direction();
        calculateGraphics();
    }

    private static int mirror(int currentPos, int mid) {
        return mid + ((currentPos - mid) * -1);
    }

    public void handleKeyPress(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                decreaseLineInterval();
                break;
            case KeyEvent.VK_DOWN:
                increaseLineInterval();
                break;
            case KeyEvent.VK_LEFT:
                decreaseNumberOfLines();
                break;
            case KeyEvent.VK_RIGHT:
                increaseNumberOfLines();
                break;
            case KeyEvent.VK_COMMA:
                decreaseThickness();
                break;
            case KeyEvent.VK_PERIOD:
                increaseThickness();
                break;
            case KeyEvent.VK_X:
                synchroniseX();
                break;
            case KeyEvent.VK_Y:
                synchroniseY();
                break;
            case KeyEvent.VK_R:
                restart();
                break;
            case KeyEvent.VK_K:
                toggleKaleidoscope();
                break;
        }
    }

    private void toggleKaleidoscope() {
        kaleidoscope = !kaleidoscope;
    }

    @Override
    public void calculateGraphics() {
        addLine();
        while (lines.size() >= numberOfLines) {
            lines.remove(0);
        }
    }


    private void addKaleidoscopeEffect(ArrayList<Line> lines, ArrayList<Drawable> result) {
        for (Line line : lines) {
            result.addAll(getMirroredLines(line));
        }
    }

    private List<Drawable> getMirroredLines(Line line) {
        var result = new ArrayList<Drawable>();
        var midx = maxX / 2;
        var midy = maxY / 2;
        mirrorXandY(line, result, midx, midy);
        mirrorY(line, result, midx, midy);
        mirrorX(line, result, midx, midy);
        return result;
    }

    private void mirrorX(Line line, ArrayList<Drawable> result, int midx, int midy) {
        result.add(new Line(getMirroredPosition(line.getPositionStart(), midx, midy), line.getPositionEnd(), line.thickness).setColor(line.getColor()));
    }

    private void mirrorY(Line line, ArrayList<Drawable> result, int midx, int midy) {
        result.add(new Line(line.getPositionStart(), getMirroredPosition(line.getPositionEnd(), midx, midy), line.thickness).setColor(line.getColor()));
    }

    private void mirrorXandY(Line line, ArrayList<Drawable> result, int midx, int midy) {
        result.add(new Line(getMirroredPosition(line.getPositionStart(), midx, midy), getMirroredPosition(line.getPositionEnd(), midx, midy), line.thickness).setColor(line.getColor()));
    }

    private Position getMirroredPosition(Position position, int midx, int midy) {
        return new Position(mirror((int)position.getX(), midx), mirror((int)position.getY(), midy));
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
        var lines = getcomputedLines();
        var result = new ArrayList<Drawable>();
        result.addAll(lines);
        if (kaleidoscope) {
            addKaleidoscopeEffect(lines, result);
        }
        return result;
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
        maxX= x;
        maxY=y;
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

    public void decreaseNumberOfLines() {
        numberOfLines = Math.max(1, --numberOfLines);
    }

    public void increaseNumberOfLines() {
        numberOfLines = Math.min(300, ++numberOfLines);
    }

    public void decreaseThickness() {
        thickness = Math.max(2, --thickness);
    }

    public void increaseThickness() {
        thickness = Math.min(30, ++thickness);
    }

    public void synchroniseX() {
        startDirection.setX(endDirection.getX());
        var lastLine = getLastLine();
        lastLine.getPositionStart().setX(lastLine.getPositionEnd().getX());
    }

    public void synchroniseY() {
        startDirection.setY(endDirection.getX());
        var lastLine = getLastLine();
        lastLine.getPositionStart().setY(lastLine.getPositionEnd().getY());
    }

    public void restart() {
        startDirection = new Direction();
        endDirection = new Direction();
    }

    public void decreaseLineInterval() {
        lineInterval = Math.max(1, lineInterval - 1);
    }

    public void increaseLineInterval() {
        ++lineInterval;
    }
}