package protocolLobbyTools;

import java.util.StringTokenizer;

import protocolTools.BluffinCommandObserver;
import utility.Constants;

public class LobbyClientSideObserver extends BluffinCommandObserver<LobbyClientSideListener>
{
    @Override
    protected void onLineReceived(String line)
    {
        final StringTokenizer token = new StringTokenizer(line, Constants.DELIMITER);
        @SuppressWarnings("unused")
        final String commandName = token.nextToken();
        
        // Rien pour le moment, le client recoit tout tout croche !!
        super.onLineReceived(line);
    }
}
