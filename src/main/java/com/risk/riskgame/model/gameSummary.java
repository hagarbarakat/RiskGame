package com.risk.riskgame.model;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.risk.riskgame.R;

public class gameSummary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_summary);

        TextView winnerName = findViewById(R.id.winnerName);
        TextView winnerTurns = findViewById(R.id.winnerTurns);
        TextView winnerExpansions = findViewById(R.id.winnerExpansions);
        TextView winnerPerformance1 = findViewById(R.id.winnerPerformance1);
        TextView winnerPerformance100 = findViewById(R.id.winnerPerformance100);
        TextView winnerPerformance10000 = findViewById(R.id.winnerPerformance10000);

        winnerName.setText(Game.winner.Name);
        winnerTurns.setText(String.valueOf(Game.winner.L));
        winnerExpansions.setText(String.valueOf(Game.winner.T));
        winnerPerformance1.setText(String.valueOf(Game.winner.L+Game.winner.T));
        winnerPerformance100.setText(String.valueOf(100*Game.winner.L+Game.winner.T));
        winnerPerformance10000.setText(String.valueOf(10000*Game.winner.L+Game.winner.T));
    }
}
