package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import util.TerritoriesGraphStructure;
import org.apache.commons.lang.SerializationUtils;

/**
 *
 * @author rodrigo
 */
public class Board implements Serializable {

    public static final int AI_PLAYER = 1;
    public static final int HUMAN_PLAYER = 2;
    public static final String LANNISTER = "Lannister";
    public static final String STARK = "Stark";
    public static final String GREYJOY = "Greyjoy";
    public static final String FREE_FOLK = "Free Folk";
    public static final String BARATHEON = "Baratheon";
    public static final String TARGARYEN = "Targaryen";
    protected LinkedList<Player> players;
    protected static Board instance;
    protected int currentPlayer;
    protected StatisticGameManager statistic;
    private Region[] regions;
    private BackEndTerritory[] territories;
    private ArrayList<House> houses;
    private ArrayList<Mission> missions;
    private boolean onInitialSetup;
    private boolean onFirstTurn;
    private int numberOfSwappedCards;
    private Player winner;

    protected Board() {
        instance = this;
        players = new LinkedList<Player>();
        currentPlayer = 0;
        statistic = new StatisticGameManager();
        onInitialSetup = true;
        onFirstTurn = false;
        houses = new ArrayList<House>();
        missions = new ArrayList<Mission>();
        numberOfSwappedCards = 0;
        winner = null;
        
        if (regions == null) {
            retrieveTerritories();
        }
    }

