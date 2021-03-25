package com.trade.trademanager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.trade.TradeManagerApplication;
import com.trade.Exception.InvalidTradeException;
import com.trade.controller.TradeManager;
import com.trade.entity.TradeEntity;
import com.trade.model.Trade;
import com.trade.model.TradeResponse;
import com.trade.repo.TradeRepo;
import com.trade.service.TradeService;
import com.trade.service.imp.TradeServiceImp;


@RunWith(SpringRunner.class)
public class TradeManagerTests {

	@Autowired
	private TradeManager tradeManager;
	
	@MockBean
	private TradeService tradeService;

	
	private SimpleDateFormat formate =new SimpleDateFormat("dd/MM/yyyy");
	
	@TestConfiguration
    static class TradeServiceImplTestContextConfiguration {
        @Bean
        public TradeManager employeeService() {
            return new TradeManager();
        }
    }
	@Test
	public void testStoreSuccessfullAdd() {
		Trade t = getTrade();
		Mockito.when(tradeService.isTradeHasLessMaturityDateThanToday(Mockito.any())).thenReturn(false);
		TradeResponse res=tradeManager.stroreTrad(t);
		
		assertTrue(res.getStatus().equals("successfull"));
	}
	@Test
	public void testStoreSuccessfullUpdate() {
		Trade t = getTrade();
		t.setVersion(2);
		Mockito.when(tradeService.isTradeHasLessMaturityDateThanToday(Mockito.any())).thenReturn(true);
		TradeEntity existingTrade=response();
		existingTrade.setVersion(1);
		Mockito.when(tradeService.getTrade(Mockito.any())).thenReturn(existingTrade);
		Mockito.when(tradeService.isTradeHasLowerVersion(Mockito.any())).thenReturn(false);
		Mockito.when(tradeService.isTradeHasLessMaturityDateThanToday(Mockito.any())).thenReturn(false);
		
		Mockito.when(tradeService.update(Mockito.any(),Mockito.any())).thenReturn(response());
		TradeResponse res=tradeManager.stroreTrad(t);
		
		assertTrue(res.getStatus().equals("successfull"));
	}
	@Test
	public void testStoreTradeHasLessMaturityDateThanToday() {
		Trade t = getTrade();
		t.setVersion(2);
		Mockito.when(tradeService.isTradeHasLessMaturityDateThanToday(Mockito.any())).thenReturn(true);
		try{
		tradeManager.stroreTrad(t);
		}catch(InvalidTradeException e){
			assertTrue(e.getDescription().equals("Trade has less maturity date than today"));
		}
		
	}
	@Test
	public void testStoreTradeHasLowerVersion() {
		Trade t = getTrade();
		t.setVersion(2);
		Mockito.when(tradeService.isTradeHasLowerVersion(Mockito.any())).thenReturn(true);
		try{
			tradeManager.stroreTrad(t);
		}catch(InvalidTradeException e){
			assertTrue(e.getDescription().equals("Trade has lower version"));
		}
		
	}

	private Trade getTrade() {
		Trade t= new Trade();
		t.setBookId("B1");
		t.setCounterPartyId("CP-1");
		t.setCreatedDate(formate.format(new Date()));
		t.setExpired("N");
		t.setMaturityDate("20/05/2020");
		t.setVersion(2);
		return t;
	}

	private TradeEntity response() {
		TradeEntity t= new TradeEntity();
		t.setBookId("B1");
		t.setCounterPartyId("CP-1");
		t.setCreatedDate(formate.format(new Date()));
		t.setExpired("N");
		t.setMaturityDate("20/05/2020");
		t.setVersion(2);
		return t;
	}

}
