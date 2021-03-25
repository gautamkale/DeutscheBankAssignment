package com.trade.service.imp;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trade.entity.TradeEntity;
import com.trade.model.Trade;
import com.trade.repo.TradeRepo;
import com.trade.service.TradeService;

@Service
public class TradeServiceImp implements TradeService {

	@Autowired
	private TradeRepo tradeRepo;
	
	private SimpleDateFormat formate =new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	public boolean isTradeHasLowerVersion(Trade t) {
		TradeEntity existingTrade = getTrade(t);
		if(existingTrade!=null && existingTrade.getVersion()>(t.getVersion())){
			return true;
		}
		return false;
	}

	@Override
	public boolean isTradeHasLessMaturityDateThanToday(Trade t) {
		if(t!=null){
			return compareDate(stringToDateFormatter(t.getMaturityDate()),new Date());
		}
		return false;
	}

	public TradeEntity getTrade(Trade t) {
		TradeEntity existingTrade= tradeRepo.findByTradeId(t.getTradeId());
		return existingTrade;
	}

	private boolean compareDate(Date maturityDate, Date date) {
         Timestamp ts=new Timestamp(date.getTime()); 
         Timestamp maturityTs=new Timestamp(maturityDate.getTime()); 
         if(ts.after(maturityTs)){
        	 return true;
         }
		return false;
	}

	@Override
	public TradeEntity add(Trade t) {
		
		return tradeRepo.save(build(t));
	}

	private TradeEntity build(Trade t) {
		TradeEntity newTrade= new TradeEntity();
		newTrade.setBookId(t.getBookId());
		newTrade.setCounterPartyId(t.getCounterPartyId());
		newTrade.setCreatedDate(dateToStringFormatter(new Date()));
		newTrade.setExpired(t.isExpired());
		newTrade.setMaturityDate(t.getMaturityDate());
		newTrade.setTradeId(t.getTradeId());
		newTrade.setVersion(t.getVersion());
		return newTrade;
	}

	private void updateTrade(TradeEntity existingTrade, Trade t) {
		existingTrade.setBookId(t.getBookId());
		existingTrade.setCounterPartyId(t.getCounterPartyId());
		existingTrade.setCreatedDate(dateToStringFormatter(new Date()));
		existingTrade.setMaturityDate(t.getMaturityDate());
		existingTrade.setExpired(isExpired(stringToDateFormatter(t.getMaturityDate()),new Date()));
	}

	private String dateToStringFormatter(Date now){
		
				return formate.format(now);
	}
	private Date stringToDateFormatter(String date){
			Date d=null;
		try {
			d= formate.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}
	private String isExpired(Date maturityDate, Date date) {
		 Timestamp ts=new Timestamp(date.getTime()); 
         Timestamp maturityTs=new Timestamp(maturityDate.getTime()); 
         if(maturityTs.after(ts)){
        	 return "N";
         }
		return "Y";
	}

	@Override
	public TradeEntity update(TradeEntity existingTrade, Trade t) {
		updateTrade(existingTrade,t);
		
		return tradeRepo.save(existingTrade);
	}

}
