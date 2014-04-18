using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Com.Ericmas001.Game.Poker.DataTypes.Parameters
{
    public interface IOption<T>
    {
        T OptionType { get; }
    }
}
