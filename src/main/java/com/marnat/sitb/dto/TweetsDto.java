package com.marnat.sitb.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TweetsDto {
	
	@JsonProperty("id")
	private BigDecimal id;
	
	@JsonProperty("tweet_text")
	private String tweetText;

	public TweetsDto(BigDecimal id, String tweetText) {
		super();
		this.id = id;
		this.tweetText = tweetText;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getTweetText() {
		return tweetText;
	}

	public void setTweetText(String tweetText) {
		this.tweetText = tweetText;
	}
}
