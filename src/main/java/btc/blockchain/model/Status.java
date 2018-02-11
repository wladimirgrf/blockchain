package btc.blockchain.model;

public enum Status {
	
	ERROR, 		//Cycle Exception
	INLINE, 		//In line for execution
	LOCK,		//Running cycle
	WAITING, 	//Waiting Blockchain confirmation	
	COMPLETED; 	// Process complete 
}
