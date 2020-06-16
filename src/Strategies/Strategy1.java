package Strategies;

import java.util.Random;

import System.Processor;

public class Strategy1 {

	Processor[] processors;
	
	public Strategy1(Processor[] processors) {
		this.processors = processors;
	}
	
	public void strategy(int time, int z, int p) {
		Random r = new Random();
		int averageLoad = 0;
		int sum = 0;
		int index = 0;
		int questionNumber = 0;
		int migration = 0;
		int load[] = new int[time/10];
		
		for (int i = 0; i < time; i++) {
			if (i >= time / 2 && i % 11 == 0) {
				for (int x = 0; x < processors.length; x++) {
					processors[x].load -= (processors[x].load / 10);
				}
			}
			// Counting the average load of every processors
			if (i != 0 && i % 10 == 0) {
				for (int x = 0; x < processors.length; x++) {
					sum += processors[x].load;
				}
				load[index] = (sum / processors.length);
				sum = 0;
				index++;
			}
			for (int j = 0; j < processors.length; j++) {
				for (int k = 0; k < processors[j].processes.size(); k++) {
					if (processors[j].processes.get(k).arrivalTime + processors[j].processes.get(k).durationTime <= i) {
						// The processes in that processors was ended so we relieve the load of processor
						processors[j].load -= processors[j].processes.get(k).part;
						// Deleting the process from the processor
						processors[j].processes.remove(k);
					}
					
					// Checking if the new process had appeared
					if (k < processors[j].processes.size() && processors[j].processes.get(k).arrivalTime == i) {
						boolean ifMigration = false;
						// We asking z times about load of other processors
						for (int l = 0; l < z; l++) {
							int bound = r.nextInt(processors.length);
							questionNumber++;
							// If the load of random processors is lower than p, the process is done on y processor
							if (processors[bound].load < p) {
								processors[bound].load += processors[j].processes.get(k).part;
								processors[bound].processes.add(processors[j].processes.get(k));
								processors[j].processes.remove(k);
								migration++;
								ifMigration = true;
								if (ifMigration) 
									break;
							}
							// If the other processors load > p, so the process is done on x processor
							if (!ifMigration && processors[j].load + processors[j].processes.get(k).part < 100) {
								processors[j].load += processors[j].processes.get(k).part;
							}
						}
					}
				}
			}
		}
		// Counting the sum of load for all processors
		for (int i = 0; i < load.length; i++) {
			sum += load[i];
		}
		// Counting the average of load
		averageLoad = sum / load.length;
		sum = 0;
		for (int i = 0; i < load.length; i++) {
			sum += Math.abs(averageLoad - load[i]);
		}
		// Counting the average deviation
		int averageDeviation = sum / load.length;
		System.out.println("Strategy 1: " + "\nAverage processors load: " + averageLoad + "%\nAverage deviation: " + averageDeviation + "%\nQuestions number: " + questionNumber + "\nMigration number: " + migration);
	}
}
