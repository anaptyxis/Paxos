package message;

import java.util.HashSet;

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
}

