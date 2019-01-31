package com.risk.riskgame.model.players;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.widget.TextView;

import com.risk.riskgame.model.Player;
import com.risk.riskgame.model.Territory;

public class passive extends Player {
    public passive() {
        this.ID=1;
        this.Name="passive"+ID;
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
    public boolean attack(TextView chosenTerritory, TextView chosenTerritoryTroops, FloatingActionButton button, Activity c) {
        return true;
    }

}
