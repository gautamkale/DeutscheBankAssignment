package com.trade.trademanager;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.trade.entity.TradeEntity;
import com.trade.model.Trade;
import com.trade.repo.TradeRepo;
import com.trade.service.TradeService;
import com.trade.service.imp.TradeServiceImp;


@RunWith(SpringRunner.class)
public class TradeServiceImpTest {
	
	@MockBean
	private TradeRepo tradeRepo;
	
	@Autowired
	private TradeService tradeService;

	
	private SimpleDateFormat formate =new SimpleDateFormat("dd/MM/yyyy");
	
	@TestConfiguration
    static class TradeServiceImplTestContextConfiguration {
        @Bean
        public TradeService TradeService() {
            return new TradeServiceImp();
        }
    }

	@Test
	public void testIsTradeHasLowerVersionTrue() {
		TradeEntity existingTrade=response();
		existingTrade.setVersion(3);
		Mockito.when(tradeRepo.findByTradeId(Mockito.any())).thenReturn(existingTrade);
		boolean flag=tradeService.isTradeHasLowerVersion(getTrade());
		assertTrue(flag);
	}
	@Test
	public void testIsTradeHasLowerVersionFalse() {
		TradeEntity existingTrade=response();
		existingTrade.setVersion(1);
		Mockito.when(tradeRepo.findByTradeId(Mockito.any())).thenReturn(existingTrade);
		boolean flag=tradeService.isTradeHasLowerVersion(getTrade());
		assertFalse(flag);
	}

	@Test
	public void testIsTradeHasLessMaturityDateThanTodayTrue() {
		boolean flag=tradeService.isTradeHasLessMaturityDateThanToday(getTrade());
		assertTrue(flag);
	}
	@Test
	public void testIsTradeHasLessMaturityDateThanTodayFalse() {
		Trade t= getTrade();
		t.setMaturityDate("22/04/2021");
		boolean flag=tradeService.isTradeHasLessMaturityDateThanToday(t);
		assertFalse(flag);
	}

	@Test
	public void testGetTrade() {
		Mockito.when(tradeRepo.findByTradeId(Mockito.any())).thenReturn(response());
		Trade tq1=getTrade();
		TradeEntity res=tradeService.getTrade(tq1);
		assertEquals(res.getTradeId(), tq1.getTradeId());
	}

	@Test
	public void testAdd() {
		Mockito.when(tradeRepo.save(Mockito.any())).thenReturn(response());
		Trade t1=getTrade();
		TradeEntity res=tradeService.add(t1);
		assertNotNull(res);
	}

	@Test
	public void testUpdate() {
		Mockito.when(tradeRepo.save(Mockito.any())).thenReturn(response());
		Trade t1=getTrade();
		TradeEntity t2=response();
		TradeEntity res=tradeService.update(t2,t1);
		assertNotNull(res);
	}
	
	private Trade getTrade() {
		Trade t= new Trade();
		t.setTradeId("T1");
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
		t.setTradeId("T1");
		t.setBookId("B1");
		t.setCounterPartyId("CP-1");
		t.setCreatedDate(formate.format(new Date()));
		t.setExpired("N");
		t.setMaturityDate("20/05/2020");
		t.setVersion(2);
		return t;
	}

}
