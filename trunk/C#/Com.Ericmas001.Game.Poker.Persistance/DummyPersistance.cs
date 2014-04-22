using Com.Ericmas001.Game.Poker.DataTypes;
using System.Collections.Generic;

namespace Com.Ericmas001.Game.Poker.Persistance
{
    public class DummyPersistance : IDataPersistance
    {
        private readonly Dictionary<string, UserInfo> m_UsersByUsername = new Dictionary<string, UserInfo>();
        private readonly Dictionary<string, UserInfo> m_UsersByDisplayname = new Dictionary<string, UserInfo>();

        #region IDataPersistance Members

        public bool IsUsernameExist(string username)
        {
            return m_UsersByUsername.ContainsKey(username.ToLower());
        }

        public bool IsDisplayNameExist(string displayName)
        {
            return m_UsersByDisplayname.ContainsKey(displayName.ToLower());
        }

        public void Register(UserInfo u)
        {
            m_UsersByUsername.Add(u.Username.ToLower(), u);
            m_UsersByDisplayname.Add(u.DisplayName.ToLower(), u);
        }

        public UserInfo Get(string username)
        {
            if (!IsUsernameExist(username))
                return null;
            return m_UsersByUsername[username.ToLower()];
        }

        public UserInfo Authenticate(string username, string password)
        {
            var u = Get(username);
            if (u != null && u.Password == password)
                return u;
            return null;
        }

        public void Update(UserInfo u)
        {
            m_UsersByUsername[u.Username.ToLower()] = u;
            m_UsersByDisplayname[u.DisplayName.ToLower()] = u;
        }

        public void Delete(UserInfo u)
        {
            m_UsersByUsername.Remove(u.Username.ToLower());
            m_UsersByDisplayname.Remove(u.DisplayName.ToLower());
        }

        #endregion
    }
}
