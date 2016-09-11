package com.zac.spring_batch.user_csv;

/**
 * 
 * @author zac
 * @version
 * @since 2.0.0
 */

public class UserCsvBean {
    private String lastName;
    private String firstName;

    public UserCsvBean() {

    }

    public UserCsvBean(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "firstName: " + firstName + ", lastName: " + lastName;
    }

}
