package com.aksh.marketlog.res;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.aksh.marketlog.dto.Execution;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles("ut")
public class TradeControllerTest {


	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void pingtest() {
		ResponseEntity<String> pingResponse=restTemplate.getForEntity("/executions/ping", String.class);
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
		
		ResponseEntity<Execution> res=restTemplate.postForEntity("/executions/save", request, Execution.class);
		assertEquals(HttpStatus.OK,res.getStatusCode());

		Execution fromDb=res.getBody();
		ResponseEntity<Execution> pingResponse=restTemplate.getForEntity("/executions/last",Execution.class);
		assertEquals(HttpStatus.OK,pingResponse.getStatusCode());
		Execution lastFromDB=pingResponse.getBody();
		
		assertEquals(fromDb.getId(), lastFromDB.getId());
		System.out.println(pingResponse.getBody());
	}
	
	


}
