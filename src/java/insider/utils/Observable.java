package insider.utils;

import java.util.List;
import java.util.ArrayList;

/**
 * My implementation of the Observable part of an Observer pattern.
 * 
 * It's a concrete object, and so needs to be subclassed by the object you
 * intend to subscribe to.
 * 
 * @author dale.macdonald
 *
 */
public class Observable {

	List<Observer> observers = new ArrayList<Observer>();
	
	/**
	 * Supply an observer that wishes to be notified by this observable
	 */
	public void registerObserver(Observer observer) {
		if (!observers.contains(observer))
			observers.add(observer);
	}
	
	/**
	 * Remove an observer if it no longer wishes to subscribe.
	 */
	public void deRegisterObserver(Observer observer) {
		if (observers.contains(observer))
			observers.remove(observer);
	}
	
	/**
	 * The callback method that will talk to the observer.
	 * 
	 * This implementation uses a concrete string as the communication token,
	 * so is not particularly flexible, but is fit for purpose.
	 * 
	 * @param result the String to be communicated back to the observer.
	 */
	public void updateAll(String result) {
		for (Observer observer : observers) {
			observer.update(result);
		}
	}
}
