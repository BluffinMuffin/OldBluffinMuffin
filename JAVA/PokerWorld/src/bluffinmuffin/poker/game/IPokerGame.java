package bluffinmuffin.poker.game;

import bluffinmuffin.poker.game.observer.IPokerGameListener;

public interface IPokerGame {

    /**
     * Debute l'ecoute de tous les evenements de la Game
     * 
     * @param listener
     */
    void attach(IPokerGameListener listener);

    /**
     * Termine l'ecoute de tous les evenements de la Game
     * 
     * @param listener
     */
    void detach(IPokerGameListener listener);

    /**
     * La table associee a la game
     * 
     * @return
     */
    TableInfo getTable();

    /**
     * L'argent jouee par un joueur precis
     * 
     * @param player
     * @param amount
     * @return
     */
    boolean playMoney(PlayerInfo player, int amount);

    /**
     * Un joueur precis quitte la Game
     * 
     * @param player
     * @return
     */
    boolean leaveGame(PlayerInfo player);

    public String encode();
}
