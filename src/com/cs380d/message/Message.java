package message;

import java.util.HashSet;
import java.util.Set;

import value.Constant;
import value.Pvalue;

/**
 * @author Tian Zhang
 */

public abstract class Message {

  public int src;
  public int dst;


  /**
   * print the message
   * @param 
   */
  public String print () {
    String rst = "\n" + src + " -> " + dst + "\t" + "\n";
    return rst;
  }
  

  /**
   * convert pvalue set to string
   * @param message string
   */
  public String PvalueSet2Str(HashSet<Pvalue> p) {
	  String result = null;
	  for(Pvalue tmp : p){
		  result+=tmp.toString();
		  result+=Constant.PVALUESETDELIMITER;
	  }
	  if(result !=null){
		  int length = result.length();
		  return result.substring(0,length-1);
	  }else{
		  return null;
	  }
  }
  

  /**
   * convert string to pvalue set
   * @param message string
   */
  public HashSet<Pvalue> str2PvalueSet(String message){
	  HashSet<Pvalue> result = new HashSet<Pvalue>();
	  String[] split = message.split(Constant.PVALUESETDELIMITER);
	  for(int i = 0 ; i < split.length; i++){
		  Pvalue tmp = new Pvalue(split[i]);
		  result.add(tmp);
	  }
	  return result;
	
  }
}

