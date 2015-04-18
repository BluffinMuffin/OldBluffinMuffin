using BluffinMuffin.Protocol.Commands.Lobby.Career;
using BluffinMuffin.Protocol.Commands.Test.Helpers;
using BluffinMuffin.Protocol.Commands.Test.Mocking;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BluffinMuffin.Protocol.Commands.Test
{
    [TestClass]
    public class LobbyCareerCommandsDecodeTest
    {

        [TestMethod]
        public void AuthenticateUserCommand()
        {
            var c = CommandMock.AuthenticateUserCommand();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            CompareAuthenticateUserCommand(c, dc);
        }

        [TestMethod]
        public void AuthenticateUserResponse()
        {
            var c = CommandMock.AuthenticateUserResponse();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            Assert.AreEqual(c.Success, dc.Success);
            CompareAuthenticateUserCommand(c.Command, dc.Command);
        }
        [TestMethod]
        public void CheckDisplayExistCommand()
        {
            var c = CommandMock.CheckDisplayExistCommand();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            CompareCheckDisplayExistCommand(c, dc);
        }

        [TestMethod]
        public void CheckDisplayExistResponse()
        {
            var c = CommandMock.CheckDisplayExistResponse();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            Assert.AreEqual(c.Exist, dc.Exist);
            CompareCheckDisplayExistCommand(c.Command, dc.Command);
        }
        [TestMethod]
        public void CheckUserExistCommand()
        {
            var c = CommandMock.CheckUserExistCommand();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            CompareCheckUserExistCommand(c, dc);
        }

        [TestMethod]
        public void CheckUserExistResponse()
        {
            var c = CommandMock.CheckUserExistResponse();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            Assert.AreEqual(c.Exist, dc.Exist);
            CompareCheckUserExistCommand(c.Command, dc.Command);
        }
        [TestMethod]
        public void CreateUserCommand()
        {
            var c = CommandMock.CreateUserCommand();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            CompareCreateUserCommand(c, dc);
        }

        [TestMethod]
        public void CreateUserResponse()
        {
            var c = CommandMock.CreateUserResponse();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            Assert.AreEqual(c.Success, dc.Success);
            CompareCreateUserCommand(c.Command, dc.Command);
        }
        [TestMethod]
        public void GetUserCommand()
        {
            var c = CommandMock.GetUserCommand();
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            CompareGetUserCommand(c, dc);
        }

        [TestMethod]
        public void GetUserResponse()
        {
            var c = CommandMock.GetUserResponse();
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
