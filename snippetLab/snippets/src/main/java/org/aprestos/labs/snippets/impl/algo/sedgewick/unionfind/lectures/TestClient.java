package org.aprestos.labs.snippets.impl.algo.sedgewick.unionfind.lectures;

import java.util.Scanner;

public class TestClient {

	public static void main(String[] args){
		
		Scanner in = null;
		try {
			in = new Scanner(System.in);
			int n = in.nextInt();
			
			
			QuickFind algo = new QuickFind(n);
			
			while(in.hasNext()){
				int a = in.nextInt();
				int b = in.nextInt();
				if(!algo.connected(a, b)){
					algo.union(a, b);
					System.out.println(a + " " + b);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			in.close();
		}
	}
	
}
