package org.aprestos.labs.challenges.stacksandqueues;

public class ThreeStacks {

 int p1=-1, p2=-1, p3=-1;
    int n;
    int s[];
    
    ThreeStacks(int n){
      this.n=n;
      this.s = new int[3*n];
    }
    
    private int getPointer(int index, int pointer) {
      return (s.length/3)* (index-1) + pointer; 
    }
    
    private int pop(int index){
      if(0 >= index || 3 < index)
        throw new IllegalArgumentException("index must be between 1 and 3");
      
      int pointer = ( 1 == index ? p1 : (2 == index ? p2 : p3)  );
      int r = 0;
      
      if( -1 != pointer ) {
        int realPointer = getPointer(index, pointer); 
        r = s[realPointer];
        s[realPointer] = 0;
        switch(index) {
          case 1: 
            p1--;break;
          case 2:
            p2--;break;
          default:
            p3--;
        }
      }
      
      return r;
    }
    
    private  void push(int index, int val) {
      int pointer = ( 1 == index ? p1 : (2 == index ? p2 : p3)  );
      pointer++;
      if( n == pointer)
        throw new IllegalArgumentException("stack is full");
      
      s[getPointer(index, pointer)]=val;
      switch(index) {
          case 1: 
            p1++;break;
          case 2:
            p2++;break;
          default:
            p3++;
        }
      
    }
    
    int pop1(){
      return pop(1);
    }
    
    int pop2(){
      return pop(2);
    }
    
    int pop3(){
      return pop(3);
    }
    
    void push1(int val){
      push(1,val);
    }
    
    void push2(int val){
      push(2,val);
    }
    
    void push3(int val){
      push(3,val);
    }

}
