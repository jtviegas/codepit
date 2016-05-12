package snippetlab.snippets.algo;

import java.util.Random;

import snippetlab.snippets.AbstractSnippet;

public class SortAndSearch extends AbstractSnippet
{
	private static int[] ARRAY = new int[]{1,2,3,4,5,7,8,9,12,13,15,18,35,67,69,78,79,83,101};
	private static int[] KEY_INDEXES = new int[]{9,18,16,12,15};
	private static int ARRAY_LENGTH = 16;
	private static int MAX_INT = 160;
	
	@Override
	public void method()
	{
		doExperiment();
	}
	

	private static void doExperiment()
	{
		System.out.println("/* bisection search */");
		//bisection search only can be done in sorted arrays
		for(int _i:KEY_INDEXES)
			System.out.println(String.format("key array[%d]=%d was found at => %d index", _i, ARRAY[_i],bisectionSearch(ARRAY,0,ARRAY.length-1,ARRAY[_i])));
		
		
		System.out.println("/* Selection sort */");
		int[] _array = getRandomIntArray();
		System.out.println("array initial state:\n %s" + intArrayDisplay(_array));
		selectionSort(_array);
		System.out.println("array final state:\n %s" + intArrayDisplay(_array));
		
		System.out.println("/* quick sort */");
		_array = getRandomIntArray();
		System.out.println("array initial state:\n %s" + intArrayDisplay(_array));
		quickSort(_array,0,_array.length-1);
		System.out.println("array final state:\n %s" + intArrayDisplay(_array));
		
	}
	
	private static int[] getRandomIntArray()
	{
		int[] _result = new int[ARRAY_LENGTH];
		Random _r = new Random();
		
		for(int _i = 0 ; _i < ARRAY_LENGTH ; _i++)
		{
			_result[_i]=_r.nextInt(MAX_INT);
		}
		
		return _result;
	}
	
	private static int bisectionSearch(int[] _array,int _l, int _r, int _key)
	{
		if(_l > _r) return -1;
		
		int _pivot = _l + ((_r - _l)/2);
		
		if(_array[_pivot] == _key)
			return _pivot;
		else
			if(_array[_pivot] < _key)
				return bisectionSearch(_array, _pivot+1, _r, _key);
			else
				return bisectionSearch(_array, _l, _pivot -1, _key);
				
		 
	}
	
	//simple sorting algo
	private static void selectionSort(int[] _array)
	{
		for(int _i=0; _i < _array.length;_i++)
		{
			for(int _j=_i+1;_j<_array.length;_j++)
			{
				if(_array[_i] > _array[_j])
					swap(_array,_i,_j);
			}
		}
	}
	
	private static void quickSort(int[] _array,int _l,int _r)
	{
		if(_r > _l)
		{
			int _pivot = partition(_array,_l,_r);
			quickSort(_array,_l,_pivot-1);
			quickSort(_array,_pivot+1,_r);
		}
		
	}
	
	private static int partition(int[] _array,int _l, int _r)
	{
		//choose a pivot
		int _pivot = _l;
		int _i = _l + 1;
		
		while(_i <= _r)
		{
			if(_array[_l] > _array[_i])
			{
				swap(_array,_i,_pivot + 1);
				++_pivot;
			}
			_i++;
		}
		
		swap(_array,_l,_pivot);
		
		return _pivot;
	}
	
	private static void swap(int[] _a, int _i , int _j)
	{
		int _tmp=_a[_i];
		_a[_i] = _a[_j];
		_a[_j] = _tmp;
	}

	private static String intArrayDisplay(int[] _a)
	{
		StringBuffer _s = new StringBuffer("|");
		for(int _i:_a)
		{
			_s.append(_i + "|");
		}
		
		return _s.toString();
	}
	

}
