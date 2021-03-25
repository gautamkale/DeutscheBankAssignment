package com.trade.Exception;

public class InvalidTradeException extends RuntimeException  {

	private String status;

	private String description;
	public InvalidTradeException() {}
	
	public InvalidTradeException(String status, String description) {
		super();
		this.status = status;
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
