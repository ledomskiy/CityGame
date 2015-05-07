package com.lpa.citygame.Entity;

public class PlayState {
    private final String ANDROID_PLAYER_NAME = "ANDROID";

    private String [] players;
	private boolean vsAndroid;
	private int indexCurrentPlayer;

    private static PlayState playState = null;
	
	private PlayState (){
		players = new String [2];
		indexCurrentPlayer = 0;
	}
	
	public String getPlayer1() {
		return players[0];
	}
	
	public String getPlayer2() {
		return players[1];
	}
	
	public boolean isVsAndroid() {
		return vsAndroid;
	}
	
	public void setPlayer1(String player) {
		players[0] = player;
	}
	
	public void setPlayer2(String player) {
		players[1] = player;
	}
	
	public void setVsAndroid(boolean vsAndroid) {
		this.vsAndroid = vsAndroid;
        if (vsAndroid) {
            players[1] = ANDROID_PLAYER_NAME;
        }
	}
	
	public static PlayState getInstance (){
		if (playState == null){
			playState = new PlayState ();
		}
		return playState;
	}
	
	// Перевод хода к следующему игроку
	public void switchCurrentPlayer (){
		indexCurrentPlayer = (indexCurrentPlayer + 1)%2;
	}
	
	// Получение игрока, который сейчас делает ход
	public String getCurrentPlayer (){
		return players [indexCurrentPlayer];
	}
	
	// Создание новой игры
	public void createNewGame(String player1, String player2){
		setPlayer1(player1);
		setPlayer2(player2);
	}

    public boolean isCurrentPlayerAndroid (){
        return vsAndroid && players [indexCurrentPlayer].equals(ANDROID_PLAYER_NAME);
    }
}
