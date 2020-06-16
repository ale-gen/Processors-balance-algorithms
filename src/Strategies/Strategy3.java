package Strategies;

import java.util.Random;

import System.Processor;

public class Strategy3 {
	
	Processor[] processors;
	
	public Strategy3(Processor[] processors) {
		this.processors = processors;
	}
	
	public void strategy(int time, int z, int p, int p2) {
		Random r = new Random();
		
		int averageLoad = 0;
		int counter = 0;
		int sum = 0;
		int index = 0;
		int questionNumber = 0;
		int migration = 0;
		int load[] = new int[time / 10];
		
		for (int i = 0; i < time; i++) {
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
								if(processors[bound].load < p) {
									processors[bound].load  += processors[j].processes.get(k).part;
									processors[bound].processes.add(processors[j].processes.get(k));
									processors[j].load -= processors[j].processes.get(k).part;
									processors[j].processes.remove(k);
									int actualProcess = bound;
									migration++;
									for (int o = 0; o < z; o++) {
										bound = r.nextInt(processors.length);
										questionNumber++;
										if (processors[bound].load > p) {
											processors[actualProcess].load += (processors[bound].load) / 5;
											processors[bound].load -= (processors[bound].load) / 10;
											processors[bound].processes.add(processors[j].processes.get(k));
											migration++;
											ifMigration = true;
											if (ifMigration)
												break;
										}
									}
									ifMigration = true;
								}
							}
						} else {
							if (processors[j].load + processors[j].processes.get(k).part < 100) {
								processors[j].load += processors[j].processes.get(k).part;
							}
						}
						for (int o = 0; o < processors.length; o++) {
							questionNumber++;
							if (processors[j].load < p2) {
								int bound = r.nextInt(processors.length);
								questionNumber++;
								if (processors[bound].load > p) {
									migration++;
									// Decrease the load about 20%
									processors[bound].load -= (processors[bound].load) / 5;
									processors[j].load += (processors[bound].load) / 5;
								}
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
		System.out.println("\nStrategy 3: " + "\nAverage processors load: " + averageLoad + "%\nAverage deviation: " + averageDeviation + "%\nQuestions number: " + questionNumber + "\nMigration number: " + migration);

	}

}
