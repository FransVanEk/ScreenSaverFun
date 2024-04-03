package nl.novi.screensaverfun.interfaces;

import java.awt.event.KeyEvent;
import java.util.List;

public interface GraphicsManagerBase {
    void calculateGraphics();
    List<? extends Drawable> getDrawables();
    void handleKeyPress(KeyEvent e);
    void setSize(int x, int y);
}
