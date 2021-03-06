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

public class RTAstar extends Player {

    ArrayList<Territory> attack;
    ArrayList<GenericTreeNode>same;
    ArrayList<GenericTreeNode> RTAstar;
    GenericTreeNode root;
    GenericTreeNode g;



    public RTAstar(){
        this.ID=7;
        this.Name="RTA*";
        attack=new ArrayList<>();
        same=new ArrayList<>();
        RTAstar=new ArrayList<>();
        root=new GenericTreeNode<Territory>();
        g=new GenericTreeNode<Territory>();

    }
    @Override
    public  Territory TerritoryForDeployment(){
        int min=10000;
        int BSR=0;
        int sum=0;
        Territory territory=null;
        for(Territory t: this.occupiedTerritories.values()){
            for(Territory s: t.adjacentTerritories.values()){
                if(!occupiedTerritories.containsKey(s.name)){
                    sum+=s.numberOfTroops;
                    if(t.numberOfTroops==0)
                        return null;
                    BSR=sum/t.numberOfTroops;
                    if(BSR<min){
                        min=BSR;
                        territory=t;
                    }

                }
            }
        }
        return territory;
    }

    public void Search() {
        attack = new ArrayList<>();
        GenericTreeNode s;
        same.clear();
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
        RTAstar.add(root);
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
                    if (!occupiedTerritories.containsKey(territory.name)&&!contain(ter,territory) && t.numberOfTroops > territory.numberOfTroops) {
                        g = new GenericTreeNode(territory);
                        for ( Territory ter2 : ter) {
                            g.occupied.add(ter2);

                        }

                        // Log.e("g.occupied", String.valueOf(g.occupied.size()));
                        g.occupied.add(territory);
                        if(solution.occupied.size()<g.occupied.size()) {
                            solution=g;
                            root.addChild(g);
                            RTAstar.add(g);
                            T++;

                            //  Log.e("ADD", territory.name);

                        }
                    }



                }


            }
            if(RTAstar.size()>0){
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


    public GenericTreeNode NextNode() {
        int alpha = 3000000;
        int h = 0;
        int index=0;
        for (GenericTreeNode n : RTAstar) {
            h = heuristic(n);
            if (h < alpha) {
                alpha = h;
                index = RTAstar.indexOf(n);
            }
        }
        GenericTreeNode newNode= RTAstar.get(index);
        RTAstar.remove(index);
        return newNode;

    }


    public int heuristic(GenericTreeNode n){
        int h=0;
        int sum=0;
        int previousCost=0;

        Territory territory= (Territory)n.getData();
        for(Territory s: territory.adjacentTerritories.values()){
            if(!occupiedTerritories.containsKey(s.name)){
                sum+=s.numberOfTroops;
            }
        }
        if(territory.numberOfTroops==0)
            return 0;
        h =sum/territory.numberOfTroops;;
        n.setH(h);
        if(n.getParent()!=null){
            previousCost=n.getParent().getH();
               }
            h=previousCost+h;


        return h;
    }

    @Override
    public Territory AttackToTerritory() {
        if(!attack.isEmpty()){
            return  attack.get(0);}
        return null;

    }

    @Override
    public boolean attack(TextView chosenTerritory, TextView chosenTerritoryTroops, FloatingActionButton button, Activity c) {
        Search();
        while(!attack.isEmpty()) {
            Log.e("RTA*", "attack");
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
