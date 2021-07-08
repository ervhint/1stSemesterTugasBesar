package com.marnat.sitb.dto;

import java.math.BigDecimal;

public class SentimentDto {
	private BigDecimal id;
	private String sentiment;
	
	public SentimentDto(BigDecimal id, String sentiment) {
		super();
		this.id = id;
		this.sentiment = sentiment;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getSentiment() {
		return sentiment;
	}

	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}
}
