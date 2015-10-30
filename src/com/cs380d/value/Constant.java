package value;

public class Constant {
		// The size of message FIFo
	    public static final int FIFOSIZE = 10000;
	    // The interleave between 2 server
	    public static final int INTERLEAVE = 10000;
	    // The role of server
	    public static final int REPLICA = 0;
	    public static final int ACCEPTOR = 1;
	    public static final int LEADER = 2;
	    
	    // Delimiter
	    public static final String DELIMITER=";";
	    public static final String PVALUEDELIMITER="&";
	    public static final String PVALUESETDELIMITER="^";
	    public static final String COMMANDDELIMITER="*";
	    public static final String BALLOTDELIMITER="%";

}
