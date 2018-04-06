package ru.job4j.wordscounter;

public class Time implements Runnable {
	private double executionTime = 0d;
	private double stoppedTime = 0d;
	
	public Time(double stopTime) {
		this.stoppedTime = stopTime; 
	}
	
	@Override
	public void run() {
		while (this.executionTime <= this.stoppedTime) {
			if (!Thread.interrupted()) {
				try {
					Thread.sleep(1);
					this.executionTime += 0.001d;
				} catch (InterruptedException e) {
					System.out.printf("Interrupted! Execution time is %s seconds. \n", this.executionTime);
					return;
				}
			}
		}
		System.out.printf("Execution time (%s second(s)) is over. \n", this.stoppedTime);
		StartThreads.timeIsOver = true;
	}
}
