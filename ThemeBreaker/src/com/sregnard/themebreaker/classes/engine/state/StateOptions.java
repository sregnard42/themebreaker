package com.sregnard.themebreaker.classes.engine.state;

import java.util.ArrayList;

import com.sregnard.themebreaker.classes.others.Observable;
import com.sregnard.themebreaker.classes.others.Observer;

public class StateOptions implements Observable {

	final static int OFF = 0;
	final static int ON = 1;
	final static int HIDDEN = 2;

	int state;
	ArrayList<Observer> obs = new ArrayList<Observer>();
	boolean needUpdate = true;

	public StateOptions() {
		state = HIDDEN;
	}

	public void setState(int state) {
		this.state = state;
		updateAll();
	}

	public void off() {
		setState(OFF);
	}

	public boolean isOff() {
		return state == OFF;
	}

	public void on() {
		setState(ON);
	}

	public boolean isOn() {
		return state == ON;
	}

	public void hide() {
		setState(HIDDEN);
	}

	public boolean isHidden() {
		return state == HIDDEN;
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
