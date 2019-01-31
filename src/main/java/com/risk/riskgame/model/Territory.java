package com.risk.riskgame.model;

import java.util.HashMap;

public class Territory extends GenericTreeNode {
    public Territory(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String name;
    public String color;
    public Player ownedBy;
    public int numberOfTroops=0;
    public HashMap<String,Territory> adjacentTerritories=new HashMap<>();

}
