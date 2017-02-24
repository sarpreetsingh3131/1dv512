import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiningPhilosopher {

	private ArrayList<Philosopher> philosophers;
	private ArrayList<Chopstick> chopsticks;
	private int simulationTime = 10000; // default
	private ExecutorService philosophersThreadPool = Executors.newFixedThreadPool(5);
	private ExecutorService deadlockDetectorThreadPool = Executors.newFixedThreadPool(1);
	private static PrintWriter writer;
	private long startTime;
	private ArrayList<PhilosopherThread> philosophersThreads;
	private DeadlockDetector deadlockDetector;
	private final File logFile = new File("src/Log.txt");

	public DiningPhilosopher() {
		philosophers = new ArrayList<>();
		chopsticks = new ArrayList<>();
		philosophersThreads = new ArrayList<>();
		
		try {
			writer = new PrintWriter(logFile); // create file
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void initialize() {
		for (int i = 1; i <= 5; i++) {
			chopsticks.add(new Chopstick(i));
		}
		
		for (int i = 1; i <= 5; i++) {
			Philosopher p;
			if (i == 5) {
				p = new Philosopher(i, chopsticks.get(0), chopsticks.get(i - 1), writer);
			} else {
				p = new Philosopher(i, chopsticks.get(i), chopsticks.get(i - 1), writer);
			}
			
			philosophers.add(p);
			philosophersThreads.add(new PhilosopherThread(p));
		}
		deadlockDetector = new DeadlockDetector(philosophersThreads);
		deadlockDetectorThreadPool.execute(deadlockDetector);
	}

	public void start() {
		startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - startTime < simulationTime && !deadlockDetector.isDeadlock()) {
			philosophersThreadPool.execute(philosophersThreads.get(0));
			philosophersThreadPool.execute(philosophersThreads.get(1));
			philosophersThreadPool.execute(philosophersThreads.get(2));
			philosophersThreadPool.execute(philosophersThreads.get(3));
			philosophersThreadPool.execute(philosophersThreads.get(4));
		}
		System.out.println("RUTIME: " + (System.currentTimeMillis() - startTime));
		
		philosophersThreadPool.shutdown();
		philosophersThreadPool.shutdownNow();
		deadlockDetectorThreadPool.shutdown();
		deadlockDetectorThreadPool.shutdownNow();
		
		if (deadlockDetector.isDeadlock()) {
			writer.println("********DEADLOCK*********");
		} else {
			writer.println("********TIME-OUT*********");
		}

		try {
			writer.close();
			System.out.println("Log file created successfully.");
		} catch (Exception e) {
			System.out.println("***** ERROR: CANNOT CREATE FILE ********");
		}
	}

	public void setSimulationTime(int i) {
		simulationTime = i;
	}

	public ArrayList<Philosopher> getPhilosophers() {
		return philosophers;
	}
}