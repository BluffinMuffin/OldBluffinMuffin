using System;
using System.Collections.Generic;
using System.Text;
using Com.Ericmas001.Games;

namespace Com.Ericmas001.Game.Poker.DataTypes
{
    public abstract class AbstractDealer
    {
        protected Stack<GameCard> m_Deck;
       
        public AbstractDealer()
        {
            FreshDeck();
        }

        public abstract GameCard[] DealHoles();
        public abstract GameCard[] DealFlop();
        public abstract GameCard DealTurn();
        public abstract GameCard DealRiver();

        public abstract void FreshDeck();
    }
}
