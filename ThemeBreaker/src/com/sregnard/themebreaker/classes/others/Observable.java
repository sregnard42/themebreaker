package com.sregnard.themebreaker.classes.others;

public interface Observable {

	public void addObs(Observer obs);

	public void delObs(Observer obs);

	public void delAllObs();

	public void updateAll();

	public boolean needUpdate();
	
	public void updateFinished();
}
