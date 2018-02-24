package btc.blockchain.rpc.model;

import org.json.simple.JSONObject;


public class Transaction {

	private String toAddress;
	
	private String bip38Cipher;
	
	private int bip38Key;
	
	private long satoshiAmount;
	
	private long fee;
	

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
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
	
	@Override
	@SuppressWarnings("unchecked")
    public String toString() {
		JSONObject transaction = new JSONObject();
		transaction.put("toAddress", toAddress);
		transaction.put("bip38Cipher", bip38Cipher);
		transaction.put("bip38Key", bip38Key);
		transaction.put("satoshiAmount", satoshiAmount);
		transaction.put("fee", fee);
        
        return  transaction.toJSONString();
    }
}
