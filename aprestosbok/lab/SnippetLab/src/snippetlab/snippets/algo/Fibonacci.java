package snippetlab.snippets.algo;

import snippetlab.snippets.AbstractSnippet;

public class Fibonacci extends AbstractSnippet
{

	private static final int FIBONACCI_UPPER_LIMIT=30;
	
	@Override
	public void method()
	{
		
		fibonacciExperiment();
		
	}
	

	private static void fibonacciExperiment()
	{
		System.out.println("goint to experiment Fibonacci until " + FIBONACCI_UPPER_LIMIT );
		
		for(int _i=0;_i<FIBONACCI_UPPER_LIMIT;_i++)
		{
			System.out.println(getFibonacci(_i));
		}
		
		System.out.println("with terminal recursion:" + getTerminalRecursionFibonacci(FIBONACCI_UPPER_LIMIT));
	
	}
	
	private static int getFibonacci(int _n)
	{
		int _result = 0;
		
		if(_n <= 1)
			_result=1;
		else
			_result=getFibonacci(_n-1) + getFibonacci(_n-2); 
		
		return _result;
	}
	
	
	private static int getFibonacciInstance(int _upperBound, int _index, int _lastResult, int _beforeLastResult)
	{
		if(_upperBound == _index)
			return _lastResult;
		else 	//escalate the calculations to the upper bound
			return getFibonacciInstance(_upperBound, _index + 1, _lastResult + _beforeLastResult, _lastResult);
	}
	
	
	//one based
	private static int getTerminalRecursionFibonacci(int _n)
	{
		if(_n <= 1)
			return 1;
		else
			return  getFibonacciInstance(_n,2,1,1);
	}
	
	
}
