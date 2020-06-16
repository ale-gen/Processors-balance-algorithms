package System;

import java.util.ArrayList;

public class Processor {
	
	public int number;
	public int load;
	public ArrayList<Process> processes;
	
	public Processor() {
		
	}
	
	public Processor(int number, int load, ArrayList<Process> processes) {
		this.number = number;
		this.load = load;
		this.processes = processes;
	}

	public Processor[] generator(int howMany, int time, int frequency, int maxTime, int chance, int maxLength) {
		Processor[] processors = new Processor[howMany];
		Process process = new Process();
		for (int i = 0; i < howMany; i++) {
			processors[i] = new Processor(i+1, 0, process.generator(time, frequency, maxTime, chance, maxLength));
		}
		return processors;
	}
	
	public Processor[] generator(int howMany, ArrayList<Process> processes) {
		Processor[] processors = new Processor[howMany];
		Process process = new Process();
		for (int i = 0; i < howMany; i++) {
			processors[i] = new Processor(i+1, 0, processes);
		}
		return processors;
	}
	
	public void show() {
		System.out.println(number + " " + load);
	}
}
