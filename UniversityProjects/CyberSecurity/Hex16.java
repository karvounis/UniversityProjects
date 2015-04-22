
/**
 * Converts a 16 bit 4-hex-digit string to an int.
 * @author ron
 *
 */
public class Hex16 
{
	public	static	int	convert(String s)
	{
		int	i0 = hex2int(s.charAt(2));
		int	i1 = hex2int(s.charAt(3));
		int	i2 = hex2int(s.charAt(4));
		int	i3 = hex2int(s.charAt(5));
		return i3 + 16 * (i2 + 16 * (i1 + 16 * i0));
	}
	
	private	static	int	hex2int(char c)
	{
		if (c >= '0' && c <= '9')
			return (int)(c - '0');
		else if (c >= 'a' && c <= 'f')
			return (int) (c - 'a') + 10;
		else if (c >= 'A' && c <= 'F')
			return (int) (c - 'A') + 10;
		else
			return 0;
	}
}
