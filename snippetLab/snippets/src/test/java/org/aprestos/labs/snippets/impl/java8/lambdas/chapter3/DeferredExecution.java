package org.aprestos.labs.snippets.impl.java8.lambdas.chapter3;

import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

public class DeferredExecution {
    
    private static Logger logger = Logger.getLogger(DeferredExecution.class.getName());
    @Before
    public void setUp() throws Exception {
    }

    public static void info(Logger logger, Supplier<String> supplier){
	if(logger.isLoggable(Level.INFO))
	    logger.info(supplier.get());
    }
    
    public static void repeat(int n, IntConsumer action) {
	for (int i = 0; i < n; i++) action.accept(i);
    }
    
    public static void doPredicate(String s, Predicate<String> p){
	if(p.test(s))
	    System.out.println("yes");
	else
	    System.out.println("no");
    }
    
    public static void doPredicate2(String s, Predicate<Object> p){
	
	if(p.test(s))
	    System.out.println("yes");
	else
	    System.out.println("no");
    }
    
    public static String transform(String s, UnaryOperator<String> o){
	return o.apply(s);
    }
    
    class Dummy {
	public Dummy(String s){this.s=s;}
	private String s;
    }
    
    @FunctionalInterface
    interface DummyTransformer {
	Dummy changeIt(String s, Dummy o);
    }
    
    public static void changeSomething(Dummy d, DummyTransformer t){
	System.out.println(t.changeIt(":::", d).s);
    }
    
    public static UnaryOperator<Dummy> changer(String s){
	return d -> {d.s += s; return d;};
    }
    
    
    public static void changeSomething2(Dummy d, UnaryOperator<Dummy> u){
	u.apply(d);
	System.out.println(d.s);
    }
    
    @Test
    public void test() {
	
	Supplier<String> s = new Supplier<String>() {
	    
	    @Override
	    public String get() {
		return "the log message";
	    }
	};
	
	info(logger, s);
	repeat(10, i -> System.out.println("countdown: " + (9 - i)));
	doPredicate("askdlkjf", st -> st.length() >= 4);
	doPredicate("ask", st -> st.length() >= 4);
	doPredicate2("ask", Predicate.isEqual("1").or(Predicate.isEqual("2")));
	
	System.out.println(transform("", st -> st + ":"));
	
	changeSomething(new Dummy("www"), (String st, Dummy d) -> { d.s += st; return d;} );
	changeSomething2(new Dummy("www"), changer("+++") );
    }
    
    
    
    
    

}
