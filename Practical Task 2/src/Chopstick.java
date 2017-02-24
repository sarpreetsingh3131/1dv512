public class Chopstick {

	private int id;
	private volatile boolean isAvailable;

	public Chopstick(int id) {
		this.id = id;
		isAvailable = true;
	}

	public void pickUp() {
		isAvailable = false;
	}

	public void release() {
		isAvailable = true;
	}

	public int getId() {
		return id;
	}

	public boolean isAvailable() {
		return isAvailable;
	}
}