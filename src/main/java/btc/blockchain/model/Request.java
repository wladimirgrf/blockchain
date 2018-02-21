package btc.blockchain.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.json.simple.JSONObject;

import btc.blockchain.rpc.model.Method;


@Entity
public class Request implements Serializable{

	private static final long serialVersionUID = -5121900911525738966L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private Long id;
	
	private String txId;
	
	private String requestIP;
	
	private JSONObject properties;
	
	private JSONObject result;
	
	private Status status;
	
	private Method method;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}
	
	public String getRequestIP() {
		return requestIP;
	}

	public void setRequestIP(String requestIP) {
		this.requestIP = requestIP;
	}
	
	public JSONObject getProperties() {
		return properties;
	}

	public void setProperties(JSONObject properties) {
		this.properties = properties;
	}

	public JSONObject getResult() {
		return result;
	}

	public void setResult(JSONObject result) {
		this.result = result;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	@Override
	@SuppressWarnings("unchecked")
    public String toString() {
		JSONObject request = new JSONObject();
		request.put("txId", txId);
		request.put("status", status);
		request.put("result", result);
        
        return  request.toJSONString();
    }
}
