﻿namespace BluffinMuffin.Protocol.Commands.Lobby.Career
{
    public class AuthenticateUserCommand : AbstractLobbyCommand
    {
        public string Username { get; set; }
        public string Password { get; set; }

        public string EncodeResponse(bool success)
        {
            return Response(success).Encode();
        }
        public AuthenticateUserResponse Response(bool success)
        {
            return new AuthenticateUserResponse(this) { Success = success };
        }
    }
}