package com.sregnard.themebreaker.classes.engine.state;

import java.util.ArrayList;

import com.sregnard.themebreaker.classes.others.Observable;
import com.sregnard.themebreaker.classes.others.Observer;

public class StateGame implements Observable {

	final static int OFF = -1;
	final static int INITIALIZED = 0;
	final static int RUNNING = 1;
	final static int PAUSED = 2;
	final static int OVER = 3;

	int state;
	ArrayList<Observer> obs = new ArrayList<Observer>();
	boolean needUpdate = true;

	public StateGame() {
		state = OFF;
	}

	public void setState(int state) {
		this.state = state;
		updateAll();
	}

	public void off() {
		setState(OFF);
	}

	public void initialized() {
		setState(INITIALIZED);
	}

	public void run() {
		setState(RUNNING);
	}

	public void pause() {
		setState(PAUSED);
	}

	public void over() {
		setState(OVER);
	}
	
	public boolean isOff() {
		return state == OFF;
	}
	
	public boolean isInitialized() {
		return state == INITIALIZED;
	}
	
	public boolean isRunning() {
		return state == RUNNING;
	}
	
	public boolean isPaused() {
		return state == PAUSED;
	}
	
	public boolean isOver() {
		return state == OVER;
	}

	@Override
	public void addObs(Observer obs) {
		this.obs.add(obs);
	}

	@Override
	public void delObs(Observer obs) {
		this.obs.remove(obs);
	}

	@Override
	public void delAllObs() {
		this.obs.clear();
	}

	public boolean needUpdate() {
		return needUpdate;
	}
	
	@Override
	public void updateAll() {
		needUpdate = true;
		for (Observer obs : this.obs)
			obs.update();
	}
	
	@Override
	public void updateFinished() {
		needUpdate = false;
	}

}
