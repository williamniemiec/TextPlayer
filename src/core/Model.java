package core;

/**
 * Responsible for business logic.
 */
public interface Model 
{
	/**
	 * Attaches a view to be informed when the model changes.
	 * 
	 * @param		view Attached view
	 */
	public void attach(View view);
	
	/**
	 * Detaches a view so that it is no longer informed when the model changes.
	 * 
	 * @param		view View to be detached
	 */
	public void detach(View view);
	
	/**
	 * Notifies all attached views that the model has changed.
	 */
	public void notifyViews();
}
