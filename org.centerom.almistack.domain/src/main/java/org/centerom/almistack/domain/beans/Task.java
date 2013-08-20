
// 
package org.centerom.almistack.domain.beans;

 
public class Task extends AbstractBean {

	//
    private String  type       = null;
    private String state       = null;
    private Float  estimation  = Float.MIN_VALUE;
    private Float done         = Float.MIN_VALUE;
    private Boolean blocked    = null;
	private UserStory owner    = null;
    private String projectName = null;
//  private Attachment attachment  = null;


    public Task() {
		super();
	}
    
	public Task(Integer   id,
				String    name,
				String    type,
				String    state,
				Float     estimation,
				Float     done,
				Boolean   blocked,
				String    description,
				String    comments,
				UserStory owner,
				String    projectName) {
		super(id, name, description, comments);

	    this.type        = type;
	    this.state       = state;
	    this.estimation  = estimation;
	    this.done        = done;
	    this.blocked     = blocked;
	    this.owner       = owner; 
	    this.projectName = projectName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Float getDone() {
		return done;
	}

	public void setDone(Float done) {
		this.done = done;
	}

	public Boolean getBlocked() {
		return blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	public UserStory getOwner() {
		return owner;
	}

	public void setOwner(UserStory owner) {
		this.owner = owner;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
/*
	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}
	
	
*/
	
	public String toString() {
//		return idName + Constants.EMPTY + getName();
		return getName();
	}

}
// END <Task> class
// --- ------ -----