package protocolLobby;

import java.util.StringTokenizer;

import protocol.PokerCommand;
import protocol.PokerCommandObserver;

public class LobbyClientSideObserver extends PokerCommandObserver<LobbyClientSideListener>
{
    @Override
    protected void commandReceived(String line)
    {
        final StringTokenizer token = new StringTokenizer(line, PokerCommand.DELIMITER);
        @SuppressWarnings("unused")
        final String commandName = token.nextToken();
        
        // Rien pour le moment, le client recoit tout tout croche !!
        super.commandReceived(line);
    }
}
