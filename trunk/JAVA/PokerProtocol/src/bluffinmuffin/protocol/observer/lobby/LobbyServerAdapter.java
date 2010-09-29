package bluffinmuffin.protocol.observer.lobby;

import bluffinmuffin.protocol.commands2.DisconnectCommand;
import bluffinmuffin.protocol.commands2.lobby.GameCommand;
import bluffinmuffin.protocol.commands2.lobby.JoinTableCommand;
import bluffinmuffin.protocol.commands2.lobby.ListTableCommand;
import bluffinmuffin.protocol.commands2.lobby.career.AuthenticateUserCommand;
import bluffinmuffin.protocol.commands2.lobby.career.CheckDisplayExistCommand;
import bluffinmuffin.protocol.commands2.lobby.career.CheckUserExistCommand;
import bluffinmuffin.protocol.commands2.lobby.career.CreateCareerTableCommand;
import bluffinmuffin.protocol.commands2.lobby.career.CreateUserCommand;
import bluffinmuffin.protocol.commands2.lobby.career.GetUserCommand;
import bluffinmuffin.protocol.commands2.lobby.training.CreateTrainingTableCommand;
import bluffinmuffin.protocol.commands2.lobby.training.IdentifyCommand;

public abstract class LobbyServerAdapter implements ILobbyServerListener
{
    
    @Override
    public void commandReceived(String command)
    {
    }
    
    @Override
    public void identifyCommandReceived(IdentifyCommand command)
    {
    }
    
    @Override
    public void disconnectCommandReceived(DisconnectCommand command)
    {
    }
    
    @Override
    public void joinTableCommandReceived(JoinTableCommand command)
    {
    }
    
    @Override
    public void listTableCommandReceived(ListTableCommand command)
    {
    }
    
    @Override
    public void gameCommandReceived(GameCommand command)
    {
    }
    
    @Override
    public void createUserCommandReceived(CreateUserCommand command)
    {
    }
    
    @Override
    public void checkUserExistCommandReceived(CheckUserExistCommand command)
    {
    }
    
    @Override
    public void checkDisplayExistCommandReceived(CheckDisplayExistCommand command)
    {
    }
    
    @Override
    public void authenticateUserCommandReceived(AuthenticateUserCommand command)
    {
    }
    
    @Override
    public void getUserCommandReceived(GetUserCommand command)
    {
    }
    
    @Override
    public void createTrainingTableCommandReceived(CreateTrainingTableCommand command)
    {
    }
    
    @Override
    public void createCareerTableCommandReceived(CreateCareerTableCommand command)
    {
    }
}
