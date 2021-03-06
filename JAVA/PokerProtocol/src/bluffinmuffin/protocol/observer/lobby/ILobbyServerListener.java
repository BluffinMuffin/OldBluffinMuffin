package bluffinmuffin.protocol.observer.lobby;

import bluffinmuffin.protocol.commands.DisconnectCommand;
import bluffinmuffin.protocol.commands.lobby.GameCommand;
import bluffinmuffin.protocol.commands.lobby.JoinTableCommand;
import bluffinmuffin.protocol.commands.lobby.ListTableCommand;
import bluffinmuffin.protocol.commands.lobby.career.AuthenticateUserCommand;
import bluffinmuffin.protocol.commands.lobby.career.CheckDisplayExistCommand;
import bluffinmuffin.protocol.commands.lobby.career.CheckUserExistCommand;
import bluffinmuffin.protocol.commands.lobby.career.CreateCareerTableCommand;
import bluffinmuffin.protocol.commands.lobby.career.CreateUserCommand;
import bluffinmuffin.protocol.commands.lobby.career.GetUserCommand;
import bluffinmuffin.protocol.commands.lobby.training.CreateTrainingTableCommand;
import bluffinmuffin.protocol.commands.lobby.training.IdentifyCommand;
import bluffinmuffin.protocol.observer.ICommandListener;

public interface ILobbyServerListener extends ICommandListener
{
    void disconnectCommandReceived(DisconnectCommand command);
    
    void joinTableCommandReceived(JoinTableCommand command);
    
    void listTableCommandReceived(ListTableCommand command);
    
    void gameCommandReceived(GameCommand command);
    
    // Training
    void createTrainingTableCommandReceived(CreateTrainingTableCommand command);
    
    void identifyCommandReceived(IdentifyCommand command);
    
    // Career
    void createCareerTableCommandReceived(CreateCareerTableCommand command);
    
    void createUserCommandReceived(CreateUserCommand command);
    
    void checkUserExistCommandReceived(CheckUserExistCommand command);
    
    void checkDisplayExistCommandReceived(CheckDisplayExistCommand command);
    
    void authenticateUserCommandReceived(AuthenticateUserCommand command);
    
    void getUserCommandReceived(GetUserCommand command);
    
}
