package com.aksh.marketlog.res.it;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.aksh.marketlog.dto.Execution;
public class TradeControllerIT {


	private RestTemplate restTemplate=new RestTemplate();
	private String serverUrl;
	@Before
	public void setup(){
		serverUrl=BaseIT.getServerUrl();
		System.out.println("In unit test server url:"+serverUrl);
	}

	@Test
	public void pingtest() {
		ResponseEntity<String> pingResponse=restTemplate.getForEntity(serverUrl+"/executions/ping", String.class);
		assertEquals(HttpStatus.OK,pingResponse.getStatusCode());
	}

	
	
	@Test
	public void lastExecutionTest() {
		Date executionTime=new Date();
		Execution exec=new Execution();
		exec.setExectutionTime(executionTime);
		exec.setPrice(10.0F);
		exec.setQty(10);
		exec.setStock("AAPL");
		exec.setRefId(1);
		
		HttpEntity<Execution> request = new HttpEntity<>(exec);
		
		ResponseEntity<Execution> res=restTemplate.postForEntity(serverUrl+"/executions/save", request, Execution.class);
		assertEquals(HttpStatus.OK,res.getStatusCode());

		Execution fromDb=res.getBody();
		ResponseEntity<Execution> pingResponse=restTemplate.getForEntity(serverUrl+"/executions/last",Execution.class);
		assertEquals(HttpStatus.OK,pingResponse.getStatusCode());
		Execution lastFromDB=pingResponse.getBody();
		
		assertEquals(fromDb.getId(), lastFromDB.getId());
		System.out.println(pingResponse.getBody());
	}
	
	


}