    private void retrieveTerritories() {
        System.out.println("Filling board territories");
        regions = new Region[6];
        String[] regionNames = {"Além da Muralha", "Cidades Livres", "O Norte", "O Sul", "Tridente", "O Mar Dothraki"};
        int[] bonus = {Region.ALEM_DA_MURALHA, Region.CIDADES_LIVRES, Region.O_NORTE, Region.O_SUL, Region.TRIDENTE, Region.O_MAR_DOTHRAKI};
        for (int i = 0; i < regionNames.length; i++) {
            regions[i] = new Region(regionNames[i], bonus[i]);
        }

        //alem da muralha
        Region current = regions[0];
        territories = new BackEndTerritory[39];
        int tIndex = 0;
        TerritoriesGraphStructure struct = TerritoriesGraphStructure.getInstance();
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.COSTA_GELADA, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.FLORESTA_ASSOMBRADA, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.SEMPRE_INVERNO, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.ILHA_DOS_URSOS, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.SKAGOS, struct));

        //cidades livres
        current = regions[1];
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.COSTA_LARANJA, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.TERRAS_DISPUTADAS, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.CAMPOS_DOURADOS, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.COLINAS_DE_NORVOS, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.COSTA_BRAVOSIANA, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.FLORESTA_DE_QOHOR, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.THE_FLATLANDS, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.VALIRIA, struct));

        //o norte
        current = regions[2];
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.A_DADIVA, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.BARROWLANDS, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.COSTA_PEDREGOSA, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.MATA_DE_LOBOS, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.PENHASCO_SOMBRIO, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.THE_HILLS, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.WINTERFELL, struct));

        //o sul
        current = regions[3];
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.ARVORE, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.DORNE, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.JARDIM_DE_CIMA, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.MATADERREI, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.MONTE_CHIFRE, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.TARTH, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.TERRAS_DA_TEMPESTADE, struct));

        //tridente
        current = regions[4];
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.MONTANHAS_DA_LUA, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.CAPE_KRAKENTT, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.GARGALO, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.VALE_DE_ARRYN, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.PORTO_REAL, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.ROCHEDO_CASTERLY, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.TERRAS_FLUVIAIS, struct));

        //o mar dothraki
        current = regions[5];
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.GHISCAR, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.DESERTO_VERMELHO, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.MAR_DOTHRAKI, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.OROS, struct));
        current.addTerritory(territories[tIndex++] = new BackEndTerritory(TerritoryID.FOOTPRINT, struct));
    }

    public void createMissions() {
        Region empty = new Region(null, 0);
        
        Mission mission1 = new Mission("", Mission.TYPE_REGION); 
        mission1.addRegion(regions[0]); //Além da Muralha
        mission1.addRegion(regions[3]); //O Sul
        mission1.addRegion(empty);      // À sua escolha
        missions.add(mission1);
        
        Mission mission2 = new Mission("", Mission.TYPE_REGION); 
        mission2.addRegion(regions[1]); //Cidades Livres
        mission2.addRegion(regions[0]); //Além da Muralha
        missions.add(mission2);
        
        Mission mission3 = new Mission("", Mission.TYPE_REGION); 
        mission3.addRegion(regions[2]); //O Norte
        mission3.addRegion(regions[5]); //O Mar Dothraki
        mission3.addRegion(empty);      // À sua escolha
        missions.add(mission3);
        
        Mission mission4 = new Mission("", Mission.TYPE_REGION);
        mission4.addRegion(regions[1]); //Cidades Livres
        mission4.addRegion(regions[4]); //Tridente
        missions.add(mission4);
        
        Mission mission5 = new Mission("", Mission.TYPE_REGION);
        mission5.addRegion(regions[2]); //O Norte
        mission5.addRegion(regions[3]); //O Sul
        missions.add(mission5);
        
        Mission mission6 = new Mission("", Mission.TYPE_REGION); 
        mission6.addRegion(regions[4]); //Tridente
        mission6.addRegion(regions[5]); //O Mar Dothraki
        missions.add(mission6);
        
        Mission mission7 = new Mission("", Mission.TYPE_TERRITORY);
        mission7.setTerritories(23);
        missions.add(mission7);
        
        Mission mission8 = new Mission("", Mission.TYPE_TERRITORY);
        mission8.setTerritories(17);
        missions.add(mission8);
        
        for (int i = 0; i < houses.size(); i++) {
            Mission mission = new Mission("", Mission.TYPE_HOUSE);
            mission.setHouse(houses.get(i));
            missions.add(mission);
        }
    }
    
    public BackEndTerritory[] getTerritories() {
        return territories;
    }
    
    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }

    public static void reset() {
        instance = null;
        RepositoryCardsTerritory.reset();
    }

    public Board getClone() {
        return (Board) SerializationUtils.clone(this);
    }

    public boolean addPlayer(Player player, int playingOrder, int type) {
        if (players.size() < 6) {
            if (!players.contains(player)) {
                players.addLast(player);
                return true;
            }
        }
        return false;
    }

    public boolean removePlayer(Player player) {
        return players.remove(player);
    }

    public void addHouse(House house) {
        houses.add(house);
    }

    public Player getPlayer(int playingOrder) {
        return players.get(playingOrder);
    }

    public int getPlayerOrder(Player p) {
        return players.indexOf(p);
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    public LinkedList<Player> getPlayers() {
        return players;
    }

    public void setHouses(ArrayList<House> houses) {
        this.houses = houses;
    }

    public void setMissions(ArrayList<Mission> missions) {
        this.missions = missions;
    }
    
    public List<House> getHouses() {
        List<House> houses = new ArrayList<House>();
        for (Player player : getPlayers()) {
            houses.add(player.getHouse());
        }
        return houses;
    }

    public boolean isPlayerCountValid() {
        return (players.size() >= 2 && players.size() <= 6);
    }

    public boolean isOnInitialSetup() {
        return onInitialSetup;
    }
    
    public boolean isOnFirstTurn() {
        return onFirstTurn;
    }
    
    public void incrementNumberOfSwappedCards() {
        numberOfSwappedCards++;
    }
    
    public int getNumberOfSwappedCards() {
        return numberOfSwappedCards;
    }

    public Player nextPlayer(Player player) {
        int i;
        for (i = 0; i < this.getPlayers().size(); i++) {
            if (player == this.getPlayer(i)) {
                break;
            }
        }
        if (i == this.getPlayers().size()) {
            i = 0;
        } else {
            i++;
        }
        return players.get(i);
    }

    public void changePlayer() {
        if (currentPlayer == getPlayers().size() - 1) {
            currentPlayer = 0;
            if (onInitialSetup) {
                onInitialSetup = false;
                onFirstTurn = true;
            }
            else if (onFirstTurn)
                onFirstTurn = false;
        } else
            currentPlayer++;
        if (!onFirstTurn)
            addPlayerArmies(getCurrentPlayer());
    }

    private void addPlayerArmies(Player curr) {
        int territoryCount = curr.getTerritories().size();
        int generalPendingArmies = territoryCount / 2;
        int totalPendingArmies = territoryCount / 2;
       
        for (Region r : regions) {
            if (r.conqueredByPlayer(curr)) {
                totalPendingArmies += r.getBonus();
                curr.setPendingArmiesForRegion(r, r.getBonus());
                System.out.println("receiving " + r.getBonus() + " from " + r);
            }
        }
        //minimum of received territories is 3
        if (totalPendingArmies < 3) {
            generalPendingArmies = 3;
            totalPendingArmies = 3;
        }
        curr.addGeneralPendingArmies(generalPendingArmies);
        curr.addTotalPendingArmies(totalPendingArmies);
    }

    public StatisticGameManager getStatistic() {
        return statistic;
    }

    public void distributeInitialTerritories() {
        RepositoryCardsTerritory.getInstance().initialRaffle();
        for (Player player : players) {
            for (CardTerritory card : player.getCards()) {
                player.addTerritory(card.getTerritory());
            }
            player.getCards().clear();
        }
        addPlayerArmies(getCurrentPlayer());
    }

    public void shuffleMissions(ArrayList<Mission> mission) {
        Collections.shuffle(mission);
    }

    public void raffleMission() {
        int size = players.size();

        shuffleMissions(missions);

        for (int i = 0; i < size; i++) {
            Player p = this.getPlayer(i);
            Mission mission = missions.get(0);
            while (mission.hasSameHouse(p)) {
                shuffleMissions(missions);
                mission = missions.get(0);
            }
            mission.setPlayer(p);
            Mission m = missions.remove(0);
            p.setMission(m);
            System.out.println("set player mission to " + m.getDescription());
        }
    }

    public Region[] getRegions() {
        return regions;
    }
    
    public void checkIfAnyMissionWasCompleted() {
        for(Player p : players) {
            if (p.getMission().isCompleted())
                winner = p;
        }
    }
    
    public boolean hasGameEnded() {
        checkIfAnyMissionWasCompleted();
        if (winner != null)
            StatisticGameManager.getInstance().setPlayTime();
        return winner != null;
    }
    
    public Player getWinner() {
        return winner;
    }
    
    public ArrayList<Mission> getMissions(){
        return missions;
    }
}