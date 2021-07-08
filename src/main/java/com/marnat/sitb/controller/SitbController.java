package com.marnat.sitb.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marnat.sitb.dto.SentimentDto;
import com.marnat.sitb.dto.TfIdfDto;
import com.marnat.sitb.dto.TweetsDto;
import com.marnat.sitb.service.IndonesianSentimentAnalysisService;
import com.marnat.sitb.service.TfIdfService;

@RestController
public class SitbController {
	private TfIdfService tis = new TfIdfService();
	private IndonesianSentimentAnalysisService isas = new IndonesianSentimentAnalysisService();
	
	@RequestMapping(value="/doTfIdf", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<TfIdfDto> doTfIdf(@RequestBody ArrayList<TweetsDto> tweetsDto) throws Exception {		
		System.out.println("Array Tweets Data: " + tweetsDto.size());
		
		return tis.doTfIdf(tweetsDto);
    }
	
	
	@RequestMapping(value="/doSentiment", method = RequestMethod.POST)
    public SentimentDto doSentiment(@RequestBody Map<String,Object> body) throws Exception {
		BigDecimal id = new BigDecimal(body.get("id").toString());
		String tweetSentence = body.get("tweet_text").toString();
    	return isas.doSentiment(id, tweetSentence);
    }
	
	@RequestMapping(value="/doSentiments", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<SentimentDto> doSentiments(@RequestBody ArrayList<TweetsDto> tweetsDto) throws Exception {
		System.out.println("Array Tweets Data: " + tweetsDto.size());

    	return isas.doSentiments(tweetsDto);
    }
	
	@RequestMapping(value="/doSentimentAndUpdate", method = RequestMethod.POST)
    public void doSentimentAndUpdate() throws Exception {
    	isas.doSentimentAndUpdate();
    }
}
