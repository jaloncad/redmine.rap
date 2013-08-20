
//
package org.centerom.almistack.domain.beans;

//
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Project extends AbstractBean {

	// 
	private URL     homePage  = null;
	private Date    createdOn = null;
	private Product owner     = null;
    // Map for storing releases
    // 	- Key   -> Release Id
    // 	- Value -> Release Object
	private Map<Integer,
				Release> releases = null;
	

	public Project() {
		super();
		// 
		this.releases = new HashMap<Integer, Release>();
	}

	public Project(Integer id,
				   String  name,
				   URL     homePage,
				   String  description,
				   String  comments,
				   Date    createdOn,
				   Product owner,
				   Map<Integer,
				   	   Release> releases) {
		super(id, name, description, comments);

		this.homePage  = homePage;
		this.createdOn = createdOn;
		this.owner     = owner;
		this.releases  = releases;
	}

	public URL getHomePage() {
		return homePage;
	}

	public void setHomePage(URL homePage) {
		this.homePage = homePage;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Product getOwner() {
		return owner;
	}

	public void setOwner(Product owner) {
		this.owner = owner;
	}

	public Map<Integer, Release> getReleases() {
		return releases;
	}

	public void setReleases(Map<Integer, Release> releases) {
		this.releases = releases;
	}
	
	public String toString() {
		return getName();
	}

}
// END <Project> class
// --- --------- -----