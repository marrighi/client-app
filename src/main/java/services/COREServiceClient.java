/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package services;


import com.authenware.core.client.implementation.AuthenwareCoreService_Service;
import com.authenware.core.client.implementation.FieldIn;
import com.authenware.core.client.implementation.PatternResetOut;
import com.authenware.core.client.implementation.Property;
import com.authenware.core.client.implementation.UserIn;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.namespace.QName;
import model.AuthenwareConfig;
import model.User;

/**
 *
 * @author mauriciogaray
 */
    public class COREServiceClient {

        private String endPoint= AuthenwareConfig.getInstance().getCORE_URL();
        private AuthenwareCoreService_Service service;

        private static COREServiceClient instance;

        public COREServiceClient() throws Exception{
        service = new AuthenwareCoreService_Service(new URL(endPoint),new QName("http://implement.service.web.authentest.authenware.com/", "AuthenwareCoreService"));
    }

            //Definition of web service URL


    public static COREServiceClient getInstance() throws Exception{
        if (instance == null) {
            instance = new COREServiceClient();
        }
        return instance;
    }
        
    public PatternResetOut patternReset (User user){

        FieldIn fieldin=new FieldIn();
        fieldin.setApplication(AuthenwareConfig.getInstance().getApplication()); //Setting the application name
        fieldin.setClientField(AuthenwareConfig.getInstance().getField()); //Setting the fields names

        UserIn userin = new UserIn();
        userin.setUserName(user.getUsername()); //Setting the username value
        userin.setIdOrganization(1);//Setting the organization value


        // APM 1 // com.authenware.authentest.proxy.User userAuthenware=new com.authenware.authentest.proxy.User();
        com.authenware.core.client.implementation.UserIn userAuthenware = new com.authenware.core.client.implementation.UserIn();
        userAuthenware.setIdOrganization(1);    
        userAuthenware.setUserName(user.getUsername()); 

        PatternResetOut answer = service.getAuthenwareCoreServicePort().patternReset(userin, fieldin, new ArrayList<Property>());

        return answer;
    }
}
