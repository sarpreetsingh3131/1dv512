public class PhilosopherThread extends Thread {

	private Philosopher philosopher;

	public PhilosopherThread(Philosopher p) {
		philosopher = p;
	}

	@Override
	public void run() {
		while (true) {
			thinkHungryEat();
		}
	}

	private void thinkHungryEat() {
		philosopher.think();
		philosopher.hungry();
		
		long hungerStart = System.currentTimeMillis();

		while (!philosopher.isLeftChopstickAvailable()) {
			try {
				Thread.sleep(5); // try after some time
			} catch (InterruptedException e) {
				// if time out and philosopher is waiting here, then it will add
				// that waiting time
				philosopher.addHungryTime((int) (System.currentTimeMillis() - hungerStart));
			}
		}
		philosopher.pickUpLeftChopstick();

		while (!philosopher.isRightChopstickAvailable()) {
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				philosopher.addHungryTime((int) (System.currentTimeMillis() - hungerStart));
			}
		}
		philosopher.pickUpRightChopstick();

		philosopher.addHungryTime((int) (System.currentTimeMillis() - hungerStart));

		philosopher.eat();
		philosopher.releaseLeftChopstick();
		philosopher.releaseRightChopstick();
	}

	public boolean isWaiting() {
		return philosopher.isHoldingOnlyLeftChopstick();
	}
}