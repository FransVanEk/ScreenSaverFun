package nl.novi.screensaverfun.ScreenSavers.drawables;

import nl.novi.screensaverfun.interfaces.Drawable;

import java.awt.*;

public class Line implements Drawable {
    public float thickness = 2;
    private Color color = Color.blue;
    private Position positionStart;
    private Position positionEnd;

    public Line(Position positionStart, Position positionEnd, float thickness) {
        this.positionStart = positionStart;
        this.positionEnd = positionEnd;
        this.thickness = thickness;
    }
    public Position getPositionStart() {
        return positionStart;
    }
    public Position getPositionEnd() {
        return positionEnd;
    }
    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(thickness));
        g2d.drawLine((int) positionStart.getX(), (int) positionStart.getY(), (int) positionEnd.getX(), (int) positionEnd.getY());
    }

    public Color getColor() {
        return color;
    }

    public Line setColor(Color color) {
        this.color = color;
        return this;
    }
}
