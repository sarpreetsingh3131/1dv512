
/*
 * File:	Process.java 
 * Course: 	Operating Systems
 * Code: 	1DV512
 * Author: 	Suejb Memeti
 * Date: 	November, 2016
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FCFS {

	// The list of processes to be scheduled
	public ArrayList<Process> processes;
	private int m_completedTime;

	// Class constructor
	public FCFS(ArrayList<Process> processes) {
		//this.processes = processes;
		this.m_completedTime = 0;
		this.processes = sortProcessesAccordingToArrivalTimes(processes); // would be nice if processes are sorted immediately
	}

	
	public void run() {
		//this.processes = sortProcessesAccordingToArrivalTimes(processes); // alternative
		for (Process a_process : processes) {
			a_process.setCompletedTime(calculateCompletedTime(a_process));
			a_process.setTurnaroundTime(calculateTurnaroundTime(a_process));
			a_process.setWaitingTime(calculateWaitingTime(a_process));
		}
	}

	
	public void printTable() {
		System.out.println(" __________________________________________________________________________________________");
		System.out.println("| Process Id | Arrival Time | Burst Time | Completed Time | Turnaround Time | Waiting Time |");
		System.out.println("|____________|______________|____________|________________|_________________|______________|");

		for (Process a_process : processes) {
			System.out.printf("|%7s     |%9s     |%7s     |%11s     |%12s     |%9s     |\n", a_process.getProcessId(),
					a_process.getArrivalTime(), a_process.getBurstTime(), a_process.getCompletedTime(),
					a_process.getTurnaroundTime(), a_process.getWaitingTime());
			System.out.println("|____________|______________|____________|________________|_________________|______________|");
		}
	}

	
	public void printGanttChart() {
		StringBuilder time = new StringBuilder();
		StringBuilder a_border = new StringBuilder();
		StringBuilder an_Id = new StringBuilder();

		for (int i = 0; i < processes.size(); i++) {
			Process a_process = processes.get(i);
			if (i == 0) {
				time.append(a_process.getArrivalTime()); // take the initial time
			}

			for (int j = 0; j < a_process.getCompletedTime(); j++) {
				a_border.append(" == ");
				an_Id.append("  ");
				time.append("    ");

				if (j == a_process.getCompletedTime() - 1) {
					time.setLength(a_border.length() + 1);  // manage the white spaces
					time.append(a_process.getCompletedTime());
					an_Id.append(a_process.getProcessId());
					a_border.append("|");

					while (an_Id.length() < a_border.length() - 1) {  // for making the chart nicely
						an_Id.append(" ");
					}
					an_Id.append("|");
				}
			}
		}
		System.out.println("|" + a_border.toString() + "\n|" + an_Id.toString() + "\n|" + a_border.toString() + "\n"+ time.toString() + "\n\n");
	}
	
	
	private int calculateCompletedTime(Process a_process) {
		 if (a_process.getArrivalTime() <= m_completedTime) {
			m_completedTime = m_completedTime + a_process.getBurstTime();
		} else {
			m_completedTime = a_process.getArrivalTime() + a_process.getBurstTime();
		}
		return m_completedTime;
	}

	
	private int calculateTurnaroundTime(Process a_process) {
		return a_process.getCompletedTime() - a_process.getArrivalTime();
	}

	
	private int calculateWaitingTime(Process a_process) {
		return a_process.getTurnaroundTime() - a_process.getBurstTime();
	}

	
	private ArrayList<Process> sortProcessesAccordingToArrivalTimes(ArrayList<Process> m_processes) {
		Collections.sort(m_processes, new Comparator<Process>() {
			@Override
			public int compare(Process first, Process second) {
				return first.getArrivalTime() - second.getArrivalTime();
			}
		});
		return m_processes;
	}
}
