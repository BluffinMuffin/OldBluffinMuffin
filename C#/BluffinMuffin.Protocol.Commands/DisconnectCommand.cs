﻿using Com.Ericmas001.Net.Protocol.JSON;

namespace BluffinMuffin.Protocol.Commands
{
    public class DisconnectCommand : AbstractBluffinCommand
    {
        public override BluffinCommandEnum CommandType
        {
            get { return BluffinCommandEnum.General; }
        }
    }
}