package model;

/**
 *
 * @author fcontigliani
 */
public class User {

    private String username;
    private String password;
    private String credentialLastSet;
    private String email;

    public User(String username, String password, String credentialLastSet, String email){
        this.username=username;
        this.password=password;
        this.credentialLastSet = credentialLastSet;
        this.email = email;
    }

        /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

  

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the credentialLastSet
     */
    public String getCredentialLastSet() {
        return credentialLastSet;
    }

    /**
     * @param credentialLastSet the credentialLastSet to set
     */
    public void setCredentialLastSet(String credentialLastSet) {
        this.credentialLastSet = credentialLastSet;
    }
    public boolean matchesWithCurrentUser (String username, String password){
        if((!AuthenwareConfig.getInstance().isUserValidation())){
        	return true;
        }
        if (AuthenwareConfig.getInstance().isCaseSensitive()){
        	if (this.getUsername().equals(username) && this.getPassword().equals(password)) {
        		return true;
        	} else {
        		return false;
        	}
        } else {
        	if (this.getUsername().equalsIgnoreCase(username) && this.getPassword().equalsIgnoreCase(password)) {
        		return true;
        	} else {
        		return false;
        	}
        }
    }

}
