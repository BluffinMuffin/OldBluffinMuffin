using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.DataTypes.Enums;
using BluffinMuffin.Poker.DataTypes.Parameters;

namespace BluffinMuffin.Protocols.Test.DataTypes
{
    public static class TupleTableMock
    {
        public static TupleTable[] AllTables()
        {
            return new TupleTable[]
            {
                TableOne(),
                TableTwo(),
                TableThree()
            };
        }
        public static TupleTable TableOne()
        {
            return new TupleTable()
            {
                BigBlind = 10,
                IdTable = 42,
                NbPlayers = 3,
                PossibleAction = LobbyActionEnum.Join,
                Params = TableParamsMock.ParamsOne()
            };
        }
        public static TupleTable TableTwo()
        {
            return new TupleTable()
                {
                    BigBlind = 100,
                    IdTable = 420,
                    NbPlayers = 7,
                    PossibleAction = LobbyActionEnum.Leave,
                    Params = TableParamsMock.ParamsTwo()
                };
        }
        public static TupleTable TableThree()
        {
            return new TupleTable()
            {
                BigBlind = 1000,
                IdTable = 4200,
                NbPlayers = 6,
                PossibleAction = LobbyActionEnum.None,
                Params = TableParamsMock.ParamsThree()
            };
        }
    }
}
