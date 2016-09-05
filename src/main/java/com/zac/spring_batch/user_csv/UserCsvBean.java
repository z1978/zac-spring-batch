package com.zac.spring_batch.user_csv;



/**
 * 
 * @author zac
 * @version
 * @since 2.0.0
 */

public class UserCsvBean {
    private String lastName;
    public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	private String firstName;

    

    public UserCsvBean(String lastName, String firstName) {
        super();
        this.lastName = lastName;
        this.firstName = firstName;
    }



}
