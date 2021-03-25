package com.trade.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.trade.Exception.InvalidTradeException;
import com.trade.entity.TradeEntity;
import com.trade.model.Trade;
import com.trade.model.TradeResponse;
import com.trade.service.TradeService;

@Controller
public class TradeManager {

	@Autowired
	private TradeService tradeService;
	
	private static final Logger log = LoggerFactory.getLogger(TradeManager.class);
	public TradeResponse  stroreTrad(Trade t){
		TradeResponse res= new TradeResponse();
		if(tradeService.isTradeHasLowerVersion(t)){
			log.error("Trade has lower version");
			throw new InvalidTradeException("fail","Trade has lower version");
		}
		
		if(tradeService.isTradeHasLessMaturityDateThanToday(t)){
			log.error("Trade has less maturity date than today");
			throw new InvalidTradeException("fail","Trade has less maturity date than today");
		}
		try{
			TradeEntity existingTrade=tradeService.getTrade(t);
			if(existingTrade!=null ){
				if(existingTrade.getVersion()<t.getVersion())
					res.setEntityTrade(tradeService.update(existingTrade,t));
			}else{
				res.setEntityTrade(tradeService.add(t));
			}
			log.info("Trade stored successfully");
			res.setStatus("successfull");
		}catch (Exception e) { 
			throw new InvalidTradeException("fail",e.getMessage());
		}
		
		return res;
		
	}
}
