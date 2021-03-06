package communication;

import models.Player;

public class BEPImpl implements BackEndPlayer{
    
    private Player p;
    private int cards, units, territories;
    private String name;
    
    public BEPImpl(String name){
        this.name = name;
        cards = (int)(Math.random() * 100);
        units = (int)(Math.random() * 100);
        territories = (int)(Math.random() * 100);
    }
    
    public BEPImpl(Player p){
        this.p = p;
        
    }
    
    @Override
    public boolean isHuman() {
        return true;
    }

    @Override
    public int getCardsCount() {
        return p.getCards().size();
    }

    @Override
    public int getUnitsCount() {
        return units;
    }

    @Override
    public int getTerritoriesCount() {
        return territories;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Mission getMission() {
        
        //STUB::
        return new Mission() {
            @Override
            public String getDescription() {
                return "Sua missão é conquistar 24 territórios.";
            }
        };
    }

}
//package communication;
//
//public class BEPImpl implements BackEndPlayer{
//    
//    private int cards, units, territories;
//    private String name;
//    
//    public BEPImpl(String name){
//        this.name = name;
//        cards = (int)(Math.random() * 100);
//        units = (int)(Math.random() * 100);
//        territories = (int)(Math.random() * 100);
//    }
//    
//    @Override
//    public boolean isHuman() {
//        return true;
//    }
//
//    @Override
//    public int getCardsCount() {
//        return cards;
//    }
//
//    @Override
//    public int getUnitsCount() {
//        return units;
//    }
//
//    @Override
//    public int getTerritoriesCount() {
//        return territories;
//    }
//
//    @Override
//    public String getName() {
//        return name;
//    }
//
//    @Override
//    public Mission getMission() {
//        
//        //STUB::
//        return new Mission() {
//            @Override
//            public String getDescription() {
//                return "Sua missão é conquistar 24 territórios.";
//            }
//        };
//    }
//
//}
