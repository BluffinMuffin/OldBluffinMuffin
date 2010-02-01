package backend.agent;

import utility.ClosingListener;
import backend.Table;

public interface IPokerAgent extends Runnable
{
    public void addClosingListener(ClosingListener<IPokerAgent> p_listener);
    
    public void removeClosingListener(ClosingListener<IPokerAgent> p_listener);
    
    public void setTable(Table p_table);
    
    public void start();
    
    public void stop();
}
