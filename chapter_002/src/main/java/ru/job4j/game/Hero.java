package ru.job4j.game;

import java.awt.Point;
import java.util.Random;

/**
 * @author achekhovsky
 * @version 0.1
 */
public class Hero extends AbstractHero {
	private final Point[] allowedSteps;
	
	public Hero(String name, int type, int stepSizeX, int stepSizeY) {
		super(name, type, stepSizeX, stepSizeY);
		this.allowedSteps = new Point[] {
				new Point(-stepSizeX, -stepSizeY),
				new Point(-stepSizeX, 0),
				new Point(+stepSizeX, +stepSizeY),
				new Point(0, +stepSizeY),
				new Point(+stepSizeX, -stepSizeY),
				new Point(0, -stepSizeY),
				new Point(-stepSizeX, +stepSizeY),
				new Point(+stepSizeX, 0)
		};
	}
	
	/* 
	 * @see ru.job4j.game.SimpleHero#motion()
	 */
	@Override	
	public void motion() {
		int numberOfAttempts = 0;
		Random rndStep = new Random();
		if (!doStep(this.allowedSteps[rndStep.nextInt(allowedSteps.length - 1)].x, 
				this.allowedSteps[rndStep.nextInt(allowedSteps.length - 1)].y)) {
			for (Point around : this.allowedSteps) {
				if (!doStep(around.x, around.y)) {
					numberOfAttempts++;
				} else {
					break;
				}				
			}
		}
		if (numberOfAttempts >= this.allowedSteps.length) {
			killHero();
			throw new GameOverException(name);
		}
	}
	
	/* 
	 * @see ru.job4j.game.SimpleHero#checkStepSize(int, int)
	 */
	@Override
	public void checkStepSize(int x, int y) {
		if (this.stepSizeX < Math.abs(x) || this.stepSizeY < Math.abs(y)) {
			throw new IncorrectStepSizeException(name);
		}
	}
}
