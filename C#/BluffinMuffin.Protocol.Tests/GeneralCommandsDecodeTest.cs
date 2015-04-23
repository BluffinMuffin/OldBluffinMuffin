using BluffinMuffin.Protocol.Tests.DataTypes;
using BluffinMuffin.Protocol.Tests.Helpers;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BluffinMuffin.Protocol.Tests
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
