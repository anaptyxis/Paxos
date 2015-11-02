package message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.sun.corba.se.spi.orb.StringPair;

import value.Command;
import value.Constant;
import value.Pvalue;

/**
 * @author Tian Zhang
 */

public abstract class Message {

  public int src;
  public int dst = -1;



  /**
   * convert pvalue set to string
   * @param message string
   */
  public String PvalueSet2Str(HashSet<Pvalue> p) {
	  String result = null;
	  if(p == null){
		  return "nothing";
	  }
	  for(Pvalue tmp : p){
		  result+=tmp.toString();
		  result+=Constant.PVALUESETDELIMITER;
	  }
	  if(result !=null){
		  int length = result.length();
		  return result.substring(0,length-1);
	  }else{
		  return "nothing";
	  }
  }
  

  /**
   * convert string to pvalue set
   * @param message string
   */
  public HashSet<Pvalue> str2PvalueSet(String message){
	  HashSet<Pvalue> result = new HashSet<Pvalue>();
	  String tmp1 = null;
	  if(!message.equals("nothing")){
		  //System.out.println("Pvalue message is "+message);
		  String[] split = message.split(Constant.PVALUESETDELIMITER);
	  	  for(int i = 0 ; i < split.length; i++){
	  		  if(!split[i].equals(tmp1)){
	  			  //System.out.println("Pvalue is "+split[i]);
	  			  Pvalue tmp = new Pvalue(split[i]);
	  			  result.add(tmp);
	  		  }
	  	  }
	  	  return result;
  	  }else{
  		  return null;
  	  }
	
  }
  
  /**
   * convert dicision map to string
   * @param decision map
   */
   public String decMap2Str(Map<Integer, Command> dec){
	   String result = null;
	   for (Map.Entry<Integer, Command> entry : dec.entrySet()) {
		    Integer key = entry.getKey();
		    Command value = entry.getValue();
		    String tmpString = Integer.toString(key) + Constant.DECISIONDELIMITER+value.toString();
		    result = result + tmpString + Constant.DECISIONLISTDELIMITER;
		}
	   if(result !=null){
			  int length = result.length();
			  return result.substring(0,length-1);
		}else{
			  return "nothing";
		}
   }
   
   
   /**
    * convert String back to decision map
    * @param decision map
    */
    public Map<Integer, Command> str2DecMap(String message){
 	   Map<Integer, Command> result = new HashMap<Integer, Command>();
 	  if(!message.equals("nothing")){
		  //System.out.println("Pvalue message is "+message);
		  String[] split = message.split(Constant.DECISIONLISTDELIMITER);
	  	  for(int i = 0 ; i < split.length; i++){
	  		   String[] tmpSplit = split[i].split(Constant.DECISIONDELIMITER);
	  		   Integer k = Integer.parseInt(tmpSplit[0]);
	  		   Command value = new Command(tmpSplit[1]);
	  		   result.put(k, value);
	  	  }
	  	  
	  	  return result;
  	  }else{
  		  return null;
  	  }
	
 	   
 	   
    }
}

