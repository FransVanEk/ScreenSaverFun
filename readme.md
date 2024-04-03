# Getting Started

Maak je eigen implementatie van
de [GraphicsManagerBase.java](src%2Fmain%2Fjava%2Fnl%2Fnovi%2Fscreensaverfun%2Finterfaces%2FGraphicsManagerBase.java) in
een package onder nl.novi.screensaverfun.ScreenSavers.<nieuwe naam>

Verander in
de [ScreenSaverFunApplication.java](src%2Fmain%2Fjava%2Fnl%2Fnovi%2Fscreensaverfun%2FScreenSaverFunApplication.java) de
code zodat je eigen screensaver geactiveerd wordt.

````java
    public ScreenSaverFunApplication(int width,int height){
        graphicsManager=new SimpleLinesManager(width,height); // change to your own
        timer=new Timer(1,this);
        timer.start();
        setFocusable(true); 
        setKeyStrokes();
        }
````

Run je applicatie!!

Er is een tweede implementatie van van een
screensaver. [LinesManager.java](src%2Fmain%2Fjava%2Fnl%2Fnovi%2Fscreensaverfun%2FScreenSavers%2FLinesManager.java).

````java
    public ScreenSaverFunApplication(int width,int height){
        graphicsManager=new LinesManager(width,height);
        timer=new Timer(1,this);
        timer.start();
        setFocusable(true); 
        setKeyStrokes();
        }
````

Kijk bij de keystrokes welke mogelijkheden er zijn om de screensaver tijdens het uitvoeren nog te beïnvloeden.

````java
 public void handleKeyPress(KeyEvent e){
        switch(e.getKeyCode()){
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
````

