package com.tgedr.labs.commons;

public class Assorted {


    public int[] xor1( int[] a, int[] b){
	int[] result = null;
	
	
	int iterationSize = Math.min(a.length, b.length);
	int roughResultSize = a.length + b.length;

	int resultSize = 0;
	int[] roughResult = new int[roughResultSize];
	
	int i = 0, k = 0;
	while(true){
	    
	    while(a[i] < b[k] && i < iterationSize){
		roughResult[resultSize++] = a[i++];
		if(a[i] == b[k]){
		    k++;
		    i++;
		    break;
		}
	    }
	    
	    if(i == iterationSize)
		break;
	    
	    while(b[k] < a[i] && k < iterationSize){
		roughResult[resultSize++] = b[k++];
		if(a[i] == b[k]){
		    i++;
		    k++;
		    break;
		}
	    }
	    
	    if(k == iterationSize)
		break;
	}
	
	
	while(i < a.length)
		roughResult[resultSize++] = a[i++];
	
	while(k < b.length)
		roughResult[resultSize++] = b[k++];
	    
	result = new int[resultSize];
	for(int j = 0; j < resultSize; j++)
	    result[j] = roughResult[j];
	
	
	return result;
    }
    
    public int[] xor2( int[] a, int[] b){
  	
	sort(a, 0, a.length-1);
	sort(b, 0, b.length - 1);

  	return xor1(a,b);
      }
    
    private void exchange(int[] items, int i, int j){
	int swap = items[i];
	items[i] = items[j];
	items[j] = swap;
    }

	<T> T[] add2Arr(T[] src, T n){
		int al = src.length;
		T[] r = (T[]) new Object[al+1];
		System.arraycopy(src, 0, r,0 , al);
		r[al] = n;
		return r;
	}

	int[] add2Arr(int[] src, int n){
		int al = src.length;
		int[] r = new int[al+1];
		System.arraycopy(src, 0, r,0 , al);
		r[al] = n;
		return r;
	}

	int[] removeFromArr(int[] src, int n){
		int al = src.length;
		int[] r = new int[src.length-1];
		int newIndex = 0;
		for(int i=0; i < src.length ;i++){
			if(i != n)
				r[newIndex++] = src[i];
		}
		return r;
	}

    private void sort(int[] items, int lo, int hi){
	
	
	if(hi <= lo) return;
	int v = items[lo];
	int i = lo, gt = hi, lt = lo;
	
	while(i <= gt){

	    if(items[i] < v)
		exchange(items, i++, lt++);
	    else if (items[i] > v)
		exchange(items, i, gt--);
	    else
		i++;

	}

	sort(items, lo, lt-1);
	sort(items, gt+1, hi);
    }

}

/*
 
 PART I
 
 Question 1
 LinkedHashMap
 
 Question 2
 private
 
 Question 3 
 see method xor1 above - complexity is 3n => O(n) = n 
 
 Question 4
 see method xor2 above = complexity is 2*n*log(n) + 3n => O(n) = n*log(n)
 
 Question 5
 Prefer always to load dependencies according to context, so would always
 try to use some kind of dependency injection or factory kind of solution
 that permit me to be as loosed coupled as possible, following the open-closed principle
 
 Question 6
 Nowadays mostly I do only unit testing as I am working in a kind of specific components
 that are mostly used by client code as libraries. Junit is the solution for this. 
 Also do assessment of analysis results implementing functionality in R and matching the outcomes with java.
 If eventually I am working in any kind of rest api, most i do after unit testing is integration testing,
 with maven and embedded application servers as websphere liberty profile and the maven failsafe plugin.

Question 7
version control systems permit us to keep track of every change in the code and to work in collaboration.
Currently I use Rational Team Concert (RTC) for version management, ticket and project management, so that every
step of the process is mapped to an item in the project management domain. That item might be a story for the
milestone, a task for the story, a defect associated with a branch, different structured items and granularity
fit in and are delivered in a changeset component and allow us to have traceability and manage the project in a safe way.
Also use git for my own open source and pet projects in the ususal worklflow of init, clone, add, commit, push, diff, branch and pull.

Question 8
I usually make an effort assessment of the tasks/defects/stories assigned to me, then project management
aligns that info with sprints/releases and then I work out on it based on its schedule. In my assessment I
try to figure out every requirement for the completion of the item in hand and to identify every risk involved. I 
share all that info with project management and we keep a close watch on the risks and times. Normally I worl on each 
item at a time, but I also am involved in another project with another team and I do allocate time for it when I need
to complete tasks for them.

Question 9
one way would be to compare characteristics of both variables, for instance:
- moving average could be a solution, would smooth data and provide some info on trend, would have to study
an analyze the best window size, we would need to keep some state to keep on going and evaluation on real time,
so probably we would have a separate process computing that info as a model to be evaluated against the real time data;
- deviation from the moving average would then provide some idea of variation of the variables; 

Question 10
in sequence (as in java pattern Strings):
"\\w(\\w)(\\w)"
"([^0-9]*)([0-9]*)[^0-9]([0-9]*)"
"([0-9]{4})([0-9]{4})([0-9]{4})"
...going to move on, similar the remaining ones
 
 
 PART II
 vertx - never used it, won't go there now.
 
 
 PART III
 ...new to the domain area I must confess.
 
 Question 1
  features: (  see figure 1 attached please, and sorry for the rough solution )
 - Ad Server: 
 	-registers publishers and advertisers as bidders, intermediates
 the placement of ads on the publishers media based on the bidding process;
 	- collects user experience such as user navigation, click and metadata, and
 collects advertiser data regarding user experience outcomes;
 	- calculates metrics and data to bill both Publisher and advertisers;
 - Publisher:
 	- provides media to be used as placeholder for ads;
 - Advertisers:
 	- register as bidders according to a bidding model;
 	- provide adds if bid winners;
 	- provide data of user  and commercial outcome to add server;
 
 Question 2
 (  see figure 2 attached please, and sorry for the rough solution )
 performance
 	- clearly an api micro services solution;
  	- Bidding engine is the critical one, would have to do this in a highly concurrent system, based on analysis evidence, 
  	if not java, probably using scala or Erlang. 
 	- Caches and store should be memory based, Redis could be an option;
 	- System should not permit latency, so no streaming bottlenecks allowed no
 	message brokers needed, unless we'd think that could be a solution for the
 	enrollment and bidding;
 	- central configuration, not in sketch, but services must be auto discoverable (Zookeeper cluster?);
  
 scaling
 	- we should scale horizontally per campaign * users;
 	- Bidding engine should be provided as a cluster of engines and cache/stores
 	could be sharded;
 	- Ad server will behave as load balancer or better, in tandem with one, and 
 	probably being a proxy;
 	
 Threats
 	security of course, we must be sure of every element in this context,
 	secured communications.
 	DOS & DDOS, we should have some solution for this threat.
 	High Availabilty, should replicate our services across the world;
 
 Data Model
 	...
 

*/