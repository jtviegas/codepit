package snippetlab.snippets.assorted;
//snippetlab.snippets.assorted.CodeTestOcado
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import snippetlab.snippets.AbstractSnippet;

public class CodeTestOcado extends AbstractSnippet
{
	
	@Override
	public void method()
	{
		method2();
	}
	
	private void method0()
	{
		System.out.println("method0@CodeTestOcado");
		int[] A = new int[6];
		
		A[0] = 2;
		A[1] = 1;
		A[2] = 1;
		A[3] = 2;
		A[4] = 3;
		A[5] = 1;
		
		System.out.println("distinct(A)->"  + distinct(A));
	}
	
		public int distinct ( int[] A ) 
	  {
	    int _result=0;  
	    List<Integer> _l = new ArrayList<Integer>();
	    
	    for(int _i:A)
	    {
	    	if(!_l.contains(_i))
	    		_l.add(_i);
	    }
	    _result=_l.size();
	    
	    return _result;
	  }
		
		private void method1()
		{
			System.out.println("method1@CodeTestOcado");
			int[] A = new int[9];
			
			A[0] = 6;
			A[1] = 6;
			A[2] = 6;
			A[3] = 6;
			A[4] = 6;
			A[5] = 4;
			A[6] = 4;
			A[7] = 4;
			A[8] = 3;
			
			
			
			System.out.println("dominator(A)->"  + dominator(A));
		}
		public int dominator(int[] A ) 
		{
			int _result=-1; 
			
			Map<Integer,Integer> _freq_map = new HashMap<Integer,Integer>();
			int _freq_rule = (A.length/2)+1;
			int _index = 0;
			for(int _i:A)
			{
				int _freq = 0;

				if(_freq_map.containsKey(_i))
					_freq = _freq_map.get(_i);
				
				_freq_map.put(_i, ++_freq);
				
				if(_freq >= _freq_rule)
					_result = _index;
				
				_index++;	
			}
		
			
			return _result;
		}
		
		private void method2()
		{
			System.out.println("method1@CodeTestOcado");
			int[] A = new int[17];
			
			A[0] = 6;
			A[1] = 3;
			A[2] = 6;
			A[3] = 2;
			A[4] = 6;
			A[5] = 4;
			A[6] =2;
			A[7] = 4;
			A[8] = 6;
			A[9] = 3;
			A[10] = 6;
			A[11] = 2;
			A[12] = 6;
			A[13] = 4;
			A[14] =-5;
			A[15] = 4;
			A[16] = 6;
			
			
			
			System.out.println("deepest_pit(A)->"  + deepest_pit(A));
		}
		
		public int deepest_pit ( int[] A ) 
		{
			int _result=0;
			
			List<Integer> _infl = getInflections(A);
			
			_result= getDeepestPit(getPits(_infl));
			
			
			return _result;
		}
		
		private List<Integer> getInflections(int[] A)
		{
			List<Integer> _result = new ArrayList<Integer>();
			
			int _prev=0;
			
			boolean _descending = true, _ascending = true;
			_result.add(A[0]);
			_prev=A[0];
			
			for(int _i=1 ; _i<A.length; _i++)
			{
				if(A[_i] < _prev)
				{
					if(!_descending)
						_result.add(_prev);
					
					_descending=true;_ascending=false;
						
				}
				else if(A[_i] > _prev)
				{
					if(!_ascending)
						_result.add(_prev);
					
					_descending=false;_ascending=true;
				}
				else
				{
					_result.add(_prev);
					_descending = true;_ascending = true;
				}
				
				_prev=A[_i];
			}
			
			
			if(A[A.length-1] != _result.get(_result.size()-1))
				_result.add(A[A.length-1]);
			
			
			return _result;
		}
		
		
		private List<Integer> getPits(List<Integer> _p)
		{
			List<Integer> _result=new ArrayList<Integer>();
			
			int _index=0, _previous=0;
			
			for(Integer _i : _p)
			{
				if((_index != 0) && (_index < (_p.size())))
				{
					if((_p.get(_index-1) > _i) && (_p.get(_index+1) > _i))
					{
						//We have a pit
						_result.add(Math.min(Math.abs(_p.get(_index-1)-_i), Math.abs(_p.get(_index+1) - _i)));
					}
					
				}
				
				_previous = _i;
				_index++;
			}
			
			
			return _result;
		}
		
		private int getDeepestPit(List<Integer> _p)
		{
			int _max = 0;
			
			for(int _i:_p)
			{
				if(_i>_max)
					_max=_i;
			}
			
			return _max;
			
		}



}
