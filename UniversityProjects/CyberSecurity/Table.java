
/**
 * Lookup table based on a HashMap
 * @author ron
 *
 */

import java.util.HashMap;
import java.util.Map;

public class Table 
{	
	private	Map	tab;
	
	public	Table()
	{
		tab = new HashMap();
	}
	
	public	void	add(int key, int data)
	{
		tab.put(new Integer(key), new Integer(data));
	}

	public	int	find(int key)	// return data or -1
	{
		Integer	keyobj = new Integer(key);
		if (tab.containsKey(keyobj))
		{
			Integer	io = (Integer) tab.get(keyobj);
			return io.intValue();
		}
		else
			return -1;
	}
}
