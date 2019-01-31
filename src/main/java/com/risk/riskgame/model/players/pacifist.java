package com.risk.riskgame.model.players;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.TextView;

import com.risk.riskgame.model.Player;
import com.risk.riskgame.model.Territory;

import java.util.ArrayList;

public class pacifist extends Player {
    public pacifist() {
        this.ID=3;
        this.Name="pacifist"+ID;
    }

    @Override
    public Territory TerritoryForDeployment() {
        Territory TerritoryForDeployment = this.occupiedTerritories.values().iterator().next();
        for(Territory t : this.occupiedTerritories.values()){
            if(t.numberOfTroops<TerritoryForDeployment.numberOfTroops){TerritoryForDeployment=t;}
        }
        return TerritoryForDeployment;
    }

    @Override
    public Territory AttackToTerritory() {
        //get all territories he can attack
        ArrayList<Territory> territoriesToAttack = new ArrayList<>();
        for(Territory t : this.occupiedTerritories.values()){
            for(Territory j : t.adjacentTerritories.values()){
                if(!territoriesToAttack.contains(j)&&!occupiedTerritories.containsKey(j.name)&&t.numberOfTroops>1){
                    territoriesToAttack.add(j);
                }
            }
        }
        if(territoriesToAttack.isEmpty()){return null;}
        //choose the one with the least troops
        Territory territoryToAttack = territoriesToAttack.get(0);
        for(Territory t : territoriesToAttack){
            if(t.numberOfTroops<territoryToAttack.numberOfTroops){territoryToAttack=t;}
        }
        return territoryToAttack;
    }

}
