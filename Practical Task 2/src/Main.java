import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static void main(String args[]) throws IOException, InterruptedException {
		DiningPhilosopher dp = new DiningPhilosopher(); // create an instance to
														// DiningPhilosopher
														// class
		int simTime = 10000;
		if (args.length > 0) // check if parameters are passed as argument
			simTime = Integer.parseInt(args[0]); // the first parameter is the
													// simulation time
		dp.setSimulationTime(simTime); // set the simulation time.
		dp.initialize(); // initialize the required objects
		dp.start(); // start the simulation process

		ArrayList<Philosopher> philosophers = dp.getPhilosophers(); // get the
																	// philosophers
		/*
		 * the following code prints a table where each row corresponds to one
		 * of the Philosophers, and columns correspond to the Philosopher ID
		 * (PID), average think time (ATT), average eating time (AET), average
		 * hungry time (AHT), and number of eating turns(#ET).
		 */
		System.out.println("\n------------------------------------------");
		System.out.println("PID \tATT \tAET \tAHT \t#ET");
		for (Philosopher p : philosophers) {
			System.out.println(p.getId() + "\t" + p.getAverageThinkingTime() + "\t" + p.getAverageEatingTime() + "\t"
					+ p.getAverageHungryTime() + "\t" + p.getNumberOfEatingTurns());
		}
		System.out.println("------------------------------------------\n");
	}
}
