using System.Linq;
using BluffinMuffin.Protocol.DataTypes.Enums;
using BluffinMuffin.Protocol.Lobby;
using BluffinMuffin.Protocol.Lobby.Career;
using BluffinMuffin.Protocol.Lobby.Training;

namespace BluffinMuffin.Protocol.Tests.DataTypes
{
    public static class LobbyCommandMock
    {
        public static SupportedRulesCommand SupportedRulesCommand()
        {
            return new SupportedRulesCommand();
        }

        public static SupportedRulesResponse SupportedRulesResponse()
        {
            return SupportedRulesCommand().Response(RuleInfoMock.GetAllRules());
        }

        public static ListTableCommand ListTableCommand()
        {
            return new ListTableCommand() { LobbyTypes = new[] { LobbyTypeEnum.Career, LobbyTypeEnum.Training } };
        }

        public static ListTableResponse ListTableResponse()
        {
            return ListTableCommand().Response(TupleTableMock.AllTables().ToList());
        }

        public static JoinTableCommand JoinTableCommand()
        {
            return new JoinTableCommand() {TableId = 42, PlayerName = "Table42"};
        }

        public static JoinTableResponse JoinTableResponse()
        {
            return JoinTableCommand().Response(true);
        }

        public static CreateTableCommand CreateTableCommand()
        {
            return new CreateTableCommand() {Params = TableParamsMock.ParamsOne()};
        }

        public static CreateTableResponse CreateTableResponse()
        {
            return CreateTableCommand().Response(42);
        }
        public static IdentifyCommand IdentifyCommand()
        {
            return new IdentifyCommand() { Name = "SpongeBob" };
        }

        public static IdentifyResponse IdentifyResponse()
        {
            return IdentifyCommand().Response(true);
        }
        public static AuthenticateUserCommand AuthenticateUserCommand()
        {
            return new AuthenticateUserCommand() { Username = "SpongeBob", Password = "SquarePants" };
        }

        public static AuthenticateUserResponse AuthenticateUserResponse()
        {
            return AuthenticateUserCommand().Response(true);
        }
        public static CheckDisplayExistCommand CheckDisplayExistCommand()
        {
            return new CheckDisplayExistCommand() { DisplayName = "SpongeBob" };
        }
        public static CheckDisplayExistResponse CheckDisplayExistResponse()
        {
            return CheckDisplayExistCommand().Response(true);
        }
        public static CheckUserExistCommand CheckUserExistCommand()
        {
            return new CheckUserExistCommand() { Username = "SpongeBob" };
        }
        public static CheckUserExistResponse CheckUserExistResponse()
        {
            return CheckUserExistCommand().Response(true);
        }
        public static CreateUserCommand CreateUserCommand()
        {
            return new CreateUserCommand() { Username = "SpongeBob", Password = "SquarePants", DisplayName = "Gary", Email = "Bikini@Bottom.com" };
        }
        public static CreateUserResponse CreateUserResponse()
        {
            return CreateUserCommand().Response(true);
        }
        public static GetUserCommand GetUserCommand()
        {
            return new GetUserCommand() { Username = "SpongeBob"};
        }
        public static GetUserResponse GetUserResponse()
        {
            return GetUserCommand().Response("Bikini@Bottom.com", "SquarePants", 42);
        }
    }
}
