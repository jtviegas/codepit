package snippetlab.snippets.algo;

import snippetlab.snippets.AbstractSnippet;

public class CheckPalindrome extends AbstractSnippet
{
	private static String[] TESTS = 
			new String[]{
		"wasitaratisaw",
		"radar",
		"ladjklfjerlwfhkfsjldhnfkl"
	};
	
	@Override
	public void method()
	{
		doExperiment();
	}
	

	private static void doExperiment()
	{
		for(String _s:TESTS)
		{
			System.out.println(String.format("is \"%s\" a palindrome? => %b", _s, checkPalindrome(_s,0, _s.length()-1)));
		}
	}
	
	private static boolean checkPalindrome(String _s,int _l, int _r)
	{
		if(_r-_l >= 0)
			if(_r == _l)
				return true;
			else
				while(_s.charAt(_l) == _s.charAt(_r))
					return checkPalindrome(_s, _l+1, _r-1);
		
		return false;
	}


}
