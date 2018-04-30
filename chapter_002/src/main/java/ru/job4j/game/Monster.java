package ru.job4j.game;

import java.awt.Point;
import java.util.Random;

public class Monster extends AbstractHero {
	private final Point[] allowedSteps;
	
	public Monster(String name, int stepSizeX, int stepSizeY) {
		super(name, CharacterTypes.MONSTER, stepSizeX, stepSizeY);
		this.allowedSteps = new Point[] {
				new Point(+stepSizeX, +stepSizeY),
				new Point(-stepSizeX, 0),
				new Point(-stepSizeX, -stepSizeY),
				new Point(0, +stepSizeY),
				new Point(+stepSizeX, -stepSizeY),
				new Point(0, -stepSizeY),
				new Point(-stepSizeX, +stepSizeY),
				new Point(+stepSizeX, 0)
		};
		
	}

	@Override
	public void motion() {
		boolean result = false;
		Random rnd = new Random();
		int count = 0;
		while (count < this.allowedSteps.length) {
			Point randomStep = this.allowedSteps[rnd.nextInt(this.allowedSteps.length - 1)];
			this.killBomberman(randomStep);
			if (!doStep(randomStep.x, randomStep.y) && !result) {
				result = this.repeatStep(randomStep);
			} else {
				break;
			}				
		}
	}
	
	@Override
	public void checkStepSize(int x, int y) throws IncorrectStepSizeException {
		if (this.stepSizeX < Math.abs(x) || this.stepSizeY < Math.abs(y)) {
			throw new IncorrectStepSizeException(name);
		}
	}
	
	/**
	 * @param stepOnTheBomberman
	 */
	private void killBomberman(Point stepOnTheBomberman) {
		Point currentPosition = this.getCurrentPosition();
		if (this.getBoard().checkBorders(currentPosition.x + stepOnTheBomberman.x, 
				currentPosition.y + stepOnTheBomberman.y)) {
			this.getBoard().board[currentPosition.x + stepOnTheBomberman.x][currentPosition.y + stepOnTheBomberman.y]
					.getOccupant().ifPresent((h) -> {
				if (h.getType() == CharacterTypes.BOMBERMAN) {
					h.killHero();
				}
			});					
		}
	}
	
	private boolean repeatStep(Point step) {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return doStep(step.x, step.y);
	}

}
