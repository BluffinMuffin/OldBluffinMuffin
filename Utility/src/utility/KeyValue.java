package utility;

public class KeyValue <K,V>
{
	private K m_key;
	private V m_value;
	public KeyValue(K key, V value) 
	{
		m_key = key;
		m_value = value;
	}
	public K getKey() 
	{
		return m_key;
	}
	public void setKey(K key) 
	{
		m_key = key;
	}
	public V getValue() 
	{
		return m_value;
	}
	public void setValue(V value) 
	{
		m_value = value;
	}
}
