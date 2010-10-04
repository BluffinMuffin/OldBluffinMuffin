﻿using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class CreateUserResponse : AbstractLobbyResponse<CreateUserCommand>
    {

        protected override string CommandName
        {
            get { return COMMAND_NAME; }
        }
        public static string COMMAND_NAME = "lobbyCAREER_CREATE_USER_RESPONSE";
        private readonly bool m_Success;
        public bool Success
        {
            get { return m_Success; }
        } 


        public CreateUserResponse(StringTokenizer argsToken)
            : base(new CreateUserCommand(argsToken))
        {
            m_Success = bool.Parse(argsToken.NextToken());
        }

        public CreateUserResponse(CreateUserCommand command, bool success)
            : base(command)
        {
            m_Success = success;
        }

        public override void Encode(StringBuilder sb)
        {
            base.Encode(sb);
            Append(sb, m_Success);
        }
    }
}