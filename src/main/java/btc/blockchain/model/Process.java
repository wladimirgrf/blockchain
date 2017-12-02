package btc.blockchain.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Process implements Serializable{

	private static final long serialVersionUID = -48691167816809896L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)	
	private Long id;
	
	private String txHash;
	
	private int confirmations;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
    public String getTxHash() {
		return txHash;
	}

	public void setTxHash(String txHash) {
		this.txHash = txHash;
	}

	public int getConfirmations() {
		return confirmations;
	}

	public void setConfirmations(int confirmations) {
		this.confirmations = confirmations;
	}

	@Override
    public String toString() {
        return String.format("btc.blockchain.model.Process#%s", id);
    }
}
