package protocolLobbyTools;

import java.util.StringTokenizer;

import protocolTools.BluffinCommandReceiver;
import utility.Constants;

public class LobbyClientSideReceiver extends BluffinCommandReceiver<LobbyServerSideListener>
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
