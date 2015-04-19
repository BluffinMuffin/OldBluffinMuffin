using BluffinMuffin.Protocol.Commands.Test.Helpers;
using BluffinMuffin.Protocols.Test.DataTypes;
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
