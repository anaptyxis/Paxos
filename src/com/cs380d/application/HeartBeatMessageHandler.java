package application;

import java.util.Timer;
import java.util.TimerTask;

import value.Constant;

/*
 *  Heart beat message handler 
 *  handle heart beat message and decide when to select new leader 
 */
public class HeartBeatMessageHandler extends Thread {
	    public TimerTask task;
	    public Timer timer;
	    public Server server;
	    private boolean started = false;
	    public HeartBeatMessageHandler(Server s){
	      timer = new Timer();
	      server = s;
	    }

	    public void cancel () {
	      task.cancel();
	    }

	    /*
	     *  IF I receive heart beat message
	     *  just reset the timer
	     */
	    public void reset() {
	      setTimeout(Constant.TIMEOUTFORHEARTBEAT);
	    }

	    @Override
	    public void run(){
	      setTimeout(Constant.TIMEOUTFORHEARTBEAT*2);
	    }

	    /*
	     *  Time out action for heart beat message
	     *  if I do not receive message for delay
	     *  Then run leader election 
	     */
	    public void setTimeout(long delay){
	      if(started){
	    	  try {
	    		  	task.cancel();
	    		  	task = new TimerTask() {
	    		  		@Override
	    		  		public void run() {
	    		  			if(Constant.DEBUG){
	    		  				System.out.println("Time out action for heart beat message");
	    		  			}
	    		  			// TODO Auto-generated method stub
	    		  			server.leaderElection();
	    		  		}
	    		  	};
	    		  	timer.schedule(task, delay);
	    	  	} catch (IllegalStateException e) {
	    	  		//e.printStackTrace();
	    	  	}
	      }	  
	      else{
	    	   started  = true;  
	    	   try {
	    		  	task = new TimerTask() {
	    		  		@Override
	    		  		public void run() {
	    		  			// TODO Auto-generated method stub
	    		  			server.leaderElection();
	    		  		}
	    		  	};
	    		  	timer.schedule(task, delay);
	    	  	} catch (IllegalStateException e) {
	    	  		//e.printStackTrace();
	    	  	}
	      }
	    }
	}
	 
       
