using PokerWorld.Game.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PokerWorld.Game.Rules
{
    public class BlindInfo
    {
        public string Name { get; set; }
        public BlindEnum Type { get; set; }
        public bool HasConfigurableAmount { get; set; }
        public string ConfigurableAmountName { get; set; }
        public int ConfigurableDefaultValue { get; set; }

        public override string ToString()
        {
            return Name;
        }
    }
}
