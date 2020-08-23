package core;


/**
 * Responsible for program interface.
 */
public interface View 
{	
	/**
	 * Updates view after a model to change.
	 * 
	 * @param		model Changed model
	 * @param		data Data provided by changing the model
	 */
	public void update(Model model, Object data);
}
