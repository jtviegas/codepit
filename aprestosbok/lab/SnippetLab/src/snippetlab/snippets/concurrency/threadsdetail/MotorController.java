package snippetlab.snippets.concurrency.threadsdetail;

public class MotorController extends Thread
{

	public MotorController()
	{
		// TODO Auto-generated constructor stub
	}

	public MotorController(Runnable target)
	{
		super(target);
		// TODO Auto-generated constructor stub
	}

	public MotorController(String name)
	{
		super(name);
		// TODO Auto-generated constructor stub
	}

	public MotorController(ThreadGroup group, Runnable target)
	{
		super(group, target);
		// TODO Auto-generated constructor stub
	}

	public MotorController(ThreadGroup group, String name)
	{
		super(group, name);
		// TODO Auto-generated constructor stub
	}

	public MotorController(Runnable target, String name)
	{
		super(target, name);
		// TODO Auto-generated constructor stub
	}

	public MotorController(ThreadGroup group, Runnable target, String name)
	{
		super(group, target, name);
		// TODO Auto-generated constructor stub
	}

	public MotorController(ThreadGroup group, Runnable target, String name,
			long stackSize)
	{
		super(group, target, name, stackSize);
		// TODO Auto-generated constructor stub
	}

}
