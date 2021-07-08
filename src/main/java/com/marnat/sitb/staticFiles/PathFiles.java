package com.marnat.sitb.staticFiles;

import java.io.File;

public class PathFiles {
	public static String indexPath = System.getProperty("user.dir") + File.separator + "index";
	public static String docsPath = System.getProperty("user.dir") + File.separator + "textfile";
	public static String queriesPath = System.getProperty("user.dir") + File.separator + "query";
	public static String queriesFile = System.getProperty("user.dir") + File.separator + "query" + File.separator + "query.txt";
	public static String field = "contents";
}
