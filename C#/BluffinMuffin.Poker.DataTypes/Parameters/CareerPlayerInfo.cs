using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BluffinMuffin.Poker.DataTypes.Parameters
{
    public class CareerPlayerInfo : PlayerInfo
    {
        public UserInfo User { get; private set; }

        public CareerPlayerInfo(UserInfo user) : base(user.DisplayName, 0)
        {
            User = user;
        }
    }
}
