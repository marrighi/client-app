package services;

import com.authenware.apm.client.implementation.AuthenwareApmService_Service;
import com.authenware.apm.client.implementation.CreatePatternOut;
import com.authenware.apm.client.implementation.Field;
import com.authenware.apm.client.implementation.Property;
import com.authenware.apm.client.implementation.ValidateUserOut;
import com.authenware.apm.client.implementation.ValidateVerificationCodeOut;
import model.User;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.namespace.QName;
import model.AuthenwareConfig;



/**
 *
 * @author fcontigliani
 */
public class APMServiceClient {


        private String endPoint= AuthenwareConfig.getInstance().getAPM_URL();
        // APM 1 // private AuthentestProxyService_Service service;
        private AuthenwareApmService_Service service;

        private static APMServiceClient instance;

        public APMServiceClient() throws Exception{
            service = new AuthenwareApmService_Service(new URL(endPoint),new QName("http://implement.service.web.apm.authenware.com/", "AuthenwareApmService"));
        }

        public static APMServiceClient getInstance() throws Exception{
                if (instance == null) instance = new APMServiceClient();
                return instance;
        }

        public ValidateUserOut validateUser(User user, String signature, String ipAddress,String ispName){
             
            Field field=new Field();
            field.setApplication(AuthenwareConfig.getInstance().getApplication());
            field.setName(AuthenwareConfig.getInstance().getField());
            com.authenware.apm.client.implementation.User userAuthenware = new com.authenware.apm.client.implementation.User();
            userAuthenware.setOrganization(1);    //Setting the organization value
            userAuthenware.setUserName(user.getUsername()); //Setting the username value

            List<Property> list= new ArrayList<Property>();
            Property ip=new Property();
            ip.setName("ip");
            ip.setValue(ipAddress);
            list.add(ip);
            Property isp=new Property();
            isp.setName("isp");
            isp.setValue(ispName);
            list.add(isp);
            Property securityLevel= new Property();
            securityLevel.setName("securityLevel");
            securityLevel.setValue("");
            list.add(securityLevel);
            Property userGroup= new Property();
            userGroup.setName("userGroup");
            userGroup.setValue("AuthentestParanoid");
            list.add(userGroup);
            Property userEmail= new Property();
            userEmail.setName("userEmail");
            userEmail.setValue(user.getEmail());
            list.add(userEmail);
            Property userCellPhone= new Property();
            userCellPhone.setName("userCellPhone");
            userCellPhone.setValue("");
            list.add(userCellPhone);

            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Property credentialContainerDate= new Property();
            credentialContainerDate.setName("credentialContainerDate");
            credentialContainerDate.setValue(f.format(Calendar.getInstance().getTime())); //
            list.add(credentialContainerDate);
            Property credentialLastSet= new Property();
            credentialLastSet.setName("credentialLastSet");
            credentialLastSet.setValue(user.getCredentialLastSet());
            list.add(credentialLastSet);

            ValidateUserOut answer=service.getAuthenwareApmServicePort().validateUser(userAuthenware, field, signature, list);
            return answer;

        }

        public ValidateVerificationCodeOut validateVerificationCode(User user, String otpcode ){

            Field field=new Field();
            field.setApplication(AuthenwareConfig.getInstance().getApplication());
            field.setName(AuthenwareConfig.getInstance().getField());

            com.authenware.apm.client.implementation.User userAuthenware = new com.authenware.apm.client.implementation.User();
            userAuthenware.setOrganization(1);    //Setting the organization value
            userAuthenware.setUserName(user.getUsername()); //Setting the username value

            ValidateVerificationCodeOut answer = service.getAuthenwareApmServicePort().validateVerificationCode(userAuthenware, field, otpcode);
            return answer;

        }

        public CreatePatternOut createPattern (User user, List<String> signatures, String ipAddress,String ispName){

            Field field=new Field();
            field.setApplication(AuthenwareConfig.getInstance().getApplication());
            field.setName(AuthenwareConfig.getInstance().getField());

            com.authenware.apm.client.implementation.User userAuthenware = new com.authenware.apm.client.implementation.User();
            userAuthenware.setOrganization(1);    //Setting the organization value
            userAuthenware.setUserName(user.getUsername()); //Setting the username value

            List<Property> list= new ArrayList<Property>();
            Property ip=new Property();
            ip.setName("ip");
            ip.setValue(ipAddress);
            list.add(ip);
            Property isp=new Property();
            isp.setName("isp");
            isp.setValue(ispName);
            list.add(isp);
            Property securityLevel= new Property();
            securityLevel.setName("securityLevel");
            securityLevel.setValue("");
            list.add(securityLevel);
            Property userEmail= new Property();
            userEmail.setName("userEmail");
            userEmail.setValue(user.getEmail());
            list.add(userEmail);
            Property userCellPhone= new Property();
            userCellPhone.setName("userCellPhone");
            userCellPhone.setValue("");
            list.add(userCellPhone);

            CreatePatternOut answer = service.getAuthenwareApmServicePort().createPattern(userAuthenware, field, signatures, list);
            return answer;

        }
            
}
