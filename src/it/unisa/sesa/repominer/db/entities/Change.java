package it.unisa.sesa.repominer.db.entities;

import java.util.Date;

import net.sf.jeasyorm.annotation.Column;
import net.sf.jeasyorm.annotation.Table;

@Table(name = "changes")
public class Change {

	private Integer id;
	private String hash;
	private Date commitDate;
	private String devMail;
	private String devId;
	private String message;

	@Column(name = "project")
	private Integer projectId;

	public Change() {

	}

	public Change(Integer id, String hash, Date commitDate, String devMail,
			String devId, String message, Integer projectId) {
		super();
		this.id = id;
		this.hash = hash;
		this.commitDate = commitDate;
		this.devMail = devMail;
		this.devId = devId;
		this.message = message;
		this.projectId = projectId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Date getCommitDate() {
		return commitDate;
	}

	public void setCommitDate(Date commitDate) {
		this.commitDate = commitDate;
	}

	public String getDevMail() {
		return devMail;
	}

	public void setDevMail(String devMail) {
		this.devMail = devMail;
	}

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	@Override
	public String toString() {
		return "Change [id=" + id + ", hash=" + hash + ", commitDate="
				+ commitDate + ", devMail=" + devMail + ", devId=" + devId
				+ ", message=" + message + ", projectId=" + projectId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Change other = (Change) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
