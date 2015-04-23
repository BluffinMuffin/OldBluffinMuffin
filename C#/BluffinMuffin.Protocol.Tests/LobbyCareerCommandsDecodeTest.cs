using BluffinMuffin.Protocol.Lobby.Career;
using BluffinMuffin.Protocol.Tests.Helpers;
using BluffinMuffin.Protocol.Tests.DataTypes;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BluffinMuffin.Protocol.Tests
{
    [TestClass]
    public class LobbyCareerCommandsDecodeTest
    {

        [TestMethod]
        public void AuthenticateUserCommand()
        {
            var c = LobbyCommandMock.AuthenticateUserCommand();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            CompareAuthenticateUserCommand(c, dc);
        }

        [TestMethod]
        public void AuthenticateUserResponse()
        {
            var c = LobbyCommandMock.AuthenticateUserResponse();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            Assert.AreEqual(c.Success, dc.Success);
            CompareAuthenticateUserCommand(c.Command, dc.Command);
        }
        [TestMethod]
        public void CheckDisplayExistCommand()
        {
            var c = LobbyCommandMock.CheckDisplayExistCommand();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            CompareCheckDisplayExistCommand(c, dc);
        }

        [TestMethod]
        public void CheckDisplayExistResponse()
        {
            var c = LobbyCommandMock.CheckDisplayExistResponse();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            Assert.AreEqual(c.Exist, dc.Exist);
            CompareCheckDisplayExistCommand(c.Command, dc.Command);
        }
        [TestMethod]
        public void CheckUserExistCommand()
        {
            var c = LobbyCommandMock.CheckUserExistCommand();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            CompareCheckUserExistCommand(c, dc);
        }

        [TestMethod]
        public void CheckUserExistResponse()
        {
            var c = LobbyCommandMock.CheckUserExistResponse();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            Assert.AreEqual(c.Exist, dc.Exist);
            CompareCheckUserExistCommand(c.Command, dc.Command);
        }
        [TestMethod]
        public void CreateUserCommand()
        {
            var c = LobbyCommandMock.CreateUserCommand();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            CompareCreateUserCommand(c, dc);
        }

        [TestMethod]
        public void CreateUserResponse()
        {
            var c = LobbyCommandMock.CreateUserResponse();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            Assert.AreEqual(c.Success, dc.Success);
            CompareCreateUserCommand(c.Command, dc.Command);
        }
        [TestMethod]
        public void GetUserCommand()
        {
            var c = LobbyCommandMock.GetUserCommand();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            CompareGetUserCommand(c, dc);
        }

        [TestMethod]
        public void GetUserResponse()
        {
            var c = LobbyCommandMock.GetUserResponse();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            Assert.AreEqual(c.Email, dc.Email);
            Assert.AreEqual(c.DisplayName, dc.DisplayName);
            Assert.AreEqual(c.Money, dc.Money);
            CompareGetUserCommand(c.Command, dc.Command);
        }
        private static void CompareAuthenticateUserCommand(AuthenticateUserCommand c, AuthenticateUserCommand dc)
        {
            Assert.AreEqual(c.Username, dc.Username);
            Assert.AreEqual(c.Password, dc.Password);
        }
        private static void CompareCheckDisplayExistCommand(CheckDisplayExistCommand c, CheckDisplayExistCommand dc)
        {
            Assert.AreEqual(c.DisplayName, dc.DisplayName);
        }

        private static void CompareCheckUserExistCommand(CheckUserExistCommand c, CheckUserExistCommand dc)
        {
            Assert.AreEqual(c.Username, dc.Username);
        }

        private static void CompareCreateUserCommand(CreateUserCommand c, CreateUserCommand dc)
        {
            Assert.AreEqual(c.Username, dc.Username);
            Assert.AreEqual(c.Password, dc.Password);
            Assert.AreEqual(c.Email, dc.Email);
            Assert.AreEqual(c.DisplayName, dc.DisplayName);
        }

        private void CompareGetUserCommand(GetUserCommand c, GetUserCommand dc)
        {
            Assert.AreEqual(c.Username, dc.Username);
        }
    }
}
