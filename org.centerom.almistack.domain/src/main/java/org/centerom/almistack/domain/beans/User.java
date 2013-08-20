
// Package
package org.centerom.almistack.domain.beans;


public class User {

	//	private String login;
//	private String password;
	private Integer id       = null;
	private String firstName = null;
	private String lastName  = null;
//	private String mail;
//	private Date createdOn;
//	private Date lastLoginOn;


	public User() {
		super();
	}

	public User(Integer id,
				String  firstName,
				String  lastName) {
		super();

		this.id        = id;
		this.firstName = firstName;
		this.lastName  = lastName;
	}
	
    public Integer getId() {
        return id;
    }

    public String toString() {
        return getFullName();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

}
// END <User> class
// --- ------ -----