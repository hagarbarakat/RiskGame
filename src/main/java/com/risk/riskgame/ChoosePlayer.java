package com.risk.riskgame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.risk.riskgame.model.Game;

public class ChoosePlayer extends AppCompatActivity {
    int chosenPlayers=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_player);


        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent myIntent = new Intent(ChoosePlayer.this, map.class);
                        startActivity(myIntent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

            findViewById(R.id.passive).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Game.Players.put(Game.getPlayer(0).Name,Game.getPlayer(0));
                    chosenPlayers++;
                    if(Game.gameMode&&chosenPlayers==1) {
                        Toast toast=Toast.makeText(getApplicationContext(),"Choose second player",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(ChoosePlayer.this);
                        builder.setMessage("Choose Map").setPositiveButton("Egypt", dialogClickListener)
                                .setNegativeButton("America", dialogClickListener).show();

                    }
                }
            });

            findViewById(R.id.agressive).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Game.Players.put(Game.getPlayer(1).Name,Game.getPlayer(1));
                    chosenPlayers++;
                    if(Game.gameMode&&chosenPlayers==1) {

                        Toast toast=Toast.makeText(getApplicationContext(),"Choose second player",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(ChoosePlayer.this);
                        builder.setMessage("Choose Map").setPositiveButton("Egypt", dialogClickListener)
                                .setNegativeButton("America", dialogClickListener).show();

                    }
                }
            });
        findViewById(R.id.pacifist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Game.Players.put(Game.getPlayer(2).Name,Game.getPlayer(2));
                chosenPlayers++;
                if(Game.gameMode&&chosenPlayers==1) {
                    Toast toast=Toast.makeText(getApplicationContext(),"Choose second player",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChoosePlayer.this);
                    builder.setMessage("Choose Map").setPositiveButton("Egypt", dialogClickListener)
                            .setNegativeButton("America", dialogClickListener).show();
                }
            }
        });
        findViewById(R.id.Greedy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Game.Players.put(Game.getPlayer(5).Name,Game.getPlayer(5));
                chosenPlayers++;
                if(Game.gameMode&&chosenPlayers==1) {
                    Toast toast=Toast.makeText(getApplicationContext(),"Choose second player",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChoosePlayer.this);
                    builder.setMessage("Choose Map").setPositiveButton("Egypt", dialogClickListener)
                            .setNegativeButton("America", dialogClickListener).show();
                }
            }
        });
        findViewById(R.id.AStar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Game.Players.put(Game.getPlayer(6).Name,Game.getPlayer(6));
                chosenPlayers++;
                if(Game.gameMode&&chosenPlayers==1) {
                    Toast toast=Toast.makeText(getApplicationContext(),"Choose second player",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChoosePlayer.this);
                    builder.setMessage("Choose Map").setPositiveButton("Egypt", dialogClickListener)
                            .setNegativeButton("America", dialogClickListener).show();
                }
            }
        });
        findViewById(R.id.minimax).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Game.Players.put(Game.getPlayer(4).Name,Game.getPlayer(4));
                chosenPlayers++;
                if(Game.gameMode&&chosenPlayers==1) {
                    Toast toast=Toast.makeText(getApplicationContext(),"Choose second player",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChoosePlayer.this);
                    builder.setMessage("Choose Map").setPositiveButton("Egypt", dialogClickListener)
                            .setNegativeButton("America", dialogClickListener).show();
                }
            }
        });
        findViewById(R.id.rta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Game.Players.put(Game.getPlayer(7).Name,Game.getPlayer(7));
                chosenPlayers++;
                if(Game.gameMode&&chosenPlayers==1) {
                    Toast toast=Toast.makeText(getApplicationContext(),"Choose second player",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChoosePlayer.this);
                    builder.setMessage("Choose Map").setPositiveButton("Egypt", dialogClickListener)
                            .setNegativeButton("America", dialogClickListener).show();
                }
            }
        });


    }
}
