package d3.revature.threads;

<<<<<<< HEAD
public class ThreadThread extends Thread {

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			System.out.println(Thread.currentThread().getName());
		}
		try {
			Thread.sleep(50);
			;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
=======
public class ThreadThread extends Thread{

	@Override
	public void run() {
		for(int i = 0; i < 20; i++){
			System.out.println(Thread.currentThread().getName());
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
		}
	}
	
>>>>>>> 908c4a08b3cf9c5cb65e60015f7c54cf564145ce
}
