package com.marnat.sitb.service;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import com.marnat.sitb.dto.TfIdfDto;

/** HelperMethods:
 *
 * Core network of commonly used methods.
 * */
public class HelperMethodsService {

    /**
     * This returns a String with the relevance judgements for a single query.
     *
     * @param searcher: searcher Object to compute our search
     * @param query: actual name of the query for the procedure to search
     * @param runCount: number of the current query
     * @param runName: name of the current run
     */

    public static ArrayList<TfIdfDto> doPagingSearch(IndexSearcher searcher, Query query, String runCount, String runName, ArrayList<TfIdfDto> tfIdfDto) throws IOException {
    		
    	// Collect AT MOST, the top 1000 hits - roughly the same as the worst case scenario for relevance_judgements_for_CW09
        TopDocs results = searcher.search(query, 1000);
        // Put the results in "hits" variable
        ScoreDoc[] hits = results.scoreDocs;

        int numTotalHits = Math.toIntExact(results.totalHits.value);
        // This will get ALL the relevant documents possible.
        if (numTotalHits > 0){
            hits = searcher.search(query, numTotalHits).scoreDocs;
        }
        int start = 0;

        for (int i = start; i < numTotalHits; i++) {

            Document doc = searcher.doc(hits[i].doc);
            String path = doc.get("path");
            float score = hits[i].score;

            if (path != null) {
            	
            	String queries[] = query.toString().split(":");
                //fr.write(runCount + " " + "Q0 " + " " +  parts[1] + " " + (i+1) + " " + "4999" + " " + runName + "\n");
                // Since the above is NOT working, when running this file, pipe the output to a file called "relevance_judgements1.txt"
                System.out.print((queries[1] + " Doc_1.txt " + score + "\n"));
                tfIdfDto.add(new TfIdfDto(queries[1], "Doc_1.txt", score));
            }
        }
        
        return tfIdfDto;
    }


}
