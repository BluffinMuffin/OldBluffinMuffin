package bluffinmuffin.data;

public interface IDataPersistance
{
    boolean isUsernameExist(String username);
    
    boolean isDisplayNameExist(String displayName);
    
    void register(UserInfo u);
    
    UserInfo get(String username);
    
    UserInfo authenticate(String username, String password);
    
    void update(UserInfo u);
    
    void delete(UserInfo u);
}
