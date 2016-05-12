package snippetlab.snippets.assorted;

import snippetlab.snippets.AbstractSnippet;

public class JavaTests extends AbstractSnippet
{

	@Override
	public void method()
	{
		System.out.println("1/2=" + 1 / 2);
		System.out.println("Floor(-1/2)=" + Math.floor(-1 / 2));
		System.out.println("1/2+1/2=" + 1 / 2 + 1 / 2);

		String a = new String("a");
		String b = new String("a");

		System.out.println("a.equals(a)?" + a.equals(b));
		System.out.println(a == b);

		E _e = new E(2);

		doTestOne();
		
		
	}
	
	void doTestOne()
	{
		String a="X";
		String[] b={" ","a"};
		
		change(a,b);
		System.out.println("a=" + a);
		System.out.println("b[0]=" + b[0]);
		System.out.println("b[1]=" + b[1]);
	}
	
	void change(String a, String[] b)
	{
		a="Y";
		b[0]=a;
	}

}

class C
{
	int foo = 0;

	// private C()
	// {
	// foo=1;
	// }

	public C()
	{
		foo = 1;
	}

	public C(int foo)
	{

	}

	public int getFoo()
	{
		return foo;
	}

}

class E extends C
{
	public E(int foo)
	{

	}
}





// class D extends C
// {
// int g;
//
// public D(int g)
// {
// this.g=g;
// }
// }