using System;
using System.Collections.Generic;
using System.Text;

namespace Com.Ericmas001.Game.Poker.Persistance
{
    public static class DataManager
    {
        public static IDataPersistance Persistance = new DummyPersistance();
    }
}
