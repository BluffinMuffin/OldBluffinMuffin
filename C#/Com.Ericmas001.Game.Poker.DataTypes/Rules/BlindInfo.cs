using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Com.Ericmas001.Game.Poker.DataTypes.Rules
{
    public class BlindInfo
    {
        public string Name { get; set; }
        public BlindTypeEnum Type { get; set; }
        public bool HasConfigurableAmount { get; set; }
        public string ConfigurableAmountName { get; set; }
        public int ConfigurableDefaultValue { get; set; }

        public override string ToString()
        {
            return Name;
        }
    }
}
