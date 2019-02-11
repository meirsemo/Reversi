package com.example.user.reversi;


import android.graphics.Path;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class MatchLogic {
    private MatchBoard matchBoard;
    private ArrayList<MatchBoardSlot> legalPositions = new ArrayList<>();
    private ArrayList<MatchBoardSlot> updatedStones = new ArrayList<>();

    DirectionToSlot vectors[] = new DirectionToSlot[]{
            new DirectionToSlot(0, -1),
            new DirectionToSlot(1, -1),
            new DirectionToSlot(1, 0),
            new DirectionToSlot(1, 1),
            new DirectionToSlot(0, 1),
            new DirectionToSlot(-1, 1),
            new DirectionToSlot(-1, 0),
            new DirectionToSlot(-1, -1),
    };
    /*private BoardPosition seekToNorth, seekToNE, seekToEast, seekToSE, seekToSouth,
                            seekToSW, seekToWest, seekToNW;
    private boolean isAtNorth, isAtNE, isAtEast, isAtSE, isAtSouth, isAtSW, isAtWest, isAtNW;*/

    public MatchLogic() {
        matchBoard = MatchBoard.getMatchBoardInstance();
    }

    public ArrayList<MatchBoardSlot> getLegalPositions() {
        return legalPositions;
    }

    public ArrayList<MatchBoardSlot> getUpdatedStones() {
        return updatedStones;
    }

    public void configureSlotsToStartingPosition(){
        matchBoard.setMatchBoardSlotColor(3, 3, Color.White);
        matchBoard.setMatchBoardSlotColor(3, 4, Color.Black);
        matchBoard.setMatchBoardSlotColor(4, 3, Color.Black);
        matchBoard.setMatchBoardSlotColor(4, 4, Color.White);
    }

    public void updateMatchBoard(int row, int column, Color playedStoneColor, int direction){
        if(!updatedStones.isEmpty())
            updatedStones.clear();
        matchBoard.setMatchBoardSlotColor(row, column, playedStoneColor);
        updateStones(row, column, playedStoneColor, direction);
        StringBuilder boardRow = new StringBuilder();
        boardRow.append("printing board:\n" + playedStoneColor + "\n");
        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++) {
                if (matchBoard.getMatchBoardSlotColor(i, j) == Color.Empty)
                    boardRow.append('-');
                else if(matchBoard.getMatchBoardSlotColor(i, j) == Color.Black)
                    boardRow.append('X');
                else
                    boardRow.append('O');
            }
            boardRow.append('\n');
        }
        matchBoard.updateStoneBalance(updatedStones.size(), playedStoneColor);
        Log.d("boardPrint", boardRow.toString()+"\n"+matchBoard.getAmountOfBlackSlots()+" "+
                matchBoard.getAmountOfWhiteSlots());

    }

    public boolean setLegalPositions(Color currentPlayerStoneColor){
        int sRow, sCol;
        ArrayList<MatchBoardSlot> slotsWithOppositeColorNeighbors = matchBoard.getAllBlockedSlots(currentPlayerStoneColor);
        /*for (MatchBoardSlot slot: slotsWithOppositeColorNeighbors) {//log
            Log.d("oppositeColor", "row "+slot.getBoardPositionRow()+"col "+slot.getBoardPositionColumn()+"\n");
        }*/
        if(!legalPositions.isEmpty())
            legalPositions.clear();
        for (MatchBoardSlot slotWithOppositeColorNeighbor:slotsWithOppositeColorNeighbors) {
            sRow=slotWithOppositeColorNeighbor.getBoardPosition().getRow();
            sCol=slotWithOppositeColorNeighbor.getBoardPosition().getColumn();
            allDirectionsCheck(sRow, sCol, slotWithOppositeColorNeighbor.getStoneColor(), Color.Empty);
            for(int i=0; i<8; i++) {
                if (vectors[i].isWantedSlotInDirection())
                    legalPositions.add(matchBoard.getMatcBoardSlot(vectors[i].getRowOfWantedSlot(),
                            vectors[i].getColumnOfWantedSlot()));
            }
           /* for (MatchBoardSlot slot: legalPositions) {//log
                Log.d("legalPos", "row "+slot.getBoardPositionRow()+"col "+slot.getBoardPositionColumn()+"\n");
            }*/
        }
        //for (MatchBoardSlot slot: legalPositions) {//log
        //    Log.d("legalPos", "row "+slot.getBoardPositionRow()+"col "+slot.getBoardPositionColumn()+"\n");
        //}
        if(legalPositions.isEmpty())
            return false;
        else
            return true;
    }

    public boolean isValidPlay(BoardPosition position, boolean isAITurn){
        boolean playIsValid = false;
        if(!isAITurn){
            if(matchBoard.isUnOccupiedSlot(position)){
                for (MatchBoardSlot slot : legalPositions) {
                    if(position.isSamePosition(slot.getBoardPosition()))
                        playIsValid = true;
                }
            }
        }
        return playIsValid;
    }

    public BoardPosition getAIMove(){//todo
        int amountOfAILegalPositions = legalPositions.size();
        Random random = new Random();
        return legalPositions.get(random.nextInt(amountOfAILegalPositions)).getBoardPosition();
    }

    private void updateStones(int row, int column, Color playedStoneColor, int direction) {
        //updateDirection(row, column, playedStoneColor, direction);
        allDirectionsCheck(row, column, playedStoneColor, playedStoneColor);
        allDirectionsUpdate(row, column);
        /*if(isSameColorAtNorth(row, column, playedStoneColor))
            updateToNorth(new BoardPosition(--row, column), playedStoneColor);
        if(isSameColorAtNE(row, column, playedStoneColor))
            updateToNE(new BoardPosition(--row, ++column), playedStoneColor);
        if(isSameColorAtEast(row, column, playedStoneColor))
            updateToEast(new BoardPosition(row, ++column), playedStoneColor);
        if(isSameColorAtSE(row, column, playedStoneColor))
            updateToSE(new BoardPosition(++row, ++column), playedStoneColor);
        if(isSameColorAtSouth(row, column, playedStoneColor))
            updateToSouth(new BoardPosition(++row, column), playedStoneColor);
        if(isSameColorAtSW(row, column, playedStoneColor))
            updateToSW(new BoardPosition(++row, --column), playedStoneColor);
        if(isSameColorAtWest(row, column, playedStoneColor))
            updateToWest(new BoardPosition(row, --column), playedStoneColor);
        if(isSameColorAtNW(row, column, playedStoneColor))
            updateToNW(new BoardPosition(--row, --column), playedStoneColor);*/
    }

    private void allDirectionsCheck(int srcRow, int srcColumn, Color srcColor, Color dstColor){
        int xDirection, yDirection, rowTemp, columnTemp, countOpposite;
        initiateIsAtDirection();
        for(int i = 0; i < 8; i++){
            xDirection = vectors[i].getxDirection();
            yDirection = vectors[i].getyDirection();
            rowTemp = srcRow + yDirection;
            columnTemp = srcColumn + xDirection;
            for(countOpposite=0; isValidPosition(rowTemp, columnTemp)
                    && matchBoard.isOppositeColorAtPosition(rowTemp, columnTemp, srcColor);
                rowTemp+=yDirection, columnTemp+=xDirection, countOpposite++);
            if(isValidPosition(rowTemp, columnTemp) && countOpposite>0){
                if (matchBoard.getMatchBoardSlotColor(rowTemp, columnTemp) == dstColor) {
                    vectors[i].setWantedSlotInDirection(true);
                    vectors[i].setBoardPositionOfWantedSlot(new BoardPosition(rowTemp, columnTemp));
                }
            }
        }
        Log.d("allDirectionsCheckFor", "srcPos "+srcRow+" "+srcColumn+"\n");//log
        for(int i=0; i<8; i++)//log
            if(vectors[i].getBoardPositionOfWantedSlot() != null)
                Log.d("allDirectionsCheck", "dstPos "+i+" "+vectors[i].getRowOfWantedSlot()+" "+vectors[i].getColumnOfWantedSlot()+"\n");
    }

    private void allDirectionsUpdate(int srcRow, int srcColumn){
        int dRow, dCol, xDirection, yDirection;
        for(int i=0; i<8; i++){
            if(vectors[i].isWantedSlotInDirection()){
                dRow = vectors[i].getBoardPositionOfWantedSlot().getRow();
                dCol = vectors[i].getBoardPositionOfWantedSlot().getColumn();
                xDirection = vectors[i].getxDirection();
                yDirection = vectors[i].getyDirection();
                for(int tRow=srcRow+yDirection, tCol=srcColumn+xDirection; tRow!=dRow || tCol!=dCol; tRow+=yDirection, tCol+=xDirection) {
                    updatedStones.add(matchBoard.getMatcBoardSlot(tRow, tCol));
                    matchBoard.turnOverAtPosition(tRow, tCol);
                }
            }
        }
    }

    private void updateDirection(int srcRow, int srcColumn, Color playedStone, int direction){
        int dRow, dCol, xDirection, yDirection;
        if(direction>=0) {
            dRow = vectors[direction].getBoardPositionOfWantedSlot().getRow();
            dCol = vectors[direction].getBoardPositionOfWantedSlot().getColumn();
            xDirection = vectors[direction].getxDirection();
            yDirection = vectors[direction].getyDirection();
            for (int tRow = srcRow, tCol = srcColumn; tRow != dRow && tCol != dCol; tRow += yDirection, tCol += xDirection) {
                updatedStones.add(matchBoard.getMatcBoardSlot(tRow, tCol));
                matchBoard.turnOverAtPosition(tRow, tCol);
            }
        }
    }

    private void initiateIsAtDirection(){
        for (DirectionToSlot vector: vectors) {
            vector.setWantedSlotInDirection(false);
        }
    }

    private boolean isValidPosition(int row, int column){
        if((row > 7) || (row < 0) || (column > 7) || (column < 0))
            return false;
        else
            return true;
    }

    public int getDirection(BoardPosition position){
        for(int i=0; i<8; i++){
            if(vectors[i].getBoardPositionOfWantedSlot() != null) {
                if (position.isSamePosition(vectors[i].getBoardPositionOfWantedSlot()))
                    return i;
            }
        }
        return -1;
    }
   /* private boolean isSameColorAtNorth(int row, int column, Color playedStoneColor) {
        isAtNorth = false;
        int rowTemp = row-1;
        if(rowTemp>=1){
            if(matchBoard.isOppositeColorAtPosition(rowTemp, column, playedStoneColor)){
                for(--rowTemp; rowTemp>=0 && !(matchBoard.isEmptyAtPosition(rowTemp, column))
                                                            && isAtNorth == false; rowTemp--){
                    if(matchBoard.getMatchBoardSlotColor(rowTemp, column) == playedStoneColor) {
                        isAtNorth = true;
                        seekToNorth = new BoardPosition(++rowTemp, column);
                    }
                }
            }
        }
        return isAtNorth;
    }
    private boolean isSameColorAtNE(int row, int column, Color playedStoneColor) {
        isAtNE = false;
        int rowTemp = row-1;
        int columnTemp = column+1;
        if(rowTemp>=1 && columnTemp<=6)
            for(; rowTemp>=0 && columnTemp<=7
                    && matchBoard.isOppositeColorAtPosition(rowTemp, columnTemp, playedStoneColor);
                                                                            rowTemp--, columnTemp++);
        if(rowTemp>=0 && columnTemp<=7) {
            if (matchBoard.getMatchBoardSlotColor(rowTemp, columnTemp) == playedStoneColor) {
                isAtNE = true;
                seekToNE = new BoardPosition(++rowTemp, --columnTemp);
            }
        }
        return isAtNE;
    }
    private boolean isSameColorAtNW(int row, int column, Color playedStoneColor) {
        isAtNW = false;
        int rowTemp = row-1;
        int columnTemp = column-1;
        if(rowTemp>=1 && columnTemp>=1)
            for(; rowTemp>=0 && columnTemp>=0
                    && matchBoard.isOppositeColorAtPosition(rowTemp, columnTemp, playedStoneColor);
                rowTemp--, columnTemp--);
        if(rowTemp>=0 && columnTemp>=0) {
            if (matchBoard.getMatchBoardSlotColor(rowTemp, columnTemp) == playedStoneColor) {
                isAtNW = true;
                seekToNW = new BoardPosition(++rowTemp, ++columnTemp);
            }
        }
        return isAtNW;
    }
    private boolean isSameColorAtSouth(int row, int column, Color playedStoneColor) {
        isAtSouth = false;
        int rowTemp = row+1;
        if(rowTemp<=6)
            for(; rowTemp<=7 && matchBoard.isOppositeColorAtPosition(rowTemp, column, playedStoneColor); rowTemp++);
        if(rowTemp<=7) {
            if (matchBoard.getMatchBoardSlotColor(rowTemp, column) == playedStoneColor) {
                isAtSouth = true;
                seekToSouth = new BoardPosition(--rowTemp, column);
            }
        }
        return isAtSouth;
    }
    private boolean isSameColorAtSE(int row, int column, Color playedStoneColor) {
        isAtSE = false;
        int rowTemp = row+1;
        int columnTemp = column+1;
        if(rowTemp<=6 && columnTemp<=6)
            for(; rowTemp<=7 && columnTemp<=7
                    && matchBoard.isOppositeColorAtPosition(rowTemp, columnTemp, playedStoneColor);
                rowTemp++, columnTemp++);
        if(rowTemp<=7 && columnTemp<=7) {
            if (matchBoard.getMatchBoardSlotColor(rowTemp, columnTemp) == playedStoneColor) {
                isAtSE = true;
                seekToSE = new BoardPosition(--rowTemp, --columnTemp);
            }
        }
        return isAtSE;
    }
    private boolean isSameColorAtSW(int row, int column, Color playedStoneColor) {
        isAtSW = false;
        int rowTemp = row+1;
        int columnTemp = column-1;
        if(rowTemp<=6 && columnTemp>=1)
            for(; rowTemp<=7 && columnTemp>=0
                    && matchBoard.isOppositeColorAtPosition(rowTemp, columnTemp, playedStoneColor);
                rowTemp++, columnTemp--);
        if(rowTemp<=7 && columnTemp>=0) {
            if (matchBoard.getMatchBoardSlotColor(rowTemp, columnTemp) == playedStoneColor) {
                isAtSW = true;
                seekToSW = new BoardPosition(--rowTemp, ++columnTemp);
            }
        }
        return isAtSW;
    }
    private boolean isSameColorAtEast(int row, int column, Color playedStoneColor) {
        isAtEast = false;
        int columnTemp = column+1;
        if(columnTemp<=6)
            for(; columnTemp<=7 && matchBoard.isOppositeColorAtPosition(row, columnTemp, playedStoneColor); columnTemp++);
        if(columnTemp<=7) {
            if (matchBoard.getMatchBoardSlotColor(row, columnTemp) == playedStoneColor) {
                isAtEast = true;
                seekToEast = new BoardPosition(row, --columnTemp);
            }
        }
        return isAtEast;
    }
    private boolean isSameColorAtWest(int row, int column, Color playedStoneColor) {
        isAtWest = false;
        int columnTemp = column-1;
        if(columnTemp>=1)
            for(; columnTemp>=0 && matchBoard.isOppositeColorAtPosition(row, columnTemp, playedStoneColor); columnTemp--);
        if(columnTemp>=0) {
            if (matchBoard.getMatchBoardSlotColor(row, columnTemp) == playedStoneColor) {
                isAtWest = true;
                seekToWest = new BoardPosition(row, ++columnTemp);
            }
        }
        return isAtEast;
    }
    private void updateToNorth(BoardPosition startPos, Color color){
        int destRow = seekToNorth.getRow();
        int column = startPos.getColumn();
        for(int i=startPos.getRow(); i>=destRow; i--){
            matchBoard.setMatchBoardSlotColor(i, column, color);
            updatedStones.add(matchBoard.getMatcBoardSlot(i, column));
        }
    }
    private void updateToNE(BoardPosition startPos, Color color){
        int destRow = seekToNE.getRow();
        for(int i=startPos.getRow(), j=startPos.getColumn(); i>=destRow; i--, j++){
            matchBoard.setMatchBoardSlotColor(i, j, color);
            updatedStones.add(matchBoard.getMatcBoardSlot(i, j));
        }
    }
    private void updateToNW(BoardPosition startPos, Color color){
        int destRow = seekToNW.getRow();
        for(int i=startPos.getRow(), j=startPos.getColumn(); i>=destRow; i--, j--){
            matchBoard.setMatchBoardSlotColor(i, j, color);
            updatedStones.add(matchBoard.getMatcBoardSlot(i, j));
        }
    }
    private void updateToSouth(BoardPosition startPos, Color color){
        int destRow = seekToSouth.getRow();
        int column = startPos.getColumn();
        for(int i=startPos.getRow(); i<=destRow; i++){
            matchBoard.setMatchBoardSlotColor(i, column, color);
            updatedStones.add(matchBoard.getMatcBoardSlot(i, column));
        }
    }
    private void updateToSE(BoardPosition startPos, Color color){
        int destRow = seekToSE.getRow();
        for(int i=startPos.getRow(), j=startPos.getColumn(); i<=destRow; i++, j++){
            matchBoard.setMatchBoardSlotColor(i, j, color);
            updatedStones.add(matchBoard.getMatcBoardSlot(i, j));
        }
    }
    private void updateToSW(BoardPosition startPos, Color color){
        int destRow = seekToSW.getRow();
        for(int i=startPos.getRow(), j=startPos.getColumn(); i<=destRow; i++, j--){
            matchBoard.setMatchBoardSlotColor(i, j, color);
            updatedStones.add(matchBoard.getMatcBoardSlot(i, j));
        }
    }
    private void updateToEast(BoardPosition startPos, Color color){
        int destColumn = seekToEast.getColumn();
        int row = startPos.getRow();
        for(int i=startPos.getColumn(); i<=destColumn; i++){
            matchBoard.setMatchBoardSlotColor(row, i, color);
            updatedStones.add(matchBoard.getMatcBoardSlot(row, i));
        }
    }
    private void updateToWest(BoardPosition startPos, Color color){
        int destColumn = seekToWest.getColumn();
        int row = startPos.getRow();
        for(int i=startPos.getColumn(); i>=destColumn; i--){
            matchBoard.setMatchBoardSlotColor(row, i, color);
            updatedStones.add(matchBoard.getMatcBoardSlot(row, i));
        }
    }*/

}
