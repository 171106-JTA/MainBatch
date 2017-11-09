package d3.revature.threads;

public class ThreadsExample {
	public static void main(String[] args) {
		/*
		 *  A thread is a single line of execution with its own memory stack.
		 *  All threads share a heap However!, 
		 *  Multithreading:
		 *  -Multithreading is the act of sharing a load of work between multiple threads, running concurrently.
		 *  
		 *  
		 *  There are two ways to create a thread class. Either by extending thread or by 
		 *  Implementing runnable. 
		 *  
		 *  Typically you want to implement runnable over extending thread. When you extend Thread you are
		 *  aiming to overwrite functionality for the Thread class. Chances are you are not going to come
		 *   up with anything more efficient On top of this when you extend thread you are now not able to
		 *    extend any other classes Whereas
		 *  with runnable, you are simply implementing the run method and are still able to extend at most one other class
		 *  
		 *  
		 *  We can solve race condition with SYNCHRONIZATION:
		 *  DeadLock
		 *  Starvation
		 *  
		 *  
		 *  
		 * */
		
		System.out.println(Thread.currentThread().getName());
		System.out.println("===========================THreaDs===============================");
		ThreadThread tt = new ThreadThread();
		ThreadRunnable tr = new ThreadRunnable();
		
		Thread t1 = new Thread(tt, "Thread1");
		Thread t2 = new Thread(tr, "Thread2");
		//run does not start a thread
		// "start" starts a thread.
		t1.run();
		t2.run();
		t1.start();
		t2.start();
	}
}
