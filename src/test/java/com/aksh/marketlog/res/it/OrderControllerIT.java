package com.aksh.marketlog.res.it;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.aksh.marketlog.dto.Execution;
import com.aksh.marketlog.dto.ExecutionMessage;
import com.aksh.marketlog.dto.NewOrder;
import com.aksh.marketlog.dto.OrderStatus;
import com.aksh.marketlog.dto.OrderType;
public class OrderControllerIT {


	private RestTemplate restTemplate=new RestTemplate();
	private String serverUrl;

	@Before
	public void setup(){
		serverUrl=BaseIT.getServerUrl();
		System.out.println("In unit test server url:"+serverUrl);
	}

	@Test
	public void pingtest() {
		ResponseEntity<String> pingResponse=restTemplate.getForEntity(serverUrl+"/orders/ping", String.class);
		assertEquals(HttpStatus.OK,pingResponse.getStatusCode());
	}

	
	
	@Test
	public void orderLife() {
		int refId=(int)System.nanoTime();
		Date executionTime=new Date();
		NewOrder ord = createOrder(refId, executionTime);
		System.out.println("Order:"+ord);
		
		HttpEntity<NewOrder> request = new HttpEntity<>(ord);
		
		ResponseEntity<NewOrder> res=restTemplate.postForEntity(serverUrl+"/orders/save", request, NewOrder.class);
		assertEquals(HttpStatus.OK,res.getStatusCode());
		NewOrder fromDb=res.getBody();
		System.out.println("Order From DB:"+fromDb);
		
		Map< String, Object> temp=new HashMap<>();
		temp.put("refId", ord.getRefId());
		ResponseEntity<NewOrder> pingResponse=restTemplate.getForEntity(serverUrl+"/orders/byref?refId="+ord.getRefId(),NewOrder.class);
		fromDb=pingResponse.getBody();
		System.out.println("Get Response from db:"+fromDb);
		assertEquals(HttpStatus.OK,pingResponse.getStatusCode());
		assertEquals(refId,fromDb.getRefId());
		assertEquals(ord.getStock(),fromDb.getStock());
		assertEquals(ord.getQty(),fromDb.getQty());
		assertEquals(ord.getPrice(),fromDb.getPrice(),0.001f);
		
		
		
		ExecutionMessage exm=new ExecutionMessage();
		exm.setExectutionTime(new Date());
		exm.setPrice(11.0F);
		exm.setQty(6);
		ResponseEntity<Execution> execResponse=restTemplate.postForEntity(serverUrl+"/orders/execute?refId="+ord.getRefId(),exm,Execution.class);
		Execution execR=execResponse.getBody();
		System.out.println("Execution Response:"+execR);
		assertEquals(HttpStatus.OK,pingResponse.getStatusCode());
		assertEquals(refId,execR.getRefId());
		assertEquals(exm.getPrice(),execR.getPrice(),0.0001);
		assertEquals(exm.getQty(),execR.getQty());
		
	}	
	
	private NewOrder createOrder(int refId, Date executionTime) {
		NewOrder ord=new NewOrder();
		ord.setEntryTime(executionTime);
		ord.setStatus(OrderStatus.O);
		ord.setType(OrderType.B);
		ord.setPrice(10.0F);
		ord.setQty(10);
		ord.setStock("AAPL");
		ord.setRefId(refId);
		return ord;
	}


}
