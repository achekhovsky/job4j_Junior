package ru.job4j.nonblocked;

public class OptimisticException extends RuntimeException {
	private final String message;
	
	OptimisticException(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "OptimisticException: " + message;
	}
}
