/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import java.util.Iterator;
import model.User;
import model.UsersDummy;

/**
 *
 * @author santiago
 */
public class Security {

    private UsersDummy systemUsers;


    public  Security() {
            systemUsers = UsersDummy.getInstance();
    }

    public boolean isAValidUser(String username, String password) {

        for (Iterator user = systemUsers.getUsers().iterator(); user.hasNext();) {
            User actual = (User) user.next();
            if(username.equals(actual.getUsername()) && password.endsWith(actual.getPassword())) {
                return true;
            }

        }
        return false;
    }




}

