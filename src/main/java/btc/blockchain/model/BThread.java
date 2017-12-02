package btc.blockchain.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class BThread implements Serializable {

	private static final long serialVersionUID = -7390718900293594142L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)	
	private Long id;
	
	private Long runed;
	
	private Long created;
	
	private Long updated;
	
	private Status status;
	
	private String requestIP;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Process process;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRuned() {
		return runed;
	}

	public void setRuned(Long runed) {
		this.runed = runed;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public Long getUpdated() {
		return updated;
	}

	public void setUpdated(Long updated) {
		this.updated = updated;
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

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}
}
