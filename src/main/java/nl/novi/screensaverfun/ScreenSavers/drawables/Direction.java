package nl.novi.screensaverfun.ScreenSavers.drawables;

import java.util.Random;

public class Direction {
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void setZ(int z) {
        this.z = z;
    }
    private int x;
    private int y;
    private int z;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public int getZ() {
        return z;
    }

    public Direction() {
        Random randomizer = new Random();
        this.x = generateNonZeroDirection(randomizer);
        this.y = generateNonZeroDirection(randomizer);
        this.z = generateNonZeroDirection(randomizer);
    }

    private int generateNonZeroDirection(Random randomizer) {
        int direction;
        do {
            direction = randomizer.nextInt(15) - 7; // Genereert een nummer tussen -7 en 7.
        } while (direction == 0);

        return direction;
    }

    public void changeXDirection() {
        x = x * -1;
    }
    public void changeYDirection() {
        y = y * -1;
    }
    public void changeZDirection() {
        z = z * -1;
    }
}
