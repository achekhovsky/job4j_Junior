package ru.job4j.game;

import java.awt.Point;
import java.util.concurrent.LinkedBlockingQueue;

public class Bomberman extends AbstractHero {
	private final LinkedBlockingQueue<Point> queue;
	
	public Bomberman(String name, int stepSizeX, int stepSizeY) {
		super(name, CharacterTypes.BOMBERMAN, stepSizeX, stepSizeY);
		queue = new LinkedBlockingQueue<>();
	}

	@Override
	public void motion() {
		if (this.isLive()) {
			Point currentStep = null;
			try {
				currentStep = this.queue.take();
				this.stepOnMonster(currentStep);
				this.doStep(currentStep.x, currentStep.y);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
		throw new GameOverException(this.name);
	}

	@Override
	public void checkStepSize(int x, int y) throws IncorrectStepSizeException {
		if (this.stepSizeX < Math.abs(x) || this.stepSizeY < Math.abs(y)) {
			throw new IncorrectStepSizeException(name);
		}		
	}
	
	@Override
	public void killHero() {
		super.killHero();
		queue.offer(getCurrentPosition());
	}
	
	/**
	 * Add a step to the queue
	 * @param newStep
	 */
	public void addStep(Point newStep) {
		this.checkStepSize(newStep.x, newStep.y);
		this.queue.add(newStep);
	}
	
	/**
	 * Check step and if a bomberman step on a monster, bomberman dies
	 * @param stepOnTheMonster - checked step
	 */
	public void stepOnMonster(Point stepOnTheMonster) {
		Point currentPosition = this.getCurrentPosition();
		if (this.getBoard().checkBorders(currentPosition.x + stepOnTheMonster.x, 
				currentPosition.y + stepOnTheMonster.y)) {
			this.getBoard().board[currentPosition.x + stepOnTheMonster.x][currentPosition.y + stepOnTheMonster.y]
					.getOccupant().ifPresent((h) -> {
				if (h.getType() == CharacterTypes.MONSTER) {
					this.killHero();
				}
			});					
		}
	}
}
