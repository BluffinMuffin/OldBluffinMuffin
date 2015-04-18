﻿namespace BluffinMuffin.Protocol.Commands.Lobby.Career
{
    public class CheckUserExistCommand : AbstractLobbyCommand
    {
        public string Username { get; set; }

        public string EncodeResponse(bool exist)
        {
            return Response(exist).Encode();
        }
        public CheckUserExistResponse Response(bool exist)
        {
            return new CheckUserExistResponse(this) { Exist = exist };
        }
    }
}
