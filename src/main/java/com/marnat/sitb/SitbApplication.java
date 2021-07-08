package com.marnat.sitb;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.marnat.sitb.controller.SitbController;

@SpringBootApplication
public class SitbApplication {
	public long delay = 60 * 1000; // delay in milliseconds
	public LoopTask task = new LoopTask();
	public Timer timer = new Timer("TaskName");
	public SitbController sc = new SitbController();

	public SitbApplication() {
		super();
	}

	public static void main(String[] args) {
		SpringApplication.run(SitbApplication.class, args);
		
		SitbApplication sa = new SitbApplication();
		sa.start();
	}

	public void start() {
	    this.timer.cancel();
	    timer = new Timer("TaskName");
	    Date executionDate = new Date(); // no params = now
	    timer.scheduleAtFixedRate(task, executionDate, delay);
    }
	
	private class LoopTask extends TimerTask {
        public void run() {
            System.out.println("Applications for sentiment runs every 1 minutes");
            try {
				sc.doSentimentAndUpdate();
			} 
            catch (Exception e) {
				e.printStackTrace();
			}
        }
    }
}
