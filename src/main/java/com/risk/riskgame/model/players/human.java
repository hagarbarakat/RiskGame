package com.risk.riskgame.model.players;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.risk.riskgame.R;
import com.risk.riskgame.model.Player;
import com.risk.riskgame.model.Territory;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class human extends Player {
    public human() {
        this.ID=4;
        this.Name="Human";
        go.set(false);
        endTurn.set(false);
    }
    public Activity c;
    public AtomicBoolean go = new AtomicBoolean();
    public AtomicBoolean endTurn = new AtomicBoolean();
    public AtomicInteger state = new AtomicInteger();
    public Territory From;
    public Territory To;
    public Territory Deploy;
    @Override
    public Territory TerritoryForDeployment() {
        //toast choose where to deploy
        endTurn.set(false);
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast=Toast.makeText(c,"Choose territory to deploy at then press the button",Toast.LENGTH_LONG);
                toast.show();
                FloatingActionButton button = c.findViewById(R.id.floatingActionButton);
                button.setImageDrawable(c.getDrawable(R.drawable.ic_arrow_downward_black_24dp));
            }
        });

        state.set(0);
        Log.e("state",state.toString());
        while(go.get()==false){}
        Log.wtf("deploy", "done" );
        go.set(false);
        return Deploy;
    }

    @Override
    public Territory AttackToTerritory() {
        state.set(1);
        //toast choose where to attack
        Log.e("state",state.toString());
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast=Toast.makeText(c,"Choose territory to attack to then press the button",Toast.LENGTH_LONG);
                toast.show();
                FloatingActionButton button = c.findViewById(R.id.floatingActionButton);
                button.setImageDrawable(c.getDrawable(R.drawable.attack_to_24dp));
            }
        });

        while(go.get()==false){}
        go.set(false);
        return To;
    }

    @Override
    public Territory AttackFromTerritory() {
        state.set(2);
        Log.e("state",state.toString());
        //toast choose where from attack
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast=Toast.makeText(c,"Choose territory to attack from then press the button",Toast.LENGTH_LONG);
                toast.show();
                FloatingActionButton button = c.findViewById(R.id.floatingActionButton);
                button.setImageDrawable(c.getDrawable(R.drawable.attack_24dp));
            }
        });

        while(go.get()==false){}
        go.set(false);
        return From;
    }

    @Override
    public boolean attack(TextView chosenTerritory, TextView chosenTerritoryTroops, FloatingActionButton button, Activity c){
        while (!endTurn.get()){
             super.attack(chosenTerritory,chosenTerritoryTroops,button,c);
        }
        state.set(-1);
        Log.e("state",state.toString());
        return true;
    }
}
