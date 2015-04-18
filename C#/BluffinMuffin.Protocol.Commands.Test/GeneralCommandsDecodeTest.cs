using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.DataTypes.Enums;
using BluffinMuffin.Poker.DataTypes.Parameters;
using BluffinMuffin.Poker.Logic;
using BluffinMuffin.Protocol.Commands.Lobby;
using BluffinMuffin.Protocol.Commands.Test.Comparing;
using BluffinMuffin.Protocol.Commands.Test.Helpers;
using BluffinMuffin.Protocol.Commands.Test.Mocking;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BluffinMuffin.Protocol.Commands.Test
{
    [TestClass]
    public class GeneralCommandsDecodeTest
    {

        [TestMethod]
        public void DisconnectCommand()
        {
            var c = GeneralCommandMock.DisconnectCommand();
            var dc =EncodeDecodeHelper.GetDecodedCommand(c);
        }
    }
}
