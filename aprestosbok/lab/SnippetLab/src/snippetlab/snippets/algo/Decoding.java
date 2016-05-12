package snippetlab.snippets.algo;

import snippetlab.snippets.AbstractSnippet;

public class Decoding extends AbstractSnippet
{

	
	@Override
	public void method()
	{
		getBase2(35);
	}

	
	private static void getBase2(int _n)
	{
		
		int _number = _n;
		int _bit = 0;
		int[] _result = new int[0];
				
		while(_number > 0)
		{
			_result = expandArray(_result);//get space for bits
			_bit = _number % 2;
			_result[_result.length - 1]=_bit;
			_number = _number / 2;
		}
		
		System.out.println(_result);
	}
	
	private static int[] expandArray(int[] _a)
	{
		int[] _result = new int[_a.length + 1];
		
		for(int _i=0;_i<_a.length;_i++)
			_result[_i]=_a[_i];
		
		return _result;
	}


}
