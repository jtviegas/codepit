package snippetlab.snippets.tests;

import java.util.LinkedList;

public final class Events
{
	private LinkedList<Event> list = new LinkedList<Event>();
	
	public Events()
	{
		
	}
	
	void pushEvent(Event event, java.util.Date date) 
	{
		
		if(0 == list.size())
			list.addFirst(event);
		else
		{
			int _idx = 0;
			for(Event _ev:list)
			{
				if(_ev.getDate().before(date))
				{
					list.add(_idx, event);
					break;
				}
				else
					if(_idx == (list.size()-1))
						list.add(event);
				
					_idx++;
			}
		}
		
	}
	
	Event[] getEvents() 
	{
		int _size=list.size();
		Event[] _events = new Event[_size];
		
		for(Event _event:list)
		{
			_events[--_size]=_event;
		}
		
		return _events;
	}
	
	Event getEvent(long date) 
	{
		Event _r = null;
		
		for(Event _ev:list)
		{
			if(date == _ev.getDate().getTime())
				return _ev;
		}
		
		return _r;
	}

}
