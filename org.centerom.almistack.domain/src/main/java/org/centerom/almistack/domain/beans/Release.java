
// 
package org.centerom.almistack.domain.beans;

// 
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Release extends AbstractBean {

	//
    private Date startDate     = null;
    private Date endDate       = null;
    private Float velocity     = null;
    private Float estimation   = null;
    private Float done         = null;
    private Float toDo         = null;
    private String state       = null;
    private Integer version    = null;
    private User user          = null;
	private Project owner      = null;
    // Map for storing iterations
    // 	- Key   -> Iteration Id
    // 	- Value -> Iteration Object
	private Map<Integer,
				Iteration> iterations = null;


    public Release() {
		super();
		this.iterations = new HashMap<Integer,
									  Iteration>();
	}

	public Release(Integer id,
				   String  name,
				   Date    startDate,
				   Date    endDate,
				   Float   estimation,
				   Float   done,
				   Float   toDo,
				   String  state,
				   String  description,
				   String  comments,
				   User    user,
				   Project owner,
				   Map<Integer,
					   Iteration> iterations) {
		super(id, name, description, comments);

		this.startDate  = startDate;
		this.endDate    = endDate;
		this.estimation = estimation;
		this.done       = done;
		this.toDo		= toDo;
		this.state      = state;
		this.setUser(user);
		this.owner      = owner;
		this.iterations = iterations;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Float getVelocity() {
		return velocity;
	}

	public void setVelocity(Float velocity) {
		this.velocity = velocity;
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

	public Float getToDo() {
		return toDo;
	}

	public void setToDo(Float toDo) {
		this.toDo = toDo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getOwner() {
		return owner;
	}

	public void setOwner(Project owner) {
		this.owner = owner;
	}

	public Map<Integer, Iteration> getIterations() {
		return iterations;
	}

	public void setIterations(Map<Integer, Iteration> iterations) {
		this.iterations = iterations;
	}

	public String toString() {
		return getName();
	}

}
// END <Release> class
// --- --------- -----