package com.risk.riskgame.model.players;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.TextView;

import com.risk.riskgame.model.Game;
import com.risk.riskgame.model.GenericTree;
import com.risk.riskgame.model.GenericTreeNode;
import com.risk.riskgame.model.Player;
import com.risk.riskgame.model.Territory;

import java.util.ArrayList;

public class MiniMax extends Player {
  ArrayList  <Territory> player;
    ArrayList <Territory> opponent;
    ArrayList <Territory> attack=new ArrayList<>();
    int BestVal;
    int Utility=0;
    int value;
    static int MAX=10000000;
    static int MIN= -10000000;
    GenericTreeNode child;
    public MiniMax() {
        this.ID = 4;
        this.Name = "MiniMax";
    player=new ArrayList<>();
    opponent=new ArrayList<>();

    BestVal=0;
    value=0;
    child=new GenericTreeNode();}
    public int Search (Territory p,int depth, Boolean isMax,int Alpha, int Beta) {
        int score=Evaluate(p);
        if (occupiedTerritories.size()==Game.Territories.size()) {
            if(score==10000){
                return score;
            }
            if(score==-10000)
                return score;
        }

        if (isMax) {
            BestVal = MIN;
            for (Territory t : this.occupiedTerritories.values()) {
                for (Territory s : t.adjacentTerritories.values()) {

                    value = Search(s, depth + 1, false, Alpha, Beta);
                    BestVal = Math.max(BestVal, value);
                    Alpha = Math.max(Alpha, BestVal);
                    if (Beta <= Alpha)
                        break;
                    return BestVal;
                }
            }
        } else {
            BestVal = MAX;
            for (Territory t : this.occupiedTerritories.values()) {
                for (Territory s : t.adjacentTerritories.values()) {
                    value = Search(s, depth + 1, true, Alpha, Beta);
                    BestVal = Math.max(BestVal, value);
                    if (Beta <= Alpha)
                        break;
                    return BestVal;

                }
            }
        }
return  BestVal;

    }
    public int Minimize(Territory t, int Alpha, int Beta){

        if(!(occupiedTerritories.size()==Game.Territories.size())){
             Evaluate(t);
        }
        GenericTreeNode minChild= null;
       int minUtility=MAX;
       for(Territory territory: t.adjacentTerritories.values()){
           if(occupiedTerritories.containsKey(territory.name)){
              Utility= Maximize(territory,Alpha,Beta);
           if(Utility<minUtility){
               minChild=territory;
               minUtility=Utility;
           }
           if(minUtility<Alpha)
               break;
           if(minUtility<Beta)
               Beta=minUtility;
       }}
       return minUtility;
    }
public int Maximize(Territory t, int Alpha, int Beta){

    if((occupiedTerritories.size()==Game.Territories.size())){
        return  Evaluate(t);
    }
    GenericTreeNode maxChild= null;
    int maxUtility=MIN;
    for(Territory territory: t.adjacentTerritories.values()){
        if(!occupiedTerritories.containsKey(territory.name)){
            Utility= Minimize(territory,Alpha,Beta);
            if(Utility<maxUtility){
                maxChild=territory;
                maxUtility=Utility;
            }
            if(maxUtility<Alpha)
                break;
            if(maxUtility>Alpha)
                Alpha=maxUtility;
        }}
    return maxUtility;
}
    public int Evaluate( Territory t){
        int score=0;
        int sum=0;

            for (Territory territory: t.adjacentTerritories.values())
            {
                if(!occupiedTerritories.containsKey(territory.name)&&t.numberOfTroops>territory.numberOfTroops){
                           sum+=territory.numberOfTroops;
                           score=sum/t.numberOfTroops;
                           attack.add(territory);
                    return score;
                }
                else if (occupiedTerritories.containsKey(territory.name)&&t.numberOfTroops<territory.numberOfTroops) {
                    sum+=t.numberOfTroops;
                    score=sum/territory.numberOfTroops;
                    return score;
                }

                }

        return 0;
    }

    @Override
    public Territory AttackToTerritory () {
        Territory t = null;
        int i = 0;
        while (!attack.isEmpty()) {
            t = attack.remove(i);
            i++;
            return t;
        }
        return null;
    }
    public GenericTreeNode Decision(Territory t){
       GenericTreeNode child= new GenericTreeNode(Maximize(t,MIN,MAX));
       return child;
    }

    @Override
    public boolean attack (TextView chosenTerritory, TextView chosenTerritoryTroops, FloatingActionButton button, Activity c){
        for (Territory t:this.occupiedTerritories.values()){
            for (Territory s: t.adjacentTerritories.values()){
                if(!occupiedTerritories.containsKey(s.name)){
                    Decision(s);
                }
            }
        }
        for (int i = 0; i < attack.size(); i++) {

            Log.e("MiniMax", "attack");
            super.attack(chosenTerritory, chosenTerritoryTroops, button, c);
        }
        return true;
    }
}
