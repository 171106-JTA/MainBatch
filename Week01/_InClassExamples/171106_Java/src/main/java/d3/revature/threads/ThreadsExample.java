package d3.revature.threads;

public class ThreadsExample {

	public static void main(String[] args) {
		/*
		 * A thread is a single line of execution with its own memory stack.
		 * All threads share a heap however.
		 * Multithreading:
		 * -Multithreading is the act of sharing a load of work between multiple threads, running
		 * concurrently.
		 * 
		 * 
		 * There are two ways to create a thread class. Either by extending Thread or by
		 * implementing runnable.
		 * 
		 * Typically you want to implement runnable over extending thread. When you extend Thread
		 * you are aiming to overwrite functionality for the Thread class. Chances are you are not
		 * going to come up with anything more efficient. On top of this, when you extend Thread,
		 * you are now not able to extend any other classes. Whereas with runnable, you are simply
		 * implementing the run method, and are still able to extend at most one other class.
		 * 
		 * With multiple threads, we come across certain issues. One common issue of multithreading
		 * is called race conditions. A race condition is any element that will yield different results
		 * (Most times unwanted) based on the order they are interacted with through multiple threads.
		 * 
		 * We can solve race conditions by using synchronization. Synchronization is used to prevent
		 * a resource from being used by more than 1 thread at a time. Synchronized is a keyword you
		 * can apply to a method signature. You can also make entire blocks of synchronization.
		 */
		
		System.out.println(Thread.currentThread().getName());
		System.out.println("=========THREADS=======");
		
		
		ThreadThread tt = new ThreadThread();
		ThreadRunnable tr = new ThreadRunnable();
		
		Thread t1 = new Thread(tt, "Thread1"); //Pass an instance of a thread-possible object, + a name
		Thread t2 = new Thread(tr, "Thread2");
		
		//run() does not start a thread! start() starts a thread.
		t1.start();
		t2.start();
		
		
	}

}
