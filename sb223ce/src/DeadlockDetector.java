import java.util.ArrayList;

public class DeadlockDetector extends Thread {

	private PhilosopherThread t1;
	private PhilosopherThread t2;
	private PhilosopherThread t3;
	private PhilosopherThread t4;
	private PhilosopherThread t5;
	private volatile boolean isDeadlock = false;

	public DeadlockDetector(ArrayList<PhilosopherThread> threads) {
		t1 = threads.get(0);
		t2 = threads.get(1);
		t3 = threads.get(2);
		t4 = threads.get(3);
		t5 = threads.get(4);
	}

	@Override
	public void run() {
		while (!isDeadlock) {
			checkForDeadlock();
		}
	}

	private void checkForDeadlock() {
		if (t1.isWaiting() && t2.isWaiting() && t3.isWaiting() && t4.isWaiting() && t5.isWaiting()) {
			isDeadlock = true;
		}
	}

	public boolean isDeadlock() {
		return isDeadlock;
	}
}