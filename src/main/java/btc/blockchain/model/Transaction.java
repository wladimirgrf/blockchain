package btc.blockchain.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.json.simple.JSONObject;


@Entity
public class Transaction implements Serializable{

	private static final long serialVersionUID = -5121900911525738966L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private Long id;
	
	private long satoshiAmount;
	
	private long fee;
	
	private String txHash;
	
	private String bip38Cipher;
	
	private int bip38Key;
	
	private String toAddress;
	
	private Status status;
	
	private String requestIP;
	
	private String note;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getSatoshiAmount() {
		return satoshiAmount;
	}

	public void setSatoshiAmount(long satoshiAmount) {
		this.satoshiAmount = satoshiAmount;
	}

	public long getFee() {
		return fee;
	}

	public void setFee(long fee) {
		this.fee = fee;
	}

	public String getTxHash() {
		return txHash;
	}

	public void setTxHash(String txHash) {
		this.txHash = txHash;
	}

	public String getBip38Cipher() {
		return bip38Cipher;
	}

	public void setBip38Cipher(String bip38Cipher) {
		this.bip38Cipher = bip38Cipher;
	}

	public int getBip38Key() {
		return bip38Key;
	}

	public void setBip38Key(int bip38Key) {
		this.bip38Key = bip38Key;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getRequestIP() {
		return requestIP;
	}

	public void setRequestIP(String requestIP) {
		this.requestIP = requestIP;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	
	@Override
	@SuppressWarnings("unchecked")
    public String toString() {
		JSONObject obj = new JSONObject();
		obj.put("txHash", txHash);
		obj.put("toAddress", toAddress);
        obj.put("satoshiAmount", satoshiAmount);
        obj.put("fee", fee);
        obj.put("status", status);
        
        return  obj.toJSONString();
    }
}
