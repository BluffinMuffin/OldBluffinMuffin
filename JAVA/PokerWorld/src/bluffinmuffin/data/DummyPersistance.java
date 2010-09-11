package bluffinmuffin.data;

import java.util.HashMap;
import java.util.Map;

public class DummyPersistance implements IDataPersistance
{
    private final Map<String, UserInfo> usersByUsername = new HashMap<String, UserInfo>();
    private final Map<String, UserInfo> usersByDisplayname = new HashMap<String, UserInfo>();
    
    @Override
    public boolean isUsernameExist(String username)
    {
        return usersByUsername.containsKey(username.toLowerCase());
    }
    
    @Override
    public boolean isDisplayNameExist(String displayName)
    {
        return usersByDisplayname.containsKey(displayName.toLowerCase());
    }
    
    @Override
    public void register(UserInfo u)
    {
        usersByUsername.put(u.getUsername().toLowerCase(), u);
        usersByDisplayname.put(u.getDisplayName().toLowerCase(), u);
    }
    
    @Override
    public UserInfo get(String username)
    {
        if (!isUsernameExist(username))
        {
            return null;
        }
        return usersByUsername.get(username.toLowerCase());
    }
    
    @Override
    public UserInfo authenticate(String username, String password)
    {
        final UserInfo u = get(username);
        if (u != null && u.getPassword().equals(password))
        {
            return u;
        }
        return null;
    }
    
    @Override
    public void update(UserInfo u)
    {
        usersByUsername.put(u.getUsername().toLowerCase(), u);
        usersByDisplayname.put(u.getDisplayName().toLowerCase(), u);
    }
    
    @Override
    public void delete(UserInfo u)
    {
        usersByUsername.remove(u.getUsername().toLowerCase());
        usersByDisplayname.remove(u.getDisplayName().toLowerCase());
    }
    
}
