package snippetlab.snippets.concurrency.commsynch;

import java.util.Random;

import snippetlab.snippets.AbstractSnippet;

public class Synching extends AbstractSnippet
{
	static int idgen;
	static int MAX_OPS=20;
	static int MAX_OP_VAL=8000;
	static int INITIAL_BALANCE=10000;
	

	@Override
	public void method()
	{
		BankAccount _account = new BankAccount();
		
		Thread one = new Thread(new BankClient(_account));
		Thread two = new Thread(new BankClient(_account));
		Thread three = new Thread(new BankClient(_account));
		Thread four = new Thread(new BankClient(_account));
		Thread five = new Thread(new BankClient(_account));
		
		
		one.start();
		two.start();
		three.start();
		four.start();
		five.start();
		
	}
	
	
	class BankClient implements Runnable
	{
		 
		int id;
		BankAccount account;
		
		BankClient(BankAccount _account)
		{
			this.id=++idgen;
			this.account = _account;
		}
		
		@Override
		public void run()
		{
			int _ops = 0;
			Random _random = new Random();
			long _balance = 0;
			long _val = 0;
			
			while(_ops++ < MAX_OPS)
			{
				synchronized (this.account)
				{
					_balance = account.getBalance();
					System.out.println(String.format("customer %d arrived at the bank, current balance is: %d", id, _balance));
					_val = _random.nextInt(MAX_OP_VAL);
					
					if( 1 >= _random.nextInt(3) )
					{
						
						if(0 > (_balance - _val))
						{
							System.out.println(String.format("customer %d cannot withdraw %d (%d)", id, _val, _balance));
						}
						else
						{
							System.out.println(String.format("customer %d can withdraw %d (%d)", id, _val, _balance));
							this.account.withdrawal(_val, this.id);
						}
					}
					else
						this.account.deposit(_val, this.id);
					
					System.out.println(String.format("customer %d left the bank, current balance is: %d", id, account.getBalance()));
				}
				
				
				try
				{
					Thread.sleep(1000);
				} 
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	class BankAccount
	{
		long balance;
		
		BankAccount()
		{
			this.balance = INITIAL_BALANCE;
			display();
		}
		
		void display()
		{
			System.out.println(String.format("current balance is: %d", this.balance));
		}
		
		synchronized void withdrawal(long _val, int _id)
		{

			if(0 > (this.balance - _val))
				System.out.println(String.format("customer %d wanted to withdrawn  %d, but there's no money in (%d) !",_id, _val, this.balance));
			else
			{
				this.balance -= _val;
				System.out.println(String.format("customer %d has withdrawn  %d",_id, _val));
			}
			
		}
		
		synchronized void deposit(long _val, int _id)
		{

			this.balance += _val;
			System.out.println(String.format("customer %d has deposited  %d",_id, _val));

		}
		
		synchronized long getBalance()
		{
			return this.balance;
		}
		
		synchronized void setBalance(long _val)
		{
			this.balance = _val;
		}
	}

}
