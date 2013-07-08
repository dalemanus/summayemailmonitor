package insider.utils;

import java.util.List;
import java.util.ArrayList;

public class Observable {

	List<Observer> observers = new ArrayList<Observer>();
	
	public void registerObserver(Observer observer) {
		if (!observers.contains(observer))
			observers.add(observer);
	}
	
	public void deRegisterObserver(Observer observer) {
		if (observers.contains(observer))
			observers.remove(observer);
	}
	
	public void updateAll(String result) {
		for (Observer observer : observers) {
			observer.update(result);
		}
	}
}
