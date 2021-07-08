package com.marnat.sitb.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.marnat.sitb.database.MySqlConnection;
import com.marnat.sitb.dto.SentimentDto;
import com.marnat.sitb.dto.TweetsDto;
import com.marnat.sitb.staticFiles.PathFiles;

public class DataHelperService {
	
	public DataHelperService() {
		super();
	}
	
	public void doUpdateData(ArrayList<SentimentDto> sentimentDtoList) {		
		try {
			Connection con = MySqlConnection.createConnection();
			
			String updateQuery = "update tweets set sentiment_analysis = ? where id = ?";
			PreparedStatement ps = con.prepareStatement(updateQuery);
			
			for(int i = 0; i < sentimentDtoList.size(); i++) {
				System.out.println("Make Query " + (i+1));
				ps.setString(1, sentimentDtoList.get(i).getSentiment().toString());
				ps.setBigDecimal(2, sentimentDtoList.get(i).getId());
				ps.addBatch();
			}
			
			if(sentimentDtoList.size() > 0) {
				System.out.println("Execute Update Query..."); 
				int numUpdates[] = ps.executeBatch();         
				for (int i = 0; i < numUpdates.length; i++) {            
				    if (numUpdates[i] == -2) {
				    	System.out.println("Execution " + i + ": unknown number of rows updated");
				    }
				    else {
				    	System.out.println("Execution " + i + "successful: " + numUpdates[i] + " rows updated");
				    }
				}
			}			
			
			con.close();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public ArrayList<TweetsDto> doGetData() {
		ArrayList<TweetsDto> td = new ArrayList<TweetsDto>();
		
		try {
			Connection con = MySqlConnection.createConnection();
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select id, tweet_text_clean from tweets where tweet_text_clean is not null and sentiment_analysis is null order by created_at asc limit 50");
			
			while(rs.next()) {				
				td.add(new TweetsDto(rs.getBigDecimal(1), rs.getString(2)));
			}
			con.close();			
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return td;
	}

	public void doWriteData(ArrayList<TweetsDto> tweetsDto) {
		String docsPath = PathFiles.docsPath;
		
		// Create query.txt indexed term to textfile query
        File myObj = new File(docsPath + File.separator + "Doc_1.txt");
        try {
			if (myObj.createNewFile()) {
			    System.out.println("File created: " + myObj.getName());
			} 
			else {
			    System.out.println("File already exists.");
			}
		} 
        catch (IOException e) {
			e.printStackTrace();
		}
        
        // Write the file into txt
        FileWriter myWriter;
		try {
			myWriter = new FileWriter(docsPath + File.separator + "Doc_1.txt");
	        for(int i = 0; i < tweetsDto.size(); i++) {
				myWriter.write(tweetsDto.get(i).getTweetText() + "\n");
			}
	        
	        myWriter.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
