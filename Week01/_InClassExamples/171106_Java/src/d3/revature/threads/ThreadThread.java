package d3.revature.threads;

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
}
