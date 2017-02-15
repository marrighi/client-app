package com.authenware.clientapp;

import java.util.Optional;

import org.apache.catalina.startup.Tomcat;
//import org.apache.commons.configuration.PropertiesConfiguration;

public class Main {
    
    public static final Optional<String> port = Optional.ofNullable(System.getenv("PORT"));
    
    public static void main(String[] args) throws Exception {
   // 	PropertiesConfiguration config = new PropertiesConfiguration("./application.properties");
//		System.out.println(" dato:"+config.getString("nombre"));
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