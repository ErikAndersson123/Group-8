package server;

import java.util.LinkedList;

import client.Observer;

public class Datalogic {
	private LinkedList<Observer> obs;
	
	
	public void Notify(/*need one probably*/){
		for (int i = 0; i < obs.size(); i++) {
			obs.get(i).update(/**/);
		}
	};
	public void addObserver(Observer obs) {
		this.obs.add(obs);
	}
	public void delObserver(Observer obs) {
		this.obs.remove(this.obs.indexOf(obs));
	}
}
