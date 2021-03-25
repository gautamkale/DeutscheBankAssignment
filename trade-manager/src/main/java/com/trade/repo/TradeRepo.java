package com.trade.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trade.entity.TradeEntity;

@Repository
public interface TradeRepo extends CrudRepository<TradeEntity, Long>{
	public TradeEntity findByTradeId(String tradeId);
}
