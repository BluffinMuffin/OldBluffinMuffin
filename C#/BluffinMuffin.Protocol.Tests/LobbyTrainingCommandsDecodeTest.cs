using BluffinMuffin.Protocol.Lobby.Training;
using BluffinMuffin.Protocol.Tests.Helpers;
using BluffinMuffin.Protocol.Tests.DataTypes;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BluffinMuffin.Protocol.Tests
{
    [TestClass]
    public class LobbyTrainingCommandsDecodeTest
    {

        [TestMethod]
        public void IdentifyCommand()
        {
            var c = LobbyCommandMock.IdentifyCommand();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            CompareIdentifyCommand(c, dc);
        }

        [TestMethod]
        public void IdentifyResponse()
        {
            var c = LobbyCommandMock.IdentifyResponse();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            Assert.AreEqual(c.Ok, dc.Ok);
            CompareIdentifyCommand(c.Command, dc.Command);
        }

        private static void CompareIdentifyCommand(IdentifyCommand c, IdentifyCommand dc)
        {
            Assert.AreEqual(c.Name, dc.Name);
        }
    }
}
