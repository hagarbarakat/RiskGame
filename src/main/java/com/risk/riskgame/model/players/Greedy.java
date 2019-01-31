package com.risk.riskgame.model.players;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorTreeAdapter;
import android.widget.TextView;

import com.risk.riskgame.model.Game;
import com.risk.riskgame.model.GenericTree;
import com.risk.riskgame.model.GenericTreeNode;
import com.risk.riskgame.model.Player;
import com.risk.riskgame.model.Territory;

import org.w3c.dom.Node;

import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Timer;


public class Greedy extends Player {
    ArrayList<Territory> attack;
    ArrayList<GenericTreeNode> Greedy;
    GenericTreeNode root;
    GenericTreeNode g;


    public Greedy() {
        this.ID = 5;
        this.Name = "Greedy";
        attack = new ArrayList<>();
        Greedy = new ArrayList<>();
        root = new GenericTreeNode();
        g= new GenericTreeNode();

    }
    @Override
    public  Territory TerritoryForDeployment(){
        int min=10000;
        int BSR=0;
        int sum=0;
        Territory territory=null;
        for(Territory t: this.occupiedTerritories.values()){
            for(Territory s: t.adjacentTerritories.values()) {
                if (!occupiedTerritories.containsKey(s.name)) {
                    sum += s.numberOfTroops;
                    if(sum==0)
                        return null;
                    BSR = t.numberOfTroops / sum;
                    if (BSR < min) {
                        min = BSR;
                        territory = t;
                    }


                }
            }
        }
        return territory;
    }

    public void Search() {
        attack = new ArrayList<>();
        GenericTreeNode s;
        long t2 = 0;
        long t1 = System.currentTimeMillis();
        GenericTreeNode solution= new GenericTreeNode();


        GenericTreeNode min= new GenericTreeNode(null,10000000);
        for ( Territory t : this.occupiedTerritories.values()) {
            solution.occupied.add(t);
            min.occupied.add(t);
            }
        Territory x = TerritoryForDeployment();
        if(x==null)return;
        x.numberOfTroops+=NumberOfTroopsToDeploy();
        min.setData(x);
        // Log.e("min.occupied", String.valueOf(min.occupied.size()));
        root=min;
        Greedy.add(root);
        while(!(root.occupied.size() ==Game.Territories.size())&&(t2-t1)/1000<5) {
            //Log.e("while", "check");
            ArrayList<Territory> ter = new ArrayList<>();
            ArrayList<Territory> ter1 = root.occupied;
            for ( Territory t : ter1) {
                ter.add(t);
                //Log.e("ter.occupied", t.name);

            }
            //Log.e("root.occupied", String.valueOf(ter.size()));
            for (Territory t : ter) {
                //Log.e("for1", "check");
                for (Territory territory : t.adjacentTerritories.values()) {
                    //Log.e("for", "check");
                    //Log.e("territory", territory.name);
                    //Log.e("ter.contains:", String.valueOf(ter.contains(territory)));
                    // Log.e("t.numberofTroops", String.valueOf(t.numberOfTroops));
                    // Log.e("territory.numberTroops", String.valueOf(territory.numberOfTroops));
                    if (!contain(ter,territory) && t.numberOfTroops > territory.numberOfTroops) {
                        g = new GenericTreeNode(territory);
                        for ( Territory ter2 : ter) {
                            g.occupied.add(ter2);

                        }

                        // Log.e("g.occupied", String.valueOf(g.occupied.size()));
                        g.occupied.add(territory);
                        if(solution.occupied.size()<g.occupied.size()) {
                            solution=g;
                            root.addChild(g);
                            Greedy.add(g);
                            T++;

                            //  Log.e("ADD", territory.name);

                        }
                    }



                }


            }
            if(Greedy.size()>0){
                root=NextNode();
                //same.add(root);
            }
            t2 = System.currentTimeMillis();
        }
        while(solution!=null){
            Territory p= (Territory)solution.getData();
            attack.add(p);
            solution=solution.getParent();
        }

    }

    private GenericTreeNode NextNode() {
        int alpha = 300000000;
        int index = 0;
        for (int i=0; i<Greedy.size();i++) {
            int h = heuristic(Greedy.get(i));
            if (h < alpha) {
                alpha = h;
                index = Greedy.indexOf(Greedy.get(i));

            }
        }

        GenericTreeNode newNode = Greedy.get(index);
        Greedy.remove(index);
        return newNode;

    }

    public int heuristic(GenericTreeNode node) {
        int h=0;
        int sum=0;
        Territory territory;
        territory= (Territory)node.getData();
        for(Territory s: territory.adjacentTerritories.values()){
            if(this.occupiedTerritories.containsKey(s.name)){
                sum+=s.numberOfTroops;

            }
        }
        if(territory.numberOfTroops==0)
            return 0;
        h=sum/territory.numberOfTroops;


        return h;
    }



    @Override
    public Territory AttackToTerritory() {
        if (!attack.isEmpty()) {
            return attack.get(0);
        }
        return null;

    }

    @Override
    public boolean attack(TextView chosenTerritory, TextView chosenTerritoryTroops, FloatingActionButton button, Activity c) {
        Search();
        Log.e("attack list", String.valueOf(attack.size()));
        while(!attack.isEmpty()) {
            Log.e("A*", "attack");
            Territory from = AttackFromTerritory();
            Territory to = attack.get(0);
            boolean sucess = true;
            if(from!=null&&to!=null){
                while(from.numberOfTroops>to.numberOfTroops&&from.numberOfTroops>1&&sucess){
                    Log.e("attack","astar");
                    sucess=super.attack(chosenTerritory, chosenTerritoryTroops, button, c);
                }
            }
            attack.remove(0);

        }
        return true;

    }


    private boolean contain(ArrayList<Territory> x,Territory T){

        for(Territory t : x){
            if (t.name.equals(T.name)){return true;}
        }
        return false;
    }

}


