import java.io.PrintWriter;
import java.util.Random;

public class Philosopher {

	public enum State {THINKING, HUNGRY, EATING}
	private int id;
	private Chopstick leftChopstick;
	private Chopstick rightChopstick;
	private int thinkingTime = 0;
	private int thinkingTurns = 0;
	private int hungryTime = 0;
	private int hungryTurns = 0;
	private int eatingTime = 0;
	private int eatingTurns = 0;
	private static PrintWriter writer;
	private volatile boolean isHoldingOnlyLeftChopstick = false;

	public Philosopher(int id, Chopstick left, Chopstick right, PrintWriter writer) {
		this.id = id;
		leftChopstick = left;
		rightChopstick = right;
		Philosopher.writer = writer;
	}
	
	public double getAverageThinkingTime() {
		return calculateAverage(thinkingTime, thinkingTurns);
	}

	public double getAverageEatingTime() {
		return calculateAverage(eatingTime, eatingTurns);
	}

	public double getAverageHungryTime() {
		return calculateAverage(hungryTime, hungryTurns);
	}

	public int getNumberOfEatingTurns() {
		return eatingTurns;
	}

	public int getId() {
		return id;
	}

	public synchronized boolean isLeftChopstickAvailable() {
		return leftChopstick.isAvailable();
	}

	public synchronized boolean isRightChopstickAvailable() {
		return rightChopstick.isAvailable();
	}

	public synchronized void addHungryTime(int time) {
		hungryTime += time;
	}

	public synchronized void pickUpLeftChopstick() {
		isHoldingOnlyLeftChopstick = true;
		pickUpChopstick(leftChopstick);
	}

	public synchronized void pickUpRightChopstick() {
		isHoldingOnlyLeftChopstick = false;
		pickUpChopstick(rightChopstick);
	}

	public synchronized void releaseLeftChopstick() {
		releaseChopstick(leftChopstick);
	}

	public synchronized void releaseRightChopstick() {
		releaseChopstick(rightChopstick);
	}
	
	public synchronized boolean isHoldingOnlyLeftChopstick() {
		return isHoldingOnlyLeftChopstick;
	}

	public void think() {
		synchronized(Philosopher.writer){
			writer.println("Philospher " + this.id + " is " + State.THINKING);	
		}
		int time = sleepTime();
		thinkingTime += time;
		thinkingTurns++;
	}

	public void hungry() {
		synchronized(Philosopher.writer){
			writer.println("Philospher " + this.id + " is " + State.HUNGRY);	
		}
		hungryTurns++;
	}

	public void eat() {	
		synchronized(Philosopher.writer){
			writer.println("Philospher " + this.id + " is " + State.EATING);	
		}
		int time = sleepTime();
		eatingTime += time;
		eatingTurns++;
	}
	
	private void pickUpChopstick(Chopstick c) {
		c.pickUp();
		writer.println("Chopstick " + c.getId() + " is PICKED_UP by Philosopher " + this.id);
	}

	private void releaseChopstick(Chopstick c) {
		c.release();
		writer.println("Chopstick " + c.getId() + " is RELEASED by Philosopher " + this.id);	
	}

	private double calculateAverage(int sum, int size) {
		if (size == 0) {
			return 0.0;
		} else {
			return (double) (sum / size);
		}
	}

	private int sleepTime() {
		Random ran = new Random();
		int num = ran.nextInt(10) + 1;

		try {
			Thread.sleep(num);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return num;
	}
}