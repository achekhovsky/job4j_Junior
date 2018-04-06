package ru.job4j.jmm;

/**
 * Runnable инкрементирующий в цикле значение поля testedNumber 
 * объекта класса TestedNumber  
 * @author achekhovsky
 * @version 0.1
 * @since 0.1
 */
public class Modifying implements Runnable {
	private TestedNumber tn;
	public Modifying(TestedNumber tn) {
		this.tn = tn;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10000; i++) {
			tn.setValue(tn.getValue() + 1);
		}
		System.out.println("Modification complete!");
	}
}
