package com.marnat.sitb.service;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.misc.HighFreqTerms;
import org.apache.lucene.misc.TermStats;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.store.FSDirectory;

import com.marnat.sitb.dto.TfIdfDto;
import com.marnat.sitb.dto.TweetsDto;
import com.marnat.sitb.staticFiles.PathFiles;

/** TF-IDF: Term Frequency/Inverted Document Frequency
 *
 * Classically a search method that returns high quality search hits.
 * Core to the Lucene Framework.
 *
 * ATTENTION: The code returns hits, **Assuming** That you are running the code within the document corpus folder.
 * This way, we don't have to go through removing the path to the file you are accessing (much easier if for some
 * reason you have this in a nested folder sequence). Inspect the run configuration.
 *
 * */
public class TfIdfService {	

    public TfIdfService() {
		super();
	}
    
    public ArrayList<TfIdfDto> doTfIdf(ArrayList<TweetsDto> tweetsDto){
    	DataHelperService dhs = new DataHelperService();
		ArrayList<TfIdfDto> tfIdfDto = new ArrayList<TfIdfDto>();
		
		// 1. Make Directory to save Index Data, Query, and Textfile
		// 1.a Create Index Data Directory
		File indexDir = new File(PathFiles.indexPath);
		indexDir.mkdir();
		// 1.b Create Query Directory
		File queryDir = new File(PathFiles.queriesPath);
		queryDir.mkdir();
		// 1.c Create Text File Directory
		File textFileDir = new File(PathFiles.docsPath);
		textFileDir.mkdir();
		
		// 2. Write data to txt
    	dhs.doWriteData(tweetsDto);
		
		// 3. Do Indexing
		IndexerBasicService.doIndex();
		
    	// Calculate Tf Idf
    	tfIdfDto = this.calculateTfIdf(tfIdfDto);
		
		return tfIdfDto;
    }

    public ArrayList<TfIdfDto> calculateTfIdf(ArrayList<TfIdfDto> tfIdfDto) {
    	ClassicSimilarity cs = new ClassicSimilarity();
    	
    	float tf = 0;
    	float idf = 0;
    	float tfidf = 0;
    	
    	// Because in our case we always collect document become one, the idf will always be the same
    	idf = cs.idf(1, 1);
    	
    	String indexPath = PathFiles.indexPath;
    	
    	IndexReader reader = null;
        int numTerms = 100;
        TermStats[] stats = null;
		try {
			reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
			stats = HighFreqTerms.getHighFreqTerms(reader, numTerms, "contents", new HighFreqTerms.DocFreqComparator());
			for (TermStats termStats : stats) {
	            String termText = termStats.termtext.utf8ToString();
	            System.out.println(termText + " " + termStats.totalTermFreq);
	            
	            tf = cs.tf(termStats.totalTermFreq);
	            
	            tfidf = tf * idf;
	            
	            tfIdfDto.add(new TfIdfDto(termText, "Doc_1.txt", tfidf));
	        }
			
			reader.close();
		}			
		catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return tfIdfDto;
    }
    
//	public ArrayList<TfIdfDto> calculateTfIdf(ArrayList<TfIdfDto> tfIdfDto) {
//    	String indexPath = PathFiles.indexPath;
//    	String field = PathFiles.field;
//    	String queriesPath = PathFiles.queriesFile;
//    	String run_id = null;
//    	
//		try {
//	    	// Open a new IndexReader object, using index.
//	        IndexReader reader;
//			reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
//				        
//	        // Create query.txt indexed term to textfile query
//	        File myObj = new File(queriesPath);
//	        if (myObj.createNewFile()) {
//	            System.out.println("File created: " + myObj.getName());
//	        } 
//	        else {
//	            System.out.println("File already exists.");
//	        }
//	        
//	        // Write indexed term to query.txt
//	        FileWriter myWriter = new FileWriter(queriesPath);
//	
//	        // TEST: Get ALL the terms of an index.
//	        LuceneDictionary ld = new LuceneDictionary( reader, "contents" );
//	        BytesRefIterator iterator = ld.getEntryIterator();
//	        BytesRef byteRef = null;
//	        int total_terms_indexed = 0;
//	        while ( ( byteRef = iterator.next() ) != null )
//	        {
//	            String term = byteRef.utf8ToString();
//	            myWriter.write(total_terms_indexed + 1 + ":" + term + "\n");
//	            
//	            System.out.println(term);
//	            total_terms_indexed ++;
//	        }
//	        System.out.println(total_terms_indexed);
//	        myWriter.close();
//	
//	        // Open a new IndexSearcher object.
//	        // As a baseline, this works - Why?
//	        // IndexSearcher implements TF-IDF internally.
//	
//	        // change the similarity to "classic similarity" - defined as tf-idf.
//	        IndexSearcher searcher = new IndexSearcher(reader);
//	        searcher.setSimilarity(new ClassicSimilarity());
//	
//	        Analyzer analyzer = new StandardAnalyzer();
//	
//	        BufferedReader queryReader = null;
//	        if (queriesPath != null) {
//	            // Open the path to our queries
//	            queryReader = Files.newBufferedReader(Paths.get(queriesPath), StandardCharsets.UTF_8);
//	        }
//	
//	        QueryParser parser = new QueryParser(field, analyzer);
//	        while (true) {
//	
//	            // May lead to null pointer exception - consider cleaning up
//	            String line = queryReader.readLine();
//	
//	            // EOF
//	            if (line == null) {
//	                break;
//	            }
//	
//	            // Processing the inputs
//	            // ^regex splits at the first instance of ":"
//	
//	            String[] parts = line.split(":");
//	
//	            // Assign the first part to qN, second part to stringOfQueries
//	            QueryStringService test = new QueryStringService(parts[0], parts[1]);
//	
//	            // trim the stringOfQueries of any whitespace.
//	            test.stringOfQueries = test.stringOfQueries.trim();
//	
//	            // If we have an instance where there's no query provided...
//	            if (test.stringOfQueries.length() == 0) {
//	                break;
//	            }
//	
//	            // Build a Lucene query.
//	            Query query = parser.parse(test.stringOfQueries);
//	
//	            // Output the search entries.
//	            tfIdfDto = HelperMethodsService.doPagingSearch(searcher, query, test.qN, run_id, tfIdfDto);
//	        }
//	        reader.close();
//		} 
//		catch (IOException e) {
//			e.printStackTrace();
//		} 
//		catch (ParseException e) {
//			e.printStackTrace();
//		}
//		
//		return tfIdfDto;
//    }
}