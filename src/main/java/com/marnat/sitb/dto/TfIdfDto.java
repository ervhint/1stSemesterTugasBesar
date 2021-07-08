package com.marnat.sitb.dto;

import java.util.ArrayList;

public class TfIdfDto {
	private String term;
	private String document;
	private float score;
	
	public static ArrayList<TfIdfDto> tfIdfList = new ArrayList<TfIdfDto>();

	public TfIdfDto(String term, String document, float score) {
		super();
		this.term = term;
		this.document = document;
		this.score = score;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public static ArrayList<TfIdfDto> getTfIdfList() {
		return tfIdfList;
	}

	public static void setTfIdfList(ArrayList<TfIdfDto> tfIdfList) {
		TfIdfDto.tfIdfList = tfIdfList;
	}
}
