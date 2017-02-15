/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author santiago
 */
public class AuthenwareConfig {


    private String field;
    private String application;
    private String APM_URL ;
    private String WRAP_URL;
    private String CORE_URL;

    public static AuthenwareConfig instance;


    private AuthenwareConfig() {
          this.field = "loginTest";
          this.application = "Test";
          this.APM_URL = "http://192.168.1.119:8080/authenware-apm/AuthenwareApmService?wsdl ";
          this.WRAP_URL = "http://192.168.1.119:8080/authenware-wrap/AuthenwareWrapService?wsdl";
          this.CORE_URL = "http://192.168.1.119:8080/authenware-core/AuthenwareCoreService?wsdl"; 
    }

    public static AuthenwareConfig getInstance(){
        if (instance == null) instance = new AuthenwareConfig();
        return instance;
    }

    /**
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * @param field the field to set
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * @return the application
     */
    public String getApplication() {
        return application;
    }

    /**
     * @param application the application to set
     */
    public void setApplication(String application) {
        this.application = application;
    }

    /**
     * @return the APM_URL
     */
    public String getAPM_URL() {
        return APM_URL;
    }

    /**
     * @param APM_URL the APM_URL to set
     */
    public void setAPM_URL(String APM_URL) {
        this.APM_URL = APM_URL;
    }

    /**
     * @return the WRAP_URL
     */
    public String getWRAP_URL() {
        return WRAP_URL;
    }

    /**
     * @param WRAP_URL the WRAP_URL to set
     */
    public void setWRAP_URL(String WRAP_URL) {
        this.WRAP_URL = WRAP_URL;
    }

        public String getCORE_URL() {
        return CORE_URL;
    }

    /**
     * @param WRAP_URL the WRAP_URL to set
     */
    public void setCORE_URL(String CORE_URL) {
        this.CORE_URL = CORE_URL;
    }
    @Override
    public String toString(){
    	StringBuffer stringB = new StringBuffer();
    	stringB.append("Field:");
    	stringB.append(field);
    	stringB.append("\nApplication:");
    	stringB.append(application);
    	stringB.append("\nAPM_URL:");
    	stringB.append(APM_URL);
    	stringB.append("\nCORE_URL:");
    	stringB.append(CORE_URL);
    	stringB.append("\nWRAP_URL:");
    	stringB.append(WRAP_URL);
    	return stringB.toString();
    }
}
