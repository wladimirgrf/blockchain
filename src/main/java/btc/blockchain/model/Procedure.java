package btc.blockchain.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Procedure implements Serializable{

	private static final long serialVersionUID = -5121900911525738966L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)	
	private Long id;
	
//	private Long amount;
//	
//	private Long fee;
//	
//	private String txHash;
//	
//	private String guid;
//	
//	private String password;
//	
//	private String toAddress;
//	
//	private String fromAddress;
//	
//	private Status status;
//	
//	private String requestIP;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
    public String toString() {
        return String.format("btc.blockchain.model.Procedure#%s", id);
    }
}
