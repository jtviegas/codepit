package org.aprestos.labs.snippets.impl.java8.streams;

import java.util.Arrays;
import java.util.List;

public class Simple {

    private List<String> d = Arrays.asList("2", "9", "3");
    
    public Simple() {
	System.out.println( d.stream().filter(i -> (i.compareTo("5") < 0) ).count());
	System.out.println( d.stream().filter(i -> (i.compareTo("5") < 0) ).toString());
    }

    
    public static void main(String[] args){
	new Simple();
    }

}
