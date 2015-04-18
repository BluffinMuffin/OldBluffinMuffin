using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BluffinMuffin.Protocol.Commands.Test.Helpers
{
    public static class EncodeDecodeHelper
    {
        public static T GetDecodedCommand<T>(T c) where T : AbstractBluffinCommand
        {
            string ce = c.Encode();
            AbstractBluffinCommand dc = AbstractBluffinCommand.DeserializeCommand(ce);

            Assert.AreEqual(c.GetType(), dc.GetType(), "Command and Decoded Command should be the same");
            return (T)dc;
        }
    }
}
