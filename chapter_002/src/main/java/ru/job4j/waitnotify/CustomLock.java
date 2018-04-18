package ru.job4j.waitnotify;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class CustomLock implements Lock {
	
	private final Sync sync = new Sync();
	
	/**
	 * It is a non-reentrant mutual exclusion lock class
	 * that uses the value zero to represent the unlocked 
	 * state, and one to represent the locked state.
	 */
	private static class Sync extends AbstractQueuedSynchronizer {
	
		/** 
		 * Reports whether in locked state
		 * @return true if locked
		 */
		protected boolean isHeldExclusively() {
			return getState() == 1;
		}

		/** 
		 * Acquires the lock if state is zero
		 * @param acquires - parameter is not used because it
		 * non-reentrant mutual exclusion Lock. It is assumed 
		 * that it is equal to 1
		 * @return true if acquired
		 */
		public boolean tryAcquire(int acquires) {
			boolean acquired = false;
			if (compareAndSetState(0, 1)) {
				setExclusiveOwnerThread(Thread.currentThread());
				acquired = true;
			} else {
				//Output to the screen for greater visibility of the test
				System.out.printf("%s can not modify an object because it is locked\n", Thread.currentThread().getName());
			}
			return acquired;
		}

		/** 
		 * Acquires the lock if state is zero
		 * @param releases - parameter is not used.
		 * It is assumed that it is equal to 1
		 * @return true if acquired
		 */
		protected boolean tryRelease(int releases) {
			System.out.println("TryRe");
			if (getState() == 0 || Thread.currentThread() != getExclusiveOwnerThread()) {
				throw new IllegalMonitorStateException();				
			}
				setExclusiveOwnerThread(null);
				setState(0);				
			return true;
		}

		Condition newCondition() {
			return new ConditionObject();
		}
	}
	
	@Override
	public void lock() {
		sync.acquire(1);		
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
	}

	@Override
	public boolean tryLock() {
		return sync.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return sync.tryAcquireNanos(1, unit.toNanos(time));
	}

	@Override
	public void unlock() {
		sync.release(1);		
	}

	@Override
	public Condition newCondition() {
		return sync.newCondition();
	}
}
