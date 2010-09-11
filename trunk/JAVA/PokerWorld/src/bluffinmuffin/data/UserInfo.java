package bluffinmuffin.data;

public class UserInfo
{
    private String m_username;
    private String m_password;
    private String m_email;
    private String m_displayName;
    private double m_totalMoney;
    
    public UserInfo()
    {
    }
    
    public UserInfo(String username, String password, String email, String displayname, double totalmoney)
    {
        m_displayName = displayname;
        m_email = email;
        m_password = password;
        m_totalMoney = totalmoney;
        m_username = username;
    }
    
    public String getUsername()
    {
        return m_username;
    }
    
    public void setUsername(String username)
    {
        m_username = username;
    }
    
    public String getPassword()
    {
        return m_password;
    }
    
    public void setPassword(String password)
    {
        m_password = password;
    }
    
    public String getEmail()
    {
        return m_email;
    }
    
    public void setEmail(String email)
    {
        m_email = email;
    }
    
    public String getDisplayName()
    {
        return m_displayName;
    }
    
    public void setDisplayName(String displayName)
    {
        m_displayName = displayName;
    }
    
    public double getTotalMoney()
    {
        return m_totalMoney;
    }
    
    public void setTotalMoney(double totalMoney)
    {
        m_totalMoney = totalMoney;
    }
}
