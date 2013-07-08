package insider.utils;

/**
 * Very simple observer interface - simple, but fit for purpose.
 * @author dale.macdonald
 *
 */
public interface Observer {
	
	/**
	 * The one method which needs to be implemented by the concrete observer.
	 * @param result
	 */
	public void update(String result);
}
