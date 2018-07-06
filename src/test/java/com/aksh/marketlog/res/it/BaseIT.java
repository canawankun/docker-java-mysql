package com.aksh.marketlog.res.it;

public class BaseIT {
	
	static String getServerUrl(){
		if(System.getProperty("docker.host.address")!=null){
			return "http://"+System.getProperty("docker.host.address")+":8080";
		}else if (System.getProperty("heroku.appName")!=null){
			return "https://"+System.getProperty("heroku.appName")+".herokuapp.com";
		}else{
			return "http://localhost:8080";
		}
	}
	

}
