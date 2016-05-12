package snippetlab.snippets.tests;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import snippetlab.snippets.AbstractSnippet;
//snippetlab.snippets.tests.TestsOne
public class TestsOne extends AbstractSnippet
{

	public TestsOne()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void method()
	{
		Scanner scanner = new Scanner(System.in);
		System.out.print("[Factorial Computation]: please give me a number : ");
        String input = scanner.nextLine();
        try
		{
			int number = Integer.parseInt(input);
			int result = factorial(number);
			System.out.print("[Factorial Computation]: " + result);
		} 
        catch (NumberFormatException nfe)
		{
			System.out.print("!!! You should provide an integer number !!! ");
		}
        
        GregorianCalendar _cal = new GregorianCalendar(1995, Calendar.DECEMBER,21);
        
        System.out.print(_cal.getTime());
        
	}
	
	
	
	private int factorial(int n)
	{
		int _r = 1;
		boolean _m3 = false;
		boolean _m5 = false;
		
		while (n>0)
		{
			_r*=n;
			
			if(0 == (_r%3))
				_m3=true;
			
			if(0 == (_r%5))
				_m5=true;
			
			if(_m3 && _m5)
				System.out.println("pástrás");
			else if(_m3)
					System.out.println("pás");
			else if(_m5)
				System.out.println("trás");
			
			_m3=false;
			_m5=false;
				
			n--;
		}
		
		return _r;
	}
	
	
	public int mostOften2 ( int[] A ) 
	{
		int _o = 0;
		int _max = -1;
		int _count = 0;
		int _cur = -1;
		
		quickSort(A, 0, A.length-1);
		for(int _i = 0; _i < A.length ; _i++)
		{
			if(_i != _cur)
			{
				if(_count > _max)
				{
					_max=_count;
					_o=_cur;
				}
				_cur = _i;
				_count = 0;
			}
			else
				_count++;
		}
		
		return _o;
	}
	
	public int mostOften ( int[] A ) 
	{
		int _mo = 0;
		int _cur = 0;
		int _r = 0;
		
		//not really specified but...
		if(A.length == 0)
			return -1;
		
		for(int _i : A)
		{
			_cur=howMuch(A, _i);
			if(_mo < _cur)
			{
				_mo=_cur;
				_r = _i;
			}
		}
		
		return _r;
	}
	
	private int howMuch(int[] A, int _o)
	{
		int _c = 0;
		for(int _i : A)
			if(_i == _o)
				_c++;
		
		return _c;
	}
	
	
	
	
	
	
	private void second()
	{
		int[] _a = new int[]{5,4,0,3,1,6,2};
		System.out.println(max_size(_a));
		_a = new int[]{5,4,8,3,1,6,2};
		System.out.println(max_size(_a));
		_a = new int[]{};
		System.out.println(max_size(_a));
	}
	
	public int max_size ( int[] A ) 
	{
		int _max = 0;
		int _current = 0;
		for(int _i=0 ; _i < A.length ; _i++)
		{	
			_current=drill(A, _i, new int[0]);
			if(_max < _current)
				_max=_current;
		}
		return _max;	
	}
	
	private int drill(int[] A, int _i, int[] _subset)
	{
		if(_i >= A.length || contains(_subset, A[_i]))
			return _subset.length;
		else
			return drill(A, A[_i],add2Array(_subset, A[_i]));
	}
	
	private int[] add2Array(int[] A, int _o)
	{
		int[] _r = new int[A.length+1];
		for(int _i=0; _i < A.length ; _i++)
			_r[_i]=A[_i];
		
		_r[A.length]=_o;
		
		return _r;
	}
	
	private boolean contains(int[] A, int _o)
	{
		boolean _result = false;
		for(int _i : A)
		{
			if(_i == _o)
				return true;
		}
			
		return _result;	
	}
	
	
	private void first()
	{
		int[] _a = new int[]{2,2,2,2,2,2};
		System.out.println(median(_a));
		_a = new int[]{1,2,5,10,20,1};
		System.out.println(median(_a));
		_a = new int[]{1,2,5,10,20,1,3,5,4};
		System.out.println(median(_a));
	}
	
	public int median ( int[] A ) 
	{
		quickSort(A, 0, A.length -1);
		
		return(A[A.length/2]);
	}  
	
	private void quickSort(int[] _array,int _l,int _r)
	{
		if(_r > _l)
		{
			int _pivot = partition(_array,_l,_r);
			quickSort(_array,_l,_pivot-1);
			quickSort(_array,_pivot+1,_r);
		}
		
	}
	
	private int partition(int[] _array,int _l, int _r)
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
	
	private void swap(int[] _a, int _i , int _j)
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
