package com.authenware.clientapp;

import java.util.Optional;

import model.AuthenwareConfig;

import org.apache.catalina.startup.Tomcat;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Main {
    
    public static final Optional<String> port = Optional.ofNullable(System.getenv("PORT"));
    
    public static void main(String[] args) throws Exception {
    	PropertiesConfiguration config = new PropertiesConfiguration("application.properties");
    	AuthenwareConfig ac = AuthenwareConfig.instance;
    	ac.setAPM_URL(config.getString("APM_URL"));
    	ac.setCORE_URL(config.getString("CORE_URL"));
    	ac.setWRAP_URL(config.getString("WRAP_URL"));
    	ac.setApplication(config.getString("application"));
    	ac.setField(config.getString("field"));
    	System.out.println(ac.toString());
		String contextPath = "/";
        String appBase = ".";
        Tomcat tomcat = new Tomcat();     
        tomcat.setPort(Integer.valueOf(port.orElse("8060") ));
        tomcat.getHost().setAppBase(appBase);
        tomcat.addWebapp(contextPath, appBase);
        tomcat.start();
        tomcat.getServer().await();
    }
}