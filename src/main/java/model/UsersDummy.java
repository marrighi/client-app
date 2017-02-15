/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import java.util.Iterator;




public class UsersDummy {
	
	
	public String usuario;
    private ArrayList<User> users;
    private static UsersDummy instance;

    public UsersDummy(){
        users = new ArrayList<User>();
        users.add(new User("danielcaselles","danielcaselles", "2011-07-20 10:55:00","danyel.caselles@gmail.com"));
        users.add(new User("dcaselles","danielcaselles", "2011-08-12 11:25:00","danyel.caselles@gmail.com"));
        users.add(new User("integrado","sinproblemas", "2003-03-14 08:00:00","danyel.caselles@gmail.com"));
         users.add(new User("testing","testing", "2003-03-14 08:00:00","jeremiascastillo@gmail.com"));
        users.add(new User("test","test", "2003-03-14 08:00:00","marrighi@gmail.com"));
    }

   public static UsersDummy getInstance(){
       if (instance == null) instance = new UsersDummy();
       return instance;
   }
    
    public User getUser(String username){
        for (Iterator user = users.iterator(); user.hasNext();) {
            User currentUser = (User) user.next();
            if(username.equals(currentUser.getUsername() )) {
                return currentUser;
            }
        }
        return null;
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


}
