using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PokerWorld.Game.Rules
{
    public class LimitInfo
    {
        public string Name { get; set; }
        public LimitTypeEnum Type { get; set; }

        public override string ToString()
        {
            return Name;
        }
    }
}
