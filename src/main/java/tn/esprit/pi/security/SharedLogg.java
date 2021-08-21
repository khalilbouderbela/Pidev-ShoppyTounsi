package tn.esprit.pi.security;

import org.springframework.web.client.RestTemplate;

public class SharedLogg {
public static boolean addlog(String tableName,String actionName,UserDetailsImpl u){
	if(u!=null){
		long iduser=u.getId();
	final String uri = "http://localhost:8081/add-logg/"+actionName+"/"+tableName+"/"+iduser;

    RestTemplate restTemplate = new RestTemplate();
    String result = restTemplate.postForObject(uri,null,  String.class);
    if(result.equals("OK")){
    	return true;
    }else return false;
	}else{
		return false;
	}
}
}
