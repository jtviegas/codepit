package org.aprestos.labs.snippets.impl.java8.fortheimpatient.lambdas.theory;

interface JackInterface {
    public static void jackMethod() {
	System.out.println("Hello");
    }
    default void jackMethod2() {
	System.out.println("jackmethod2");
    }
    void jackMethod3();
    public static void jackMethod4() {
	System.out.println("Hello4");
    }
    public static class JackClass {
	public static void printHello() {
	    System.out.println("Hello from JackClass");
	}
    }
    public class JackClassStrange {
	public void printBye() {
	    System.out.println("Bye from JackClassStrange");
	}
    }
}

public class JackImpl implements JackInterface {
   
    public static void main(String args[]) {
	//Static one
	JackInterface.jackMethod();
	// Local one
	jackMethod();
	JackInterface j1 = new JackImpl();
	j1.jackMethod2();
	j1.jackMethod3();
	JackInterface.jackMethod4();
	// Compile error even though you would think you could
	// see jackMethod4 from JackImpl seeing as it implements the interface
	// jackMethod4();
	JackInterface.JackClass.printHello();
	JackClassStrange strange = new JackInterface.JackClassStrange();
	strange.printBye();
    }
    public static void jackMethod() {
	System.out.println("Hello2");
    }
    public void jackMethod2() {
	System.out.println("Overridden");
    }
    @Override
    public void jackMethod3() {
	// TODO Auto-generated method stub
	System.out.println("JackMethod3");
    }
}