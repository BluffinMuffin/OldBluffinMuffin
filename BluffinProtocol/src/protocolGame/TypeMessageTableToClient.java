package protocolGame;

/**
 * @author Hocus
 *         This enum list the type of message that the table can send to the client
 */
public enum TypeMessageTableToClient
{
    // Server to client
    WAITING_FOR_PLAYERS, GAME_STARTED, PLAYER_JOINED, PLAYER_LEFT, PLAYER_TURN_ENDED, PLAYER_TURN_BEGAN, TAKE_ACTION, BETTING_TURN_ENDED, PLAYER_CARD_CHANGED, BOARD_CHANGED, PLAYER_MONEY_CHANGED, POT_WON, GAME_ENDED, TABLE_CLOSED, TABLE_INFOS, PING
}
