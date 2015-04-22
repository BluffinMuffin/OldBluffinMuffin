using System.Linq;
using BluffinMuffin.Protocol.Commands.Lobby;
using BluffinMuffin.Protocol.Commands.Test.Comparing;
using BluffinMuffin.Protocol.Commands.Test.Helpers;
using BluffinMuffin.Protocols.Test.DataTypes;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BluffinMuffin.Protocol.Commands.Test
{
    [TestClass]
    public class LobbyCommandsDecodeTest
    {

        [TestMethod]
        public void SupportedRulesCommand()
        {
            var c = LobbyCommandMock.SupportedRulesCommand();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            CompareSupportedRulesCommand(c, dc);
        }

        [TestMethod]
        public void SupportedRulesResponse()
        {
            var c = LobbyCommandMock.SupportedRulesResponse();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);

            Assert.AreEqual(c.Rules.Count, dc.Rules.Count);
            for (int i = 0; i < c.Rules.Count; ++i)
                CompareRuleInfo.Compare(c.Rules[i], dc.Rules[i]);
            CompareSupportedRulesCommand(c.Command, dc.Command);
        }

        [TestMethod]
        public void ListTableCommand()
        {
            var c = LobbyCommandMock.ListTableCommand();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            CompareListTableCommand(c, dc);
        }

        [TestMethod]
        public void ListTableResponse()
        {
            var c = LobbyCommandMock.ListTableResponse();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);

            Assert.AreEqual(c.Tables.Count, dc.Tables.Count);
            for (int i = 0; i < c.Tables.Count; ++i)
                CompareTupleTable.Compare(c.Tables[i], dc.Tables[i]);
            CompareListTableCommand(c.Command, dc.Command);
        }

        [TestMethod]
        public void JoinTableCommand()
        {
            var c = LobbyCommandMock.JoinTableCommand();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            CompareJoinTableCommand(c, dc);
        }

        [TestMethod]
        public void JoinTableResponse()
        {
            var c = LobbyCommandMock.JoinTableResponse();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);

            Assert.AreEqual(c.Success, dc.Success);
            CompareJoinTableCommand(c.Command, dc.Command);
        }

        [TestMethod]
        public void CreateTableCommand()
        {
            var c = LobbyCommandMock.CreateTableCommand();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            CompareCreateTableCommand(c, dc);
        }

        [TestMethod]
        public void CreateTableResponse()
        {
            var c = LobbyCommandMock.CreateTableResponse();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);

            Assert.AreEqual(c.IdTable, dc.IdTable);
            CompareCreateTableCommand(c.Command, dc.Command);
        }

        private static void CompareSupportedRulesCommand(SupportedRulesCommand c, SupportedRulesCommand dc)
        {
        }

        private static void CompareListTableCommand(ListTableCommand c, ListTableCommand dc)
        {
            Assert.IsFalse(c.LobbyTypes.Except(dc.LobbyTypes).Any());
            Assert.AreEqual(c.LobbyTypes.Length, dc.LobbyTypes.Length);
        }

        private static void CompareJoinTableCommand(JoinTableCommand c, JoinTableCommand dc)
        {
            Assert.AreEqual(c.TableId, dc.TableId);
            Assert.AreEqual(c.PlayerName, dc.PlayerName);
        }

        private static void CompareCreateTableCommand(CreateTableCommand c, CreateTableCommand dc)
        {
            CompareTableParams.Compare(c.Params, dc.Params);
        }
    }
}
