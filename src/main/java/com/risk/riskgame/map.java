package com.risk.riskgame;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.risk.riskgame.model.Game;
import com.risk.riskgame.model.Territory;
import com.risk.riskgame.model.players.human;

import static com.risk.riskgame.model.Game.Territories;
import static com.risk.riskgame.model.Game.gameMode;

public class map extends AppCompatActivity {

    Territory selectedTerritory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        final TextView territory = findViewById(R.id.TerritoryName);
        final TextView territoryTroops = findViewById(R.id.TerritoryTroops);
        final TextView territoryOwner = findViewById(R.id.territoryOwner);
        territory.setText("");
        territoryTroops.setText("0");
        territoryOwner.setText("0");

        if(!gameMode){
            human h = (human) Game.Players.get(Game.getPlayer(3).Name);
            h.c=map.this;
        }
        Thread t = new Thread((new Runnable() {
            @Override
            public void run() {
                Game.setupGame(false,map.this);
                Game.startGameSimulation(map.this);
            }
        }));
        t.start();


        findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Game.PlayerATM.getClass()==new human().getClass()){
                    human h = (human)Game.PlayerATM;

                    int state = h.state.get();
                    Log.e("stateMap",String.valueOf(state));
                    switch (state){
                        case 0:
                            //deploy
                            if(h.occupiedTerritories.containsKey(selectedTerritory.name)){
                                h.Deploy=selectedTerritory;
                                h.go.set(true);
                                Log.wtf("deploy", "oui" );
                            }
                            else{
                                Log.wtf("deploy", "failed" );
                                Toast toast=Toast.makeText(getApplicationContext(),"Choose a territory that's yours and try again!",Toast.LENGTH_LONG);
                                toast.show();
                            }
                            break;
                        case 1:
                            //attack to
                            boolean ok = false;
                            if(!h.occupiedTerritories.containsKey(selectedTerritory.name)){//if the territory isnt urs
                                for(Territory t: selectedTerritory.adjacentTerritories.values()){//if u have a territory adjacent to it
                                    if(h.occupiedTerritories.containsKey(t.name)){
                                        if(h.occupiedTerritories.get(t.name).numberOfTroops>2){
                                            ok = true;
                                            h.To = selectedTerritory;
                                            h.go.set(true);
                                        }
                                    }
                                }
                            }
                            if(ok==false){
                                Toast toast=Toast.makeText(getApplicationContext(),"Choose a territory that you can attack and try again!",Toast.LENGTH_LONG);
                                toast.show();
                            }
                            break;
                        case 2:
                            //attack from
                            Territory to = h.To;
                            if(selectedTerritory.numberOfTroops>1&&selectedTerritory.adjacentTerritories.containsKey(to.name)){
                                h.From = selectedTerritory;
                                h.go.set(true);
                            }
                            else{
                                Toast toast=Toast.makeText(getApplicationContext(),"Choose a territory that you can attack the previously chosen one from and try again!",Toast.LENGTH_LONG);
                                toast.show();
                            }
                            break;

                    }
                }
            }
        });

        ///todo: on hold end turn
        findViewById(R.id.floatingActionButton).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.wtf("long press", "oui" );
                if (Game.PlayerATM.getClass() == new human().getClass()) {
                    human h = (human) Game.PlayerATM;
                    h.endTurn.set(true);
                }
                return true;
            }
        });

        final ImageView img = findViewById(R.id.imageView);

        img.setOnTouchListener(new View.OnTouchListener()
        {

            public boolean onTouch(View v, MotionEvent event) {

                Bitmap bmp = ((BitmapDrawable) img.getDrawable()).getBitmap();
                float x= event.getX();
                float y= event.getY();

                try {
                    int pixel = bmp.getPixel((int) x, (int) y);
                    int redValue = Color.red(pixel);
                    int blueValue = Color.blue(pixel);
                    int greenValue = Color.green(pixel);
                    String red = Integer.toString(redValue);
                    String blue = Integer.toString(blueValue);
                    String green = Integer.toString(greenValue);
                    Log.e("color", red +green +blue );
                    String s =red +green +blue;
                    Toast toast=Toast.makeText(getApplicationContext(),Game.Territories.get(s).name,Toast.LENGTH_LONG);
                    //toast.show();
                    selectedTerritory = Game.Territories.get(s);
                    territory.setText(selectedTerritory.name);
                    territoryTroops.setText(String.valueOf(selectedTerritory.numberOfTroops));
                    territoryOwner.setText(selectedTerritory.ownedBy.Name.toString());
                } catch (Exception e) {
                    Log.e("color", e.toString() );
                }

                return false;
            }

        });
    }

    @Override
    public void onBackPressed() {
        //2175000966942201012
    }




}
