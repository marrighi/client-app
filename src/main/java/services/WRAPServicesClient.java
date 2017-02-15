/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import com.authenware.wrap.client.implementation.AuthenwareWrapService_Service;
import com.authenware.wrap.client.implementation.GetSignatureOut;
import com.authenware.wrap.client.implementation.Property;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import model.AuthenwareConfig;

/**
 *
 * @author Administrador
 */
public class WRAPServicesClient {

    private String endPoint= AuthenwareConfig.getInstance().getWRAP_URL();
    private AuthenwareWrapService_Service service;

    public WRAPServicesClient() throws Exception{
        this.service = new  AuthenwareWrapService_Service(new URL(endPoint), new QName("http://implement.service.web.wrap.authenware.com/", "AuthenwareWrapService"));
    }

    public String getOTJ() throws Exception{

        List<Property> properties = new ArrayList<Property>();
        Property property = new Property();
        property.setName("browser");
        property.setValue(null);
		return this.service.getAuthenwareWrapServicePort().getOTJ(properties).getOTJ();
    }

    public String getSignature(String encodedSignature,List<Property> properties) throws Exception{
        GetSignatureOut  o = this.service.getAuthenwareWrapServicePort().getSignature(encodedSignature, properties);
	if (o.isValidOTJ()) {
            return o.getSignature();
        } else {
            throw new Exception("The Signature is invalid - This may be a Fraud");
        }
    }
    
}
