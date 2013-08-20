
// 
package org.centerom.almistack.domain.beans;

import java.util.Map;
import org.centerom.almistack.core.Constants;
import org.centerom.almistack.core.Utils;

 
public class UserStory extends AbstractBean {

	//
	private String  idName     = null;	// Name of the User Story Id (f.i: US109)
    private String  state      = null;
    private Float   estimation = Float.MIN_VALUE;
    private Iteration owner    = null;
    // Map for storing tasks
    // 	- Key   -> Task Id
    // 	- Value -> Tasks Object
	private Map<Integer, Task> tasks = null;
//  private Boolean assigned = null;


	public UserStory() {
		super(); 
	}

	public UserStory(Integer    id,
					 String     idName,
					 String     name,
					 String     state,
					 Float      estimation,
					 String     description,
					 String     comments,
					 Iteration  owner,
					 Map<Integer, Task>  tasks) {
		super(id, name, description, comments);

		this.idName     = idName;
		this.state      = state;
		this.estimation = estimation;
		this.owner      = owner;
		this.tasks      = tasks;
	}

	public String getIdName() {
		return idName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Float getEstimation() {
		return estimation;
	}

	public void setEstimation(Float estimation) {
		this.estimation = estimation;
	}

	public Iteration getOwner() {
		return owner;
	}

	public void setOwner(Iteration owner) {
		this.owner = owner;
	}

	public Map<Integer, Task> getTasks() {
		return tasks;
	}

	public void setTasks(Map<Integer, Task> tasks) {
		this.tasks = tasks;
	}
	
	public String toString() {
		return Utils.getValue(idName) + Constants.EMPTY_CHAR + getName();
	}

}
// END <UserStory> class
// --- ----------- -----