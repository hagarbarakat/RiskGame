package com.risk.riskgame.model;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.opengl.GLDebugHelper;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.TextView;

import com.risk.riskgame.R;
import com.risk.riskgame.model.players.Astar;
import com.risk.riskgame.model.players.Greedy;
import com.risk.riskgame.model.players.MiniMax;
import com.risk.riskgame.model.players.RTAstar;
import com.risk.riskgame.model.players.aggressive;
import com.risk.riskgame.model.players.human;
import com.risk.riskgame.model.players.pacifist;
import com.risk.riskgame.model.players.passive;

import org.w3c.dom.Text;

import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Game {

    public static HashMap<String,Player> Players=new HashMap<>();
    public static Player PlayerATM;
    public static HashMap<String,Territory> Territories=new HashMap<>();;
    public static boolean gameMode;
    public static Player winner=null;


    public static Player getPlayer(int choice){
        switch (choice){
            case 0:
                return new passive();
            case 1:
                return new aggressive();
            case 2:
                return new pacifist();
            case 3:
                return new human();
            case 4:
                return new MiniMax();
            case 5:
                return new Greedy();
            case 6:
                return new Astar();
            case 7:
                return new RTAstar();
        }
        return null;
    }
    public static void setupGame(boolean map, Activity c){
        if (!map){//case egypt
            Territory newVally = new Territory(c.getResources().getString(R.string.newValley),c.getResources().getString(R.string.CnewValley));

            Territory SixthOct = new Territory(c.getResources().getString(R.string.SixthOct),c.getResources().getString(R.string.CSixthOct));
            Territory Menia = new Territory(c.getResources().getString(R.string.Menia),c.getResources().getString(R.string.CMenia));
            Territory Assiut = new Territory(c.getResources().getString(R.string.Assiut),c.getResources().getString(R.string.CAssiut));
            Territory Sohag = new Territory(c.getResources().getString(R.string.Sohag),c.getResources().getString(R.string.CSohag));
            Territory RedSea = new Territory(c.getResources().getString(R.string.RedSea),c.getResources().getString(R.string.CRedSea));
            Territory NorthSinai = new Territory(c.getResources().getString(R.string.NorthSinai),c.getResources().getString(R.string.CNorthSinai));
            Territory SouthSinai = new Territory(c.getResources().getString(R.string.SouthSinai),c.getResources().getString(R.string.CSouthSinai));
            Territory Alexandria = new Territory(c.getResources().getString(R.string.Alexandria),c.getResources().getString(R.string.CAlexandria));
            Territory Qina = new Territory(c.getResources().getString(R.string.Qina),c.getResources().getString(R.string.CQina));
            Territory Aswan = new Territory(c.getResources().getString(R.string.Aswan),c.getResources().getString(R.string.CAswan));
            Territory BaniSuif = new Territory(c.getResources().getString(R.string.BaniSuif),c.getResources().getString(R.string.CBaniSuif));
            Territory Fayoum = new Territory(c.getResources().getString(R.string.Fayoum),c.getResources().getString(R.string.CFayoum));
            Territory Behira = new Territory(c.getResources().getString(R.string.Behira),c.getResources().getString(R.string.CBehira));
            Territory Suez = new Territory(c.getResources().getString(R.string.Suez),c.getResources().getString(R.string.CSuez));
            Territory Ismailia = new Territory(c.getResources().getString(R.string.Ismailia),c.getResources().getString(R.string.CIsmailia));
            Territory Sharqia = new Territory(c.getResources().getString(R.string.Sharqia),c.getResources().getString(R.string.CSharqia));
            Territory Gharbia = new Territory(c.getResources().getString(R.string.Gharbia),c.getResources().getString(R.string.CGharbia));
            Territory Daqahlia = new Territory(c.getResources().getString(R.string.Daqahlia),c.getResources().getString(R.string.CDaqahlia));
            Territory Menoufia = new Territory(c.getResources().getString(R.string.Menoufia),c.getResources().getString(R.string.CMenoufia));
            Territory Cairo = new Territory(c.getResources().getString(R.string.Cairo),c.getResources().getString(R.string.CCairo));
            Territory Giza = new Territory(c.getResources().getString(R.string.Giza),c.getResources().getString(R.string.CGiza));
            Territory KafrSheikh = new Territory(c.getResources().getString(R.string.KafrSheikh),c.getResources().getString(R.string.CKafrSheikh));
            Territory PortSaid = new Territory(c.getResources().getString(R.string.PortSaid),c.getResources().getString(R.string.CPortSaid));
            Territory Matrouh = new Territory(c.getResources().getString(R.string.Matrouh),c.getResources().getString(R.string.CMatrouh));

            newVally.adjacentTerritories.put(Matrouh.name,Matrouh);
            newVally.adjacentTerritories.put(SixthOct.name,SixthOct);
            newVally.adjacentTerritories.put(Menia.name,Menia);
            newVally.adjacentTerritories.put(Assiut.name,Assiut);
            newVally.adjacentTerritories.put(Sohag.name,Sohag);
            newVally.adjacentTerritories.put(Qina.name,Qina);
            newVally.adjacentTerritories.put(Aswan.name,Aswan);
            Matrouh.adjacentTerritories.put(newVally.name,newVally);
            Matrouh.adjacentTerritories.put(Alexandria.name,Alexandria);
            Matrouh.adjacentTerritories.put(SixthOct.name,SixthOct);
            Matrouh.adjacentTerritories.put(Behira.name,Behira);
            Alexandria.adjacentTerritories.put(Matrouh.name,Matrouh);
            Alexandria.adjacentTerritories.put(Behira.name,Behira);
            Behira.adjacentTerritories.put(SixthOct.name,SixthOct);
            Behira.adjacentTerritories.put(Alexandria.name,Alexandria);
            Behira.adjacentTerritories.put(Matrouh.name,Matrouh);
            Behira.adjacentTerritories.put(Menoufia.name,Menoufia);
            Behira.adjacentTerritories.put(Gharbia.name,Gharbia);
            Behira.adjacentTerritories.put(KafrSheikh.name,KafrSheikh);
            Menia.adjacentTerritories.put(SixthOct.name,SixthOct);
            Menia.adjacentTerritories.put(BaniSuif.name,BaniSuif);
            Menia.adjacentTerritories.put(RedSea.name,RedSea);
            Menia.adjacentTerritories.put(Assiut.name,Assiut);
            Menia.adjacentTerritories.put(newVally.name,newVally);
            BaniSuif.adjacentTerritories.put(Menia.name, Menia);
            BaniSuif.adjacentTerritories.put(RedSea.name,RedSea);
            BaniSuif.adjacentTerritories.put(SixthOct.name,SixthOct);
            BaniSuif.adjacentTerritories.put(Fayoum.name,Fayoum);
            BaniSuif.adjacentTerritories.put(Giza.name,Giza);
            SixthOct.adjacentTerritories.put(Matrouh.name,Matrouh);
            SixthOct.adjacentTerritories.put(newVally.name,newVally);
            SixthOct.adjacentTerritories.put(Fayoum.name,Fayoum);
            SixthOct.adjacentTerritories.put(BaniSuif.name,BaniSuif);
            SixthOct.adjacentTerritories.put(Menia.name,Menia);
            SixthOct.adjacentTerritories.put(Behira.name,Behira);
            SixthOct.adjacentTerritories.put(Menoufia.name,Menoufia);
            SixthOct.adjacentTerritories.put(Cairo.name,Cairo);
            SixthOct.adjacentTerritories.put(Giza.name,Giza);
            Fayoum.adjacentTerritories.put(BaniSuif.name,BaniSuif);
            Fayoum.adjacentTerritories.put(SixthOct.name,SixthOct);
            Assiut.adjacentTerritories.put(Menia.name,Menia);
            Assiut.adjacentTerritories.put(newVally.name,newVally);
            Assiut.adjacentTerritories.put(RedSea.name,RedSea);
            Assiut.adjacentTerritories.put(Sohag.name,Sohag);
            Sohag.adjacentTerritories.put(Assiut.name,Assiut);
            Sohag.adjacentTerritories.put(newVally.name,newVally);
            Sohag.adjacentTerritories.put(RedSea.name,RedSea);
            Sohag.adjacentTerritories.put(Qina.name,Qina);
            Qina.adjacentTerritories.put(Sohag.name,Sohag);
            Qina.adjacentTerritories.put(Aswan.name,Aswan);
            Qina.adjacentTerritories.put(RedSea.name,RedSea);
            Qina.adjacentTerritories.put(newVally.name,newVally);
            Aswan.adjacentTerritories.put(Qina.name,Qina);
            Aswan.adjacentTerritories.put(RedSea.name,RedSea);
            Aswan.adjacentTerritories.put(newVally.name,newVally);
            RedSea.adjacentTerritories.put(Aswan.name,Aswan);
            RedSea.adjacentTerritories.put(Qina.name,Qina);
            RedSea.adjacentTerritories.put(Sohag.name,Sohag);
            RedSea.adjacentTerritories.put(Assiut.name,Assiut);
            RedSea.adjacentTerritories.put(Menia.name,Menia);
            RedSea.adjacentTerritories.put(BaniSuif.name,BaniSuif);
            RedSea.adjacentTerritories.put(Suez.name,Suez);
            Giza.adjacentTerritories.put(BaniSuif.name,BaniSuif);
            Giza.adjacentTerritories.put(Cairo.name,Cairo);
            Giza.adjacentTerritories.put(SixthOct.name,SixthOct);
            Giza.adjacentTerritories.put(Suez.name,Suez);
            Cairo.adjacentTerritories.put(Giza.name,Giza);
            Cairo.adjacentTerritories.put(SixthOct.name,SixthOct);
            Cairo.adjacentTerritories.put(Sharqia.name,Sharqia);
            Cairo.adjacentTerritories.put(Menoufia.name,Menoufia);
            Cairo.adjacentTerritories.put(Daqahlia.name,Daqahlia);
            Cairo.adjacentTerritories.put(Ismailia.name,Ismailia);
            Cairo.adjacentTerritories.put(Suez.name,Suez);
            Suez.adjacentTerritories.put(Ismailia.name,Ismailia);
            Suez.adjacentTerritories.put(NorthSinai.name,NorthSinai);
            Suez.adjacentTerritories.put(SouthSinai.name,SouthSinai);
            Suez.adjacentTerritories.put(RedSea.name,RedSea);
            Suez.adjacentTerritories.put(Cairo.name,Cairo);
            Suez.adjacentTerritories.put(Giza.name,Giza);
            NorthSinai.adjacentTerritories.put(SouthSinai.name,SouthSinai);
            NorthSinai.adjacentTerritories.put(Ismailia.name,Ismailia);
            NorthSinai.adjacentTerritories.put(Suez.name,Suez);
            SouthSinai.adjacentTerritories.put(Suez.name,Suez);
            SouthSinai.adjacentTerritories.put(NorthSinai.name,NorthSinai);
            Ismailia.adjacentTerritories.put(NorthSinai.name,NorthSinai);
            Ismailia.adjacentTerritories.put(Suez.name,Suez);
            Ismailia.adjacentTerritories.put(PortSaid.name,PortSaid);
            Ismailia.adjacentTerritories.put(NorthSinai.name,NorthSinai);
            Ismailia.adjacentTerritories.put(Sharqia.name,Sharqia);
            Ismailia.adjacentTerritories.put(Cairo.name,Cairo);
            Sharqia.adjacentTerritories.put(Ismailia.name,Ismailia);
            Sharqia.adjacentTerritories.put(Cairo.name,Cairo);
            Sharqia.adjacentTerritories.put(PortSaid.name,PortSaid);
            Sharqia.adjacentTerritories.put(Daqahlia.name,Daqahlia);
            PortSaid.adjacentTerritories.put(Sharqia.name,Sharqia);
            PortSaid.adjacentTerritories.put(Ismailia.name,Ismailia);
            Daqahlia.adjacentTerritories.put(Sharqia.name,Sharqia);
            Daqahlia.adjacentTerritories.put(Cairo.name,Cairo);
            Daqahlia.adjacentTerritories.put(KafrSheikh.name,KafrSheikh);
            Daqahlia.adjacentTerritories.put(Gharbia.name,Gharbia);
            Daqahlia.adjacentTerritories.put(Menoufia.name,Menoufia);
            KafrSheikh.adjacentTerritories.put(Daqahlia.name,Daqahlia);
            KafrSheikh.adjacentTerritories.put(Gharbia.name,Gharbia);
            KafrSheikh.adjacentTerritories.put(Behira.name,Behira);
            Gharbia.adjacentTerritories.put(Cairo.name, Cairo);
            Gharbia.adjacentTerritories.put(KafrSheikh.name, KafrSheikh);
            Gharbia.adjacentTerritories.put(Menoufia.name, Menoufia);
            Gharbia.adjacentTerritories.put(Behira.name, Behira);
            Gharbia.adjacentTerritories.put(Daqahlia.name, Daqahlia);
            Menoufia.adjacentTerritories.put(Cairo.name,Cairo);
            Menoufia.adjacentTerritories.put(Sharqia.name,Sharqia);
            Menoufia.adjacentTerritories.put(Gharbia.name,Gharbia);
            Menoufia.adjacentTerritories.put(SixthOct.name,SixthOct);
            Menoufia.adjacentTerritories.put(Behira.name,Behira);



            Territories.put(newVally.color,newVally);Territories.put(SixthOct.color,SixthOct);Territories.put(Menia.color,Menia);
            Territories.put(Assiut.color,Assiut);Territories.put(Sohag.color,Sohag);Territories.put(RedSea.color,RedSea);
            Territories.put(NorthSinai.color,NorthSinai);Territories.put(SouthSinai.color,SouthSinai);Territories.put(Alexandria.color,Alexandria);
            Territories.put(Qina.color,Qina);Territories.put(Aswan.color,Aswan);Territories.put(BaniSuif.color,BaniSuif);
            Territories.put(Fayoum.color,Fayoum);Territories.put(Behira.color,Behira);Territories.put(Suez.color,Suez);
            Territories.put(Ismailia.color,Ismailia);Territories.put(Sharqia.color,Sharqia);Territories.put(Gharbia.color,Gharbia);
            Territories.put(Daqahlia.color,Daqahlia);Territories.put(Menoufia.color,Menoufia);Territories.put(Cairo.color,Cairo);
            Territories.put(Giza.color,Giza);Territories.put(KafrSheikh.color,KafrSheikh);Territories.put(PortSaid.color,PortSaid);
            Territories.put(Matrouh.color,Matrouh);


        }
        //give each player a random territory until territories are done
        ArrayList<Territory> copy = new ArrayList<>();
        for(Territory t : Territories.values()){
            copy.add(t);
        }
        while(!copy.isEmpty()){
            for(Player p: Players.values()){

                if(!copy.isEmpty()){
                    int rand = new Random().nextInt(copy.size());
                    p.occupiedTerritories.put(copy.get(rand).name,copy.get(rand));
                    Territories.get(copy.get(rand).color).ownedBy = p ;
                    copy.remove(rand);
                }
            }
        }

            for(Player p: Players.values()){
                while(p.troops>0){
                    for(Territory t: p.occupiedTerritories.values()){
                        if(p.troops>0){
                            t.numberOfTroops++;
                            p.troops--;
                        }
                    }
                }

            }


    }

    public static void startGameSimulation(final Activity c){//doesn't need the on touch
        final TextView playerName = c.findViewById(R.id.PlayerName);
        final TextView playerTerritories = c.findViewById(R.id.PlayerTerratories);

        final TextView chosenTerritory = c.findViewById(R.id.chosenTerritory);
        final TextView chosenTerritoryTroops = c.findViewById(R.id.choosenTRoops);
        final FloatingActionButton button = c.findViewById(R.id.floatingActionButton);
        while(winner==null){
            for(final Player p : Players.values()){
                if(winner!=null){break;}
                p.L++;
                PlayerATM = p;
                Log.wtf("turn", "turn" );
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        playerName.setText(p.Name);
                        playerTerritories.setText(String.valueOf(p.occupiedTerritories.size()));
                        chosenTerritory.setText("");
                        chosenTerritoryTroops.setText("");
                    }
                });

                p.deployTroops(chosenTerritory,chosenTerritoryTroops,button, c);
                p.attack(chosenTerritory,chosenTerritoryTroops, button, c);

                if(p.occupiedTerritories.size()==Game.Territories.size()){winner=p;}
            }

        }
        //after having a winner go to the summary
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent myIntent = new Intent(c, gameSummary.class);
                c.startActivity(myIntent);
            }
        });

    }


}
