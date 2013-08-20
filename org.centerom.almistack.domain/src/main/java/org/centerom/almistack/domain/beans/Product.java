
//
package org.centerom.almistack.domain.beans;

//
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Product extends AbstractBean {

	//
    private Date    createdOn   = null;
    // Map for storing projects
    // 	- Key   -> Project Id
    // 	- Value -> Project Object
	private Map<Integer,
				Project> projects = null;


	public Product() {
		super();
		// 
		this.projects = new HashMap<Integer, Project>();
	}

	public Product(Integer id,
				   String  name,
				   String  description,
				   String  comments,
				   Date    createdOn,
				   Map<Integer,
				   	   Project> projects) {
		super(id, name, description, comments);

	    this.createdOn   = createdOn;
	    this.projects    = projects;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Map<Integer, Project> getProjects() {
		return projects;
	}

	public void setProjects(Map<Integer, Project> projects) {
		this.projects = projects;
	}

}
// END <Product> class
// --- --------- -----