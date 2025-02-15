package models.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Responsible for calling routines after a certain time or whenever the the 
 * timer expires.
 * 
 * @author		William Niemiec &lt; williamniemiec@hotmail.com &gt;
 */
public class Clock 
{
	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	/**
	 * Stores routines sent to setInterval method along with its ids.
	 */
	private static Map<Integer, Timer> intervalRoutines = new HashMap<>();
	
	/**
	 * Stores routines sent to setTimeout method along with its ids.
	 */
	private static Map<Integer, Timer> timeoutRoutines = new HashMap<>();
	
	
	//-------------------------------------------------------------------------
	//		Constructor
	//-------------------------------------------------------------------------
	private Clock()
	{}
	
	
	//-------------------------------------------------------------------------
	//		Methods
	//-------------------------------------------------------------------------
	/**
	 * Sets a timer which executes a routine once the timer expires.
	 * 
	 * @param		routine Routine to be performed
	 * @param		id Routine identifier (necessary to be able to stop it)
	 * @param		delay Waiting time before the routine is executed (in 
	 * milliseconds)
	 * 
	 * @return		False if an timeout has has already been set with the 
	 * given id or true otherwise
	 */
	public static boolean setTimeout(Runnable routine, int id, int delay)
	{
		if (timeoutRoutines.containsKey(id))
			return false;
		
		Timer t = new Timer();
		
		
		timeoutRoutines.put(id, t);
		t.schedule(new TimerTask(){
		    @Override
		    public void run()
		    {
		       routine.run();
		    }
		}, delay);
		
		return true;
	}
	
	/**
	 * Repeatedly calls a routine with a fixed time delay between each call.
	 * 
	 * @param		routine Routine to be performed
	 * @param		id Routine identifier (necessary to be able to stop it)
	 * @param		interval Interval that the routine will be invoked (in 
	 * milliseconds)
	 * 
	 * @return		False if an interval has has already been set with the 
	 * given id or true otherwise
	 */
	public static boolean setInterval(Runnable routine, int id, int interval)
	{
		if (intervalRoutines.containsKey(id))
			return false;
		
		Timer t = new Timer();
		
		
		intervalRoutines.put(id, t);
		t.scheduleAtFixedRate(new TimerTask(){
		    @Override
		    public void run()
		    {
		       routine.run();
		    }
		}, 0, interval);
		
		return true;
	}
	
	/**
	 * Cancels a timed, repeating action, which was previously established by a
	 * call to {@link #setInterval(int, Runnable, int)}.
	 * 
	 * @param		id Routine id
	 */
	public static void clearInterval(int id)
	{
		if (intervalRoutines.containsKey(id)) {
			intervalRoutines.get(id).cancel();
			intervalRoutines.remove(id);
		}
	}
	
	/**
	 * Cancels a timed action which was previously established by a
	 * call to {@link #setTimeout(int, Runnable, int)}.
	 * 
	 * @param		id Routine id
	 */
	public static void clearTimeout(int id)
	{
		if (timeoutRoutines.containsKey(id)) {
			timeoutRoutines.get(id).cancel();
			timeoutRoutines.remove(id);
		}
	}
}
