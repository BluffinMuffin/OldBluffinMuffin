using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EricUtility.Games.CardGame;

namespace PokerWorld.Game
{
    public abstract class AbstractDealer
    {
        protected Stack<GameCard> m_Deck;
       
        public AbstractDealer()
        {
            FreshDeck();
        }

        public abstract GameCard[] DealHoles(PlayerInfo p);
        public abstract GameCard[] DealFlop();
        public abstract GameCard DealTurn();
        public abstract GameCard DealRiver();

        public abstract void FreshDeck();
    }
}
