package com.tgedr.labs.exercises;

import java.util.Stack;

public class Exercise {

    
    
    
    int nofP(int[] a, long k) {
	int result = 0;
	
	boolean[] used = new boolean[a.length];
	
	for(int i = 0; i < a.length - 1; i++){
	    
	    for(int j = a.length-1 ; j > i ; j--){
		if(i != j && a[i] + a[j] == k){
		    if(!used[i] && !used[j]){
			result++;
			    used[i] = true;
			    used[j] = true;
		    }
		    
		}
	    }
	    
	}
	
	
	return result;
    }
    
    int nofP2(int[] a, long k) {
	int result = 0;
	
	for(int i = 0; i < a.length - 1; i++){
	    
	    for(int j = i+1 ; j < a.length ; j++){
		if(i != j && a[i] + a[j] == k){

			result++;

		    
		}
	    }
	    
	}
	
	
	return result;
    }
    
    
    public String doit(int n) {
	int i = 0;
	StringBuffer b = new StringBuffer();
	while (++i <= n) {

	    for (int j = 0; j < (n - i); j++)
		b.append(" ");
	    for (int j = 0; j < i; j++)
		b.append("#");
	    if(i < (n))
	    b.append(System.lineSeparator());
	}
	System.out.println(b.toString());
	return b.toString();
    }
    
    public String[] doit2(String[] input) {
	
	String[] result = new String[input.length];
	
	int i = 0;
	for(String s: input){
	    result[i++] = isBalanced(s) ? "YES" : "NO";
	}
	
	
	return result;
    }
    
    private char getClosingBrace(char c){
	char result = 0;
	switch(c){
        	case '{':
        	    result = '}';
        	    break;
        	case '[':
        	    result = ']';
        	    break;  
        	case '(':
        	    result = ')';
        	    break; 
	}
	return result;
    }
    
    public boolean isBalanced(String input) {
	
	Stack<Character> s = new Stack<Character>();
	char[] chars = input.toCharArray();

	for(char c: chars){
	    
	    if(s.empty())
		s.push(c);
	    else {
		if( getClosingBrace(s.peek()) == c )
			s.pop();
		    else
			s.push(c);
	    }
	    
	}
	
	
	return s.size() == 0;
    }

}
