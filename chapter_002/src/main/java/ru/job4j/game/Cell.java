package ru.job4j.game;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Cell of the board. It can be in two states:
 * barrier - the state when the cell permanently lock
 * not a barrier - a state in which a character can enter it, or already on it
 * @author achekhovsky
 * @version 0.1
 */
public class Cell extends ReentrantLock {
	private final boolean barrier;
	private SimpleHero occupant; 
	
	/**
	 * Initializing a cell with an initial state
	 * @param barrier - initial state
	 */
	public Cell(boolean barrier) {
		super();
		this.barrier = barrier;
		if (barrier) {
			this.lock();
		}
	}
	
	/**
	 * Put the hero to the cell.If the hero is put on a cell, it is locked.
	 * @param hero - a hero who is trying to occupy a cell
	 * @return true if success 
	 */
	public boolean setHero(SimpleHero hero) {
		boolean result = false;
		if (!barrier) {
			try {
				if (!isHeldByCurrentThread() && tryLock(500, TimeUnit.MILLISECONDS)) {
					this.occupant = hero;
					result = true;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public void removeHero() {
		if (isHeldByCurrentThread() && !this.barrier) {
			unlock();
			this.occupant = null;
		}
	}
	
	public Optional<SimpleHero> getOccupant() {
		return Optional.ofNullable(this.occupant);
	}

}
