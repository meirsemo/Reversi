package com.example.user.reversi;


import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityMatch extends AppCompatActivity {
    MatchController matchController;
    LinearLayout timerLayout;
    GridLayout matchBoardLayout;
    ImageView startTimer;
    static ImageView[][] imageViewBoard = new ImageView[8][8];
    int currentRow, currentCol;
    static AnimationDrawable flipTheSlotAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        matchController = MatchController.getMatchControllerInstance();
        matchBoardLayout = findViewById(R.id.main_grid);
        for(currentRow=0; currentRow<8; currentRow++){
            for(currentCol=0; currentCol<8; currentCol++) {
                imageViewBoard[currentRow][currentCol] = (ImageView) matchBoardLayout.getChildAt(getIndexAtPos(currentRow, currentCol));
                imageViewBoard[currentRow][currentCol].setOnClickListener(new View.OnClickListener() {
                    private final int row = currentRow;
                    private final int col = currentCol;
                    @Override
                    public void onClick(View view) {
                        matchController.playMove(new BoardPosition(row, col));
                    }
                });
            }
        }
        matchController.startMatch("Meir", false, Color.Black, "AI", true);//todo must get from intent

        //startTimer =findViewById(R.id.start_timer);
        timerLayout = findViewById(R.id.timer_layout);
 /*       Switch switchBtn = findViewById(R.id.switch_btn);
        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                timerLayout.setVisibility(b ? View.VISIBLE : View.INVISIBLE);
            }
        });*/

        ImageButton settingsBtn = findViewById(R.id.setting_btn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMatch.this,ActivitySettings.class);
                startActivity(intent);
            }
        });
        ImageButton pauseBtn = findViewById(R.id.pause_btn);
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMatch.this);
                final View dialogView = getLayoutInflater().inflate(R.layout.pause_dialog, null);

                // Set the custom layout as alert dialog view
                builder.setView(dialogView);

                Button resumeBtn = dialogView.findViewById(R.id.resume_btn);
                Button settingBtn = dialogView.findViewById(R.id.setting_btn);
                ImageButton exitBtn = dialogView.findViewById(R.id.exit_btn);
                ImageButton restart = dialogView.findViewById(R.id.restart_btn);

                // Create the alert dialog
                final AlertDialog dialog = builder.create();

                resumeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                exitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ActivityMatch.this,MainActivity.class);
                        startActivity(intent);
                    }
                });
                restart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                settingBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ActivityMatch.this,ActivitySettings.class);
                        startActivity(intent);
                    }
                });
                // Display the custom alert dialog on interface
                dialog.show();
            }
        });
    }

    private int getIndexAtPos(int row, int col){
        int indexAtPosition = 0;

        indexAtPosition = (row*8) + col;
        return indexAtPosition;
    }

    public static void updateSlotsOnDisplay(BoardPosition position, ArrayList<MatchBoardSlot> slotsToUpdate, Color turn){
        ImageView flipper;
        flipper = imageViewBoard[position.getRow()][position.getColumn()];
        if(turn == Color.Black)
            flipper.setImageResource(R.drawable.gb);
        else
            flipper.setImageResource(R.drawable.gw);
        for (MatchBoardSlot stoneToFlip: slotsToUpdate) {
            flipper = imageViewBoard[stoneToFlip.getBoardPositionRow()][stoneToFlip.getBoardPositionColumn()];
            if(turn == Color.Black)
                flipper.setImageResource(R.drawable.white_to_black_flip);
            else
                flipper.setImageResource(R.drawable.black_to_white_flip);
            flipTheSlotAnimation = (AnimationDrawable)flipper.getDrawable();
            flipTheSlotAnimation.start();
        }

    }

    public static void displayLegalPositions(ArrayList<MatchBoardSlot> legalPositions){
        for (MatchBoardSlot legalSlot: legalPositions) {
            imageViewBoard[legalSlot.getBoardPositionRow()][legalSlot.getBoardPositionColumn()].setImageResource(R.drawable.white_dot);
        }
    }

    public static void removeLegalPositions(ArrayList<MatchBoardSlot> legalPositions){
        for (MatchBoardSlot legalSlotToRemove: legalPositions) {
            imageViewBoard[legalSlotToRemove.getBoardPositionRow()][legalSlotToRemove.getBoardPositionColumn()]
                    .setImageResource(0);
        }
    }

    public static void displaySkippedTurnMessage(Color turnSkipped){}//todo

    public static void initiateGameOverProcess(){}//todo

}