package main;

import java.awt.DisplayMode;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import de.lessvoid.nifty.slick2d.NiftyStateBasedGame;

public class Main extends NiftyStateBasedGame{
    
    private static Main instance;
    
    private AppGameContainer container;
    
    private Main(){
        super("War Game of Thrones", true);
    }
    
    public static Main getInstance(){
        if(instance == null)
            instance = new Main();
        return instance;
    }

    public static void main(String[] args) throws SlickException {
        Main m = getInstance();
        AppGameContainer app = new AppGameContainer(m);
        DisplayMode dm = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        int w = dm.getWidth(), h = dm.getHeight();
        boolean fullscreen = false;
        
        app.setDisplayMode(w, h, fullscreen);
        app.setTargetFrameRate(60);
        app.start();
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        addState(new MainScene());
        addState(new GameScene());
        enterState(WarScenes.STARTING_SCENE.ordinal());
//        enterState(WarScenes.GAME_SCENE.ordinal());
    }
    
}