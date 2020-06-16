package System;

import java.util.ArrayList;
import java.util.Random;

public class Process {
	
	public int part;
	public int durationTime;
	public int arrivalTime;
	
	public Process() {
		
	}
	
	public Process(int part, int durationTime, int arrivalTime) {
		this.part = part;
		this.durationTime = durationTime;
		this.arrivalTime = arrivalTime;
	}
	
	public ArrayList<Process> generator(int time, int frequency, int maxTime, int chance, int maxLength) {
		ArrayList<Process> processes = new ArrayList<Process>();
		Random r = new Random();
		for (int i = 0; i < time; i++) {
			if (r.nextInt(chance) + 1 == 1) {
				processes.add(new Process(r.nextInt(maxLength) + 1,r.nextInt(maxTime) + 1,i));
			}
		}
		return processes;
	}

}
