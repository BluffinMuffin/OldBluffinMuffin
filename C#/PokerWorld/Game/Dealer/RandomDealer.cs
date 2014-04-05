using System;
using System.Collections.Generic;
using System.Text;
using EricUtility.Games.CardGame;

namespace PokerWorld.Game.Dealer
{
    public class RandomDealer : AbstractDealer
    {
        public override GameCard[] DealHoles(PokerPlayer p)
        {
            GameCard[] set = new GameCard[2];
            set[0] = m_Deck.Pop();
            set[1] = m_Deck.Pop();
            return set;
        }

        public override GameCard[] DealFlop()
        {
            GameCard[] set = new GameCard[3];
            set[0] = m_Deck.Pop();
            set[1] = m_Deck.Pop();
            set[2] = m_Deck.Pop();
            return set;
        }

        public override GameCard DealTurn()
        {
            return m_Deck.Pop();
        }

        public override GameCard DealRiver()
        {
            return m_Deck.Pop();
        }

        public override void FreshDeck()
        {
            m_Deck = GameCardUtility.GetShuffledDeck(false);
        }
    }
}
