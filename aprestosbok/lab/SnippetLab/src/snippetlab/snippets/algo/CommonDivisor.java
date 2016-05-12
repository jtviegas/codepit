package snippetlab.snippets.algo;

import java.util.Random;

import snippetlab.snippets.AbstractSnippet;

public class CommonDivisor extends AbstractSnippet
{
	private static int NUM_OF_ITERATIONS=12, MAX_NUM=100;
	
	@Override
	public void method()
	{
		doGCDExperiment();
	}
	

	private static void doGCDExperiment()
	{
		Random _r = new Random();
		int _index = 0, _n1=0 , _n2=0;
		
		while(_index++ < NUM_OF_ITERATIONS)
		{
			_n1=_r.nextInt(MAX_NUM);
			_n2=_r.nextInt(MAX_NUM);
			
			if (0 == _n1) ++_n1;
			
			if (0 == _n2) ++_n2;
			
			System.out.println(String.format("random got us %d and %d", _n1,_n2));
			System.out.println(String.format("gcd(%d,%d)=%d", _n1,_n2, getGCD(_n1, _n2)));
		}
		
		
	}
	
	private static int getGCD(int _n1, int _n2)
	{
		
		if(0 == _n1 % _n2)
			return _n2;
		else
			return getGCD(_n2, _n1 % _n2);
		
	}


}
