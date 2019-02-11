package com.example.user.reversi;

public class MatchController {
    private MatchLogic matchLogic;
    private MatchBoard matchBoard;
    private Player blacksPlayer;
    private Player whitesPlayer;
    private boolean isAIInTheMatch;
    private Color turn;
    private boolean nextTurnToSkip;
    private static MatchController matchController = new MatchController();

    private MatchController(){
        matchBoard = MatchBoard.getMatchBoardInstance();
        matchLogic = new MatchLogic();
    }

    public static MatchController getMatchControllerInstance(){
        return matchController;
    }

    public void startMatch(String player1Name, boolean player1IsAI, Color player1Color, String player2Name, boolean player2IsAI){
        BoardPosition AIMove;
        initiateMatchBoard();
        if(player1Color == Color.Black){
            blacksPlayer = new Player(player1Name, player1IsAI, Color.Black);
            whitesPlayer = new Player(player2Name, player2IsAI, Color.White);
        }
        else{
            blacksPlayer = new Player(player2Name, player2IsAI, Color.Black);
            whitesPlayer = new Player(player1Name, player1IsAI, Color.White);
        }
        matchBoard.setAmountOfBlackSlots(2);
        matchBoard.setAmountOfWhiteSlots(2);
        setIsAIInTheMatch();
        turn = Color.Black;
        if(isAIInTheMatch && isAITurn()){
            matchLogic.setLegalPositions(turn);
            playAI();
            turn = matchBoard.getReverseColor(turn);
        }
        matchLogic.setLegalPositions(turn);
        showLegalPositions();//undo
        nextTurnToSkip = false;
    }

    public void playMove(BoardPosition position){// for onClick listeners
        if(matchLogic.isValidPlay(position, isAITurn())){
            playUser(position);
            if(!matchBoard.boardIsFull()) {
                turn = matchBoard.getReverseColor(turn);
                if (matchLogic.setLegalPositions(turn))
                    nextTurnToSkip = false;
                else {
                    nextTurnToSkip = true;
                    turn = matchBoard.getReverseColor(turn);
                    matchLogic.setLegalPositions(turn);
                    showLegalPositions();//undo
                }
                if (!nextTurnToSkip) {
                    if (isAIInTheMatch) {
                        do{
                            playAI();
                            turn = matchBoard.getReverseColor(turn);
                            if (matchLogic.setLegalPositions(turn)) {
                                nextTurnToSkip = false;
                                showLegalPositions();//undo
                            }
                            else {
                                nextTurnToSkip = true;
                                turn = matchBoard.getReverseColor(turn);
                                matchLogic.setLegalPositions(turn);
                            }
                        }while ((!matchBoard.boardIsFull()) && nextTurnToSkip);
                    }
                }
                if(matchBoard.boardIsFull())
                    gameOver();
            }
            else
                gameOver();
        }
    }

    private void playUser(BoardPosition position){
        matchLogic.updateMatchBoard(position.getRow(), position.getColumn(), turn, matchLogic.getDirection(position));
        ActivityMatch.removeLegalPositions(matchLogic.getLegalPositions());
        ActivityMatch.updateSlotsOnDisplay(position, matchLogic.getUpdatedStones(), turn);
    }

    private void playAI(){
        BoardPosition AIMove;
        AIMove = getAIMove();
        matchLogic.updateMatchBoard(AIMove.getRow(), AIMove.getColumn(), turn, matchLogic.getDirection(AIMove));
        ActivityMatch.updateSlotsOnDisplay(AIMove, matchLogic.getUpdatedStones(), turn);
    }

    private void showLegalPositions(){
        if(matchLogic.getLegalPositions() != null) {
            ActivityMatch.displayLegalPositions(matchLogic.getLegalPositions());
        }
    }

    private BoardPosition getAIMove(){
        if(isAIInTheGame())
            return matchLogic.getAIMove();
        else
            return null;
    }

    /*public Color getTurn(){
        return(blacksTurn ? Color.Black : Color.White);
    }*/

    private boolean isAITurn(){
        if(turn == Color.Black){
            return blacksPlayer.isAI();
        }
        else
            return whitesPlayer.isAI();
    }

    private boolean isAIInTheGame(){
        return isAIInTheMatch;
    }

    private Color getAIColor(){
        if(isAIInTheGame())
            return (blacksPlayer.isAI() ? Color.Black : Color.White);
        else
            return null;
    }

    private void gameOver(){
        ActivityMatch.initiateGameOverProcess();
    }

    public int getAmountOfBlackSlots() {
        return matchBoard.getAmountOfBlackSlots();
    }

    public int getAmountOfWhiteSlots() {
        return matchBoard.getAmountOfWhiteSlots();
    }

    /*public boolean gameOver(){
    }*/

    private void initiateMatchBoard(){
        matchBoard.cleanMatchBoard();
        matchLogic.configureSlotsToStartingPosition();
    }

    private void setIsAIInTheMatch(){
        if(blacksPlayer.isAI())
            isAIInTheMatch = true;
        else
            isAIInTheMatch = whitesPlayer.isAI();
    }


}
