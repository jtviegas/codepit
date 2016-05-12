package org.aprestos.labs.snippets.impl.algo.sedgewick;

import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;

public class Generator {
    
    private static final int RANDOM_STR_LENGTH = 6;

    @SuppressWarnings("unchecked")
    public static <T> T[] generate(int n, Class<T> type){
	T[] r = null;
	if(type.equals(Integer.class))
	    r = (T[]) genInt(n);
	if(type.equals(String.class))
	    r = (T[]) genStr(n);
	if(type.equals(Double.class))
	    r = (T[]) genDbl(n);
	
	return r;
    }
    
    private static Integer[] genInt(int n){
	
	Integer[] r = new Integer[n];
	Random rand = new Random();
	for(int i=0; i < n; i++)
	    r[i] = rand.nextInt();
	return r;
	
    }
    
    @SuppressWarnings("static-access")
    private static String[] genStr(int n){

	String[] r = new String[n];
	RandomStringUtils rand = new RandomStringUtils();
	for(int i=0; i < n; i++)
	    r[i] = rand.randomAlphanumeric(RANDOM_STR_LENGTH);
	return r;
    }
    
    private static Double[] genDbl(int n){
	
	Double[] r = new Double[n];
   	Random rand = new Random();
   	for(int i=0; i < n; i++)
   	    r[i] = rand.nextDouble();
   	return r;
   	
       }
    
    
    
}
