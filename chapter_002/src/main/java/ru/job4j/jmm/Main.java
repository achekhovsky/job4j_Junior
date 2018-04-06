package ru.job4j.jmm;

public class Main {
	public static void main(String[] args) {
		TestedNumber tn = new TestedNumber(0);
		Thread firstModifier = new Thread(new Modifying(tn));
		Thread secondModifier = new Thread(new Modifying(tn));
		firstModifier.start();
		secondModifier.start();
		try {
			firstModifier.join();
			secondModifier.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Из за проблем с многопоточностью значение будет оличаться от
		//предполагаемого. (В данном случае 10000 * 2 = 20000) 
		System.out.println("Amount - " + tn.getValue());
	}
}
