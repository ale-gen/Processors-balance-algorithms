package Project_Main;

import java.util.ArrayList;

import Strategies.Strategy1;
import Strategies.Strategy2;
import Strategies.Strategy3;
import System.Process;
import System.Processor;

public class Project_Main {
	
	static public int processorsNumber = 50;
	static public int p = 90;
	static public int minP = 30;
	static public int z = 5;
	static public int time = 1000;
	static public int taskFrequency = 20;
	static public int maxTime = 3;
	static public int chance = 8;
	static public int part = 20;
	
	public static void main(String[] args) {
		
		Process process = new Process();
		ArrayList<Process> processes = process.generator(time, taskFrequency, maxTime, chance, part);
		ArrayList<Process> processes1 = (ArrayList<Process>) processes.clone();
		ArrayList<Process> processes2 = (ArrayList<Process>) processes.clone();
		ArrayList<Process> processes3 = (ArrayList<Process>) processes.clone();
	
		Processor processor = new Processor();
		Processor[] processors = new Processor[processorsNumber];
		Processor[] processors1 = new Processor[processorsNumber];
		Processor[] processors2 = new Processor[processorsNumber];
		Processor[] processors3 = new Processor[processorsNumber];
		
		processors = processor.generator(processorsNumber, processes);
		processors1 = processor.generator(processorsNumber, processes1);
		processors2 = processor.generator(processorsNumber, processes2);
		processors3 = processor.generator(processorsNumber, processes3);
		
		Strategy1 strategy1 = new Strategy1(processors);
		strategy1.strategy(time, z, p);
		Strategy2 strategy2 = new Strategy2(processors1);
		strategy2.strategy(time, p);
		Strategy3 strategy3 = new Strategy3(processors2);
		strategy3.strategy(time, z, p, minP);
	}

}
