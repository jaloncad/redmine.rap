
// Package
package org.centerom.almistack.domain.beans;


public class AbstractBean {

	// 
	private Integer id         = null;
	private String  name       = null;
    private String description = null;
    private String comments    = null;


	public AbstractBean() {
		super();
	}

	public AbstractBean(Integer id,
				   		String  name,
				   		String  description,
				   		String  comments) {
		super();

		this.id          = id;
		this.name        = name;
		this.description = description;
		this.comments    = comments;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
// END <AbstractBean> class
// --- -------------- -----