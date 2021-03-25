package com.trade.service;

import com.trade.entity.TradeEntity;
import com.trade.model.Trade;


public interface TradeService {

	boolean isTradeHasLowerVersion(Trade t);

	boolean isTradeHasLessMaturityDateThanToday(Trade t);
	
	TradeEntity getTrade(Trade t);

	TradeEntity add(Trade t);
	
	TradeEntity update(TradeEntity existingTrade, Trade t);
	

}
