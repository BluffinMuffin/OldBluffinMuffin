using System;
using System.Collections.Generic;
using System.Text;

namespace PokerWorld.Game
{
    /// <summary>
    /// 
    /// </summary>
    public class MoneyPot
    {
        /************ VARIABLES MEMBRES ************/
        private readonly int m_Id; // ID du pot
        private int m_Amount; // Quantite d'argent
        private readonly List<PlayerInfo> m_AttachedPlayers = new List<PlayerInfo>(); // Liste de joueurs attaches

        /************ PROPRIETES ************/
        /// <summary>
        /// ID du pot
        /// </summary>
        public int Id
        {
            get { return m_Id; }
        }

        /// <summary>
        /// Quantite d'argent
        /// </summary>
        public int Amount
        {
            get { return m_Amount; }
            set { m_Amount = value; }
        }

        /// <summary>
        /// Liste de joueurs attaches
        /// </summary>
        public PlayerInfo[] AttachedPlayers
        {
            get { return m_AttachedPlayers.ToArray(); }
        }

        /************ CONSTRUCTEURS ************/
        /// <summary>
        /// MoneyPot avec amount initial a 0
        /// </summary>
        /// <param name="id">ID du pot</param>
        public MoneyPot(int id)
            : this(id, 0)
        {
        }

        /// <summary>
        /// MoneyPot avec amount initial
        /// </summary>
        /// <param name="id">ID du pot</param>
        /// <param name="amount">Quantite d'argent</param>
        public MoneyPot(int id, int amount)
        {
            m_Id = id;
            m_Amount = amount;
        }

        /************ METHODES ************/
        /// <summary>
        /// Attache un joueur au POT
        /// </summary>
        /// <param name="p"></param>
        public void AttachPlayer(PlayerInfo p)
        {
            m_AttachedPlayers.Add(p);
        }

        /// <summary>
        /// Detache un joueur du POT
        /// </summary>
        /// <param name="p"></param>
        public void DetachPlayer(PlayerInfo p)
        {
            m_AttachedPlayers.Remove(p);
        }

        /// <summary>
        /// Detache tous les joueurs du POT
        /// </summary>
        public void DetachAllPlayers()
        {
            m_AttachedPlayers.Clear();
        }

        /// <summary>
        /// Ajoute un montant d'argent au POT
        /// </summary>
        /// <param name="added"></param>
        public void AddAmount(int added)
        {
            m_Amount += added;
        }
    }
}
