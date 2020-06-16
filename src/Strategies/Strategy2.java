package Strategies;

import java.util.Random;

import System.Processor;

public class Strategy2 {
	
	Processor[] processors;
	
	public Strategy2(Processor[] processors) {
		this.processors = processors;
	}
	
	public void strategy(int time, int p) {
		Random r = new Random();
		
		int averageLoad = 0;
		int counter = 0;
		int sum = 0;
		int index = 0;
		int questionNumber = 0;
		int migration = 0;
		int load[] = new int[time/10];
		
		for (int i = 0; i < time; i++)  {
			if (i >= time / 2 && i % 11 == 0) {
				for (int x = 0; x < processors.length; x++) {
					processors[x].load -= (processors[x].load / 10);
				}
			}
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
						processors[j].load -= processors[j].processes.get(k).part;
						processors[j].processes.remove(k);
					}
					
					if (k < processors[j].processes.size() && processors[j].processes.get(k).arrivalTime == i) {
						questionNumber++;
						if (processors[j].load > p) {
							boolean ifMigration = false;
							counter = 0;
							while (!ifMigration) {
								counter++;
								if(counter == 2 * processors.length) {
									ifMigration = true;
								}
								int bound = r.nextInt(processors.length);
								questionNumber++;
								if (processors[bound].load < p) {
									processors[bound].load += processors[j].processes.get(k).part;
									processors[bound].processes.add(processors[j].processes.get(k));
									processors[j].load -= processors[j].processes.get(k).part;
									processors[j].processes.remove(k);
									migration++;
									ifMigration = true;
								}
							}
						} else {
							if (processors[j].load + processors[j].processes.get(k).part < 100) {
								processors[j].load += processors[j].processes.get(k).part;
							}
						}
					}
				}
			}
		}
		for (int i = 0; i < load.length; i++) {
			sum += load[i];
		}
		averageLoad = sum / load.length;
		sum = 0;
		for (int i = 0; i < load.length; i++) {
			sum += Math.abs(averageLoad - load[i]);
		}
		int averageDeviation = sum / load.length;
		System.out.println("\nStrategy 2: " + "\nAverage processors load: " + averageLoad + "%\nAverage deviation: " + averageDeviation + "%\nQuestions number: " + questionNumber + "\nMigration number: " + migration);

	}
	
	

}
