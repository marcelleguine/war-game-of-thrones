package gui;

import communication.BEPImpl;
import communication.BackEndPlayer;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class InGameGUIController implements ScreenController{

    private StatusPanelControl [] statusPanels;
    private Label playerStatusName, playerStatusCards, playerStatusUnits, playerStatusTerritories;
    private Screen screen;
    private Nifty n;
    public static BackEndPlayer [] players;
    private static Color [] playerNameColors;
    private Element objectivePopup, exitConfirmPopup, tablesPopup, objectiveLabel, helpPopup;
    private boolean mouseOverObjective = false;
    
    public InGameGUIController(){
        //DEBUG ONLY
        if(players == null){
            playerNameColors = new Color[]{
                new Color("#465DC0"),
                new Color("#41BA47"),
                new Color("#DB27AE"),
                new Color("#F4AB0C"),
                new Color("#04AAF7"),
                new Color("#9110B5")
            };
            players = new BackEndPlayer[]{
                new BEPImpl("Anderson Busto"),
                new BEPImpl("Lucas Nadalutti"),
                new BEPImpl("Mario Henrique"),
                new BEPImpl("Marcelle Guiné"),
                new BEPImpl("Mateus Azis"),
                new BEPImpl("Rodrigo Castro")
            };
        }
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.screen = screen;
        n = nifty;
        playerStatusName = screen.findNiftyControl("playerStatusName", Label.class);
        playerStatusCards = screen.findNiftyControl("playerStatusCards", Label.class);
        playerStatusUnits = screen.findNiftyControl("playerStatusUnits", Label.class);
        playerStatusTerritories = screen.findNiftyControl("playerStatusTerritories", Label.class);
        objectivePopup = n.createPopup("objectivePopup");
        exitConfirmPopup = n.createPopup("quitConfirmationPopup");
        tablesPopup = n.createPopup("tablesPopup");
        objectiveLabel = screen.findElementByName("seeObjectiveButton");
        helpPopup = n.createPopup("helpPopup");
    }
    
    @Override
    public void onStartScreen() {  
        retrieveStatusPanels(screen);
        updatePlayersData();
    }
    
    @Override
    public void onEndScreen() {    }
    
    private void retrieveStatusPanels(Screen s){
        int playersCount = players.length;
        statusPanels = new StatusPanelControlImpl[playersCount];
        for(int i = 0; i < 6; i++){
            StatusPanelControl spc = s.findNiftyControl("player" + i + "Status", StatusPanelControl.class);
            if(i >= playersCount)
                spc.getElement().setVisible(false);
            else{
                statusPanels[i] = spc;
            }
        }
    }
    
    private void updatePlayersData(){
        for(int i = 0; i < players.length; i++){
            StatusPanelControl spc = statusPanels[i];
            BackEndPlayer current = players[i];
            spc.updateData(current.getName(), current.getCardsCount(), current.getUnitsCount(), current.getTerritoriesCount());
            spc.setNameColor(playerNameColors[i]);
        }
        updateCurrentPlayersData();
    }
    
    //TODO: check who really is the next player
    private BackEndPlayer getCurrentPlayer(){
        return players[0];
    }
    
    //TODO: check who really is the next player
    private Color getCurrentPlayerColor(){
        return playerNameColors[0];
    }
    
    private void updateCurrentPlayersData(){
        BackEndPlayer currPlayer = getCurrentPlayer();
        Color currentPlayerColor = getCurrentPlayerColor();
        playerStatusName.setText(currPlayer.getName());
        playerStatusName.setColor(currentPlayerColor);
        StatusPanelControlImpl.setLabel(playerStatusCards, currPlayer.getCardsCount(), "Carta");
        StatusPanelControlImpl.setLabel(playerStatusUnits, currPlayer.getUnitsCount(), "Exército");
        StatusPanelControlImpl.setLabel(playerStatusTerritories, currPlayer.getTerritoriesCount(), "Território");
    }
    
    public void showPlayerObjective(){
        resetMouseCursor();
        n.showPopup(screen, objectivePopup.getId(), null);
        Label description = objectivePopup.findNiftyControl("objectiveDescLabel", Label.class);
        String objectiveStr = getCurrentPlayer().getMission().getDescription();
        description.setText(objectiveStr);
    }
    
    public void showPlayerCards(){
        System.out.println("SHOW CARDS");
    }
    
    public void dismissPlayerObjective(){
        n.closePopup(objectivePopup.getId());
    }
    
    //Top Menu event handling
    public void theGameMenuClicked(){
        System.out.println("THE GAME");
    }
    public void optionsMenuClicked(){
        System.out.println("OPTIONS");
    }
    public void helpMenuClicked(){
        n.showPopup(screen, helpPopup.getId(), null);
    }
    
    public void closeHelpPopup(){
        n.closePopup(helpPopup.getId());
    }
    
    public void exitMenuClicked(){
        n.showPopup(screen, exitConfirmPopup.getId(), null);
    }
    
    public void exitGame(){
        main.Main.getInstance().getGameContainer().exit();
    }
    
    public void showTables(){
        n.showPopup(screen, tablesPopup.getId(), null);
    }
    
    public void dismissExitConfirmation(){
        n.closePopup(exitConfirmPopup.getId());
    }
    
    public void dismissTablesPopup(){
        n.closePopup(tablesPopup.getId());
    }
    
    private void resetMouseCursor(){
        GameContainer c = main.Main.getInstance().getContainer();
        c.setDefaultMouseCursor();
    }
    
    public void mouseMovedOverBottomPanel(){
        Input in = main.Main.getInstance().getContainer().getInput();
        boolean inside = objectiveLabel.isMouseInsideElement(in.getMouseX(), in.getMouseY());
        if(inside != mouseOverObjective){
            mouseOverObjective = inside;
            if(!inside)
                resetMouseCursor();
            else{
                GameContainer c = main.Main.getInstance().getContainer();
                try {
                    c.setMouseCursor("resources/cursors/aero_link.png", 8, 1);
                } catch (SlickException ex) {
                        System.out.println("error setting cursor");
                }
            }
        }
    }
}
