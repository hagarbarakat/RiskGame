package com.risk.riskgame.model;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.TextView;

import com.risk.riskgame.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import static java.lang.Thread.sleep;

public abstract class Player {

    public int ID;
    public String Name;
    public HashMap<String,Territory> occupiedTerritories= new HashMap<>();
    public int troops =20;

    //perfrormance measure
    public int L=0;//number of turns it takes to win
    public int T=0;//number of tree expansions
    public int NumberOfTroopsToDeploy(){
        int num=0;
        if(occupiedTerritories.size()<3){num = 3;}
        else {num = occupiedTerritories.size()/3;}
        return num;
    }

    public Territory TerritoryForDeployment(){
        return null;
    }

    public Territory AttackFromTerritory(){
        //get the territory with the most number of troops adjacent to the territory to attack

        Territory territoryToAttack = AttackToTerritory();
        if(territoryToAttack==null){return null;}
        Territory territoryFromAttack = new Territory("","");
        for(Territory t : territoryToAttack.adjacentTerritories.values()){
            if(occupiedTerritories.containsKey(t.name)){
                if(t.numberOfTroops>territoryFromAttack.numberOfTroops && t.numberOfTroops>1){territoryFromAttack=t;}
            }
        }
        if(territoryFromAttack.numberOfTroops==0)return null;
        return territoryFromAttack;
    }
    public Territory AttackToTerritory(){
        return null;
    }
    public void deployTroops(final TextView chosenTerritory, final TextView chosenTerritoryTroops
                            , final FloatingActionButton button, final Activity c){

        final Territory t = TerritoryForDeployment();
        if(t==null)return;
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chosenTerritory.setText(t.name);
                chosenTerritoryTroops.setText(String.valueOf(t.numberOfTroops));
                button.setBackgroundColor(Color.WHITE);
                button.setColorFilter(Color.WHITE);
                button.setImageDrawable(c.getDrawable(R.drawable.ic_arrow_downward_black_24dp));
            }
        });

        t.numberOfTroops+=NumberOfTroopsToDeploy();
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }
    public boolean canAttack(Territory from, Territory to){
        if (from.numberOfTroops>1&&from.adjacentTerritories.containsKey(to.name)){
            return true;
        }
        return false;
    }
    public boolean attack(final TextView chosenTerritory, final TextView chosenTerritoryTroops
                       , final FloatingActionButton button, final Activity c){
        Log.e("player","attack");
        final Territory to = AttackToTerritory();//can return null
        if(to==null){return false;}
        if(occupiedTerritories.containsKey(to.name))return false;
        //ui
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chosenTerritory.setText(to.name);
                chosenTerritoryTroops.setText(String.valueOf(to.numberOfTroops));
                button.setBackgroundColor(Color.YELLOW);
                button.setColorFilter(Color.YELLOW);
                button.setImageDrawable(c.getDrawable(R.drawable.attack_to_24dp));
            }
        });


        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final Territory from = AttackFromTerritory();
        //display to ui
        if(from==null){return false;}
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chosenTerritory.setText(from.name);
                chosenTerritoryTroops.setText(String.valueOf(from.numberOfTroops));
                button.setBackgroundColor(Color.GREEN);
                button.setColorFilter(Color.GREEN);
                button.setImageDrawable(c.getDrawable(R.drawable.attack_24dp));
            }
        });

        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!canAttack(from,to)){return false;}

        //roll the dice and return true if winner
        ArrayList <Integer> fromDice = new ArrayList<>();
        if (from.numberOfTroops>=3){
            fromDice.add(new Random().nextInt(6) + 1);
            fromDice.add(new Random().nextInt(6) + 1);
            fromDice.add(new Random().nextInt(6) + 1);
        }
        else if(from.numberOfTroops==2){
            fromDice.add(new Random().nextInt(6) + 1);
            fromDice.add(new Random().nextInt(6) + 1);
        }
        else if(from.numberOfTroops==1){
            fromDice.add(new Random().nextInt(6) + 1);
        }
       // if(to.numberOfTroops==0){invade(c,from,to); return true;}//if the offender killed all the defender's

        ArrayList <Integer> toDice = new ArrayList<>();
        if (from.numberOfTroops>=2){
            toDice.add(new Random().nextInt(6) + 1);
            toDice.add(new Random().nextInt(6) + 1);
        }
        else if(from.numberOfTroops==1){
            toDice.add(new Random().nextInt(6) + 1);
        }
        Collections.sort(fromDice);Collections.reverse(fromDice);
        Collections.sort(toDice);Collections.reverse(toDice);
        Log.e("from num troops",String.valueOf(from.numberOfTroops));
        for (int i : toDice){
            if(from.numberOfTroops<2)break;
            if(i>=fromDice.get(toDice.indexOf(i)))//if the defense wins
            {if(from.numberOfTroops>0)from.numberOfTroops--;}//kill one of the offender
            else{if(to.numberOfTroops>0)to.numberOfTroops--;}
        }
        if(to.numberOfTroops==0){invade(c,from,to); return true;}//if the offender killed all the defender's

        return true;
    }
     private void invade(final Activity c,Territory from, Territory to){
        if(from == null){return; }
        if(to == null){return; }
        occupiedTerritories.put(to.name,to);
        Log.e("from",from.name);
        Log.e("to",to.name);
        Game.Players.get(to.ownedBy.Name).occupiedTerritories.remove(to.name);
        //choose number of troops to move from from to to
        int troopsToRemove=0;
        troopsToRemove = from.numberOfTroops/2;
        from.numberOfTroops-=troopsToRemove;
        to.numberOfTroops=troopsToRemove;

        to.ownedBy=this;
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView pName = c.findViewById(R.id.PlayerName);
                pName.setText(Name);
                TextView pTerritories = c.findViewById(R.id.PlayerTerratories);
                pTerritories.setText(String.valueOf(occupiedTerritories.size()));
            }
        });
    }
    public void playTurn(){}
}
