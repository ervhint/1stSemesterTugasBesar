package com.marnat.sitb.service;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.marnat.sitb.dto.SentimentDto;
import com.marnat.sitb.dto.TweetsDto;
import com.masasdani.sengon.SentimentAnalysis;
import com.masasdani.sengon.model.Document;
import com.masasdani.sengon.model.Language;
import com.masasdani.sengon.model.Sentiment;

import opennlp.tools.util.eval.FMeasure;

public class IndonesianSentimentAnalysisService {
	public DataHelperService dhs = new DataHelperService();
	
	public ArrayList<SentimentDto> doSentiments(ArrayList<TweetsDto> tweetsDto){
		ArrayList<SentimentDto> sentimentDtoList = new ArrayList<SentimentDto>();
		
		for(int i = 0; i < tweetsDto.size(); i++) {
			sentimentDtoList.add(this.doSentiment(tweetsDto.get(i).getId(), tweetsDto.get(i).getTweetText()));
		}
		
		return sentimentDtoList;
	}
	
	public SentimentDto doSentiment(BigDecimal id, String tweetSentence) {
		SentimentAnalysis sa = new SentimentAnalysis(Language.INDONESIA);
		String[] predictions = new String[1]; 
		String[] references = new String[1];
		String sentence = tweetSentence;
		String classification = tweetSentence;
		Document doc = sa.doSentimentAnalysis(sentence);
		System.out.println(doc.toSentimentDocument().toString());
		references[0] = classification;
		if(doc.getSentiment() == Sentiment.NEGATIVE){
			predictions[0] = "-1";
		}
		else if(doc.getSentiment() == Sentiment.POSITIVE){
			predictions[0] = "1";
		}
		else{
			predictions[0] = "0";
		}
		SentimentDto sentimentDto = new SentimentDto(id, doc.getSentiment().toString());
		FMeasure fMeasure = new FMeasure();
		fMeasure.updateScores(references, predictions);
		System.out.println(fMeasure);
		
		return sentimentDto;
	}
	
	public void doSentimentAndUpdate() {
		ArrayList<TweetsDto> td = dhs.doGetData();
		ArrayList<SentimentDto> sentimentDtoList = doSentiments(td);
		dhs.doUpdateData(sentimentDtoList);
	}
}
