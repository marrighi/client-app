/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;




public class UsersDummy {
	
	
	public String usuario;
    private ArrayList<User> users;
    private static UsersDummy instance;

    public UsersDummy(){
        users = new ArrayList<User>();
    }

   public static UsersDummy getInstance(){
       if (instance == null) instance = new UsersDummy();
       return instance;
   }
    
    public User getUser(String username){
    	
    	for (Iterator<User> user = users.iterator(); user.hasNext();) {
            User currentUser = (User) user.next();
            if(username.equals(currentUser.getUsername() )) {
                return currentUser;
            }
        }
    	// if it is not validating, then create a phantom user, so it can be found by the method if it not exist
        if (!AuthenwareConfig.getInstance().isUserValidation()){
        	return addDummyUser(username);
        } else {
        	return null;
        }
    }


    /**
     * @return the users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void addUser (String[] userString){
    	User u = new User(userString[0],userString[1],userString[2],userString[3]);
    	users.add(u);
    	System.out.println("user: "+ u.getUsername()+ " has been created");
    }
    public User addDummyUser (String username){
    	User firstUser = users.get(0);
    	User u = new User(username,firstUser.getPassword(),firstUser.getCredentialLastSet(),firstUser.getEmail());
    	users.add(u);
    	System.out.println("Dummy User: "+ u.getUsername()+ " has been created");
    	return u;
    }

}
