
//
package org.centerom.almistack.domain.beans;

//
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Iteration extends AbstractBean {

	// 
	private Date    startDate = null;
	private Date    dueDate   = null;
	private Release owner     = null;
    // Map for storing user stories
    // 	- Key   -> UserStory Id
    // 	- Value -> UserStory Object
	private Map<Integer,
				UserStory> userStories = null;


	public Iteration() {
		super();
		// 
		this.userStories = new HashMap<Integer, UserStory>();
	}

	public Iteration(Integer id,
					 String  name,
					 Date    startDate,
					 Date    dueDate,
					 String  description,
					 String  comments,
					 Release owner,
					 Map<Integer,
					 	 UserStory> userStories) {
		super(id, name, description, comments);

		this.startDate   = startDate;
		this.dueDate     = dueDate;
		this.owner       = owner;
		this.userStories = userStories;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Release getOwner() {
		return owner;
	}

	public void setOwner(Release owner) {
		this.owner = owner;
	}

	public Map<Integer, UserStory> getUserStories() {
		return userStories;
	}

	public void setUserStories(Map<Integer, UserStory> userStories) {
		this.userStories = userStories;
	}
	
	public String toString() {
		return getName();
	}

}
// END <Iteration> class
// --- ----------- -----