package com.trade.model;

import com.trade.entity.TradeEntity;

public class TradeResponse {

	private String status;
	private TradeEntity tradeEntity;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public TradeEntity getEntityTrade() {
		return tradeEntity;
	}
	public void setEntityTrade(TradeEntity tradeEntity) {
		this.tradeEntity = tradeEntity;
	}
	
}
