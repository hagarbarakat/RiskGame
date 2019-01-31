package com.risk.riskgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.risk.riskgame.model.Game;
import com.risk.riskgame.model.players.human;

public class RiskGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_game);
        findViewById(R.id.simulation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Game.gameMode=true;
                Intent myIntent = new Intent(RiskGame.this, ChoosePlayer.class);
                startActivity(myIntent);
            }
        });
        findViewById(R.id.playing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Game.gameMode=false;
                Game.Players.put(Game.getPlayer(3).Name,Game.getPlayer(3));
                Intent myIntent = new Intent(RiskGame.this, ChoosePlayer.class);
                startActivity(myIntent);
            }
        });
    }
}
