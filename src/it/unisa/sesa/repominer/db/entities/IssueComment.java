package it.unisa.sesa.repominer.db.entities;

import java.util.Date;

import net.sf.jeasyorm.annotation.Column;

public class IssueComment {

	private Integer id;
	private String devId;
	private String devMail;
	private String text;
	private Date date;
	@Column(name = "belonging_issue")
	private Integer belongingIssueId;

	public IssueComment() {
	}

	public IssueComment(Integer id, String devId, String devMail, String text,
			Date date, Integer belongingIssueId) {
		this.id = id;
		this.devId = devId;
		this.devMail = devMail;
		this.text = text;
		this.date = date;
		this.belongingIssueId = belongingIssueId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public String getDevMail() {
		return devMail;
	}

	public void setDevMail(String devMail) {
		this.devMail = devMail;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getBelongingIssueId() {
		return belongingIssueId;
	}

	public void setBelongingIssueId(Integer belongingIssueId) {
		this.belongingIssueId = belongingIssueId;
	}

	@Override
	public String toString() {
		return "IssueComment [id=" + id + ", devId=" + devId + ", devMail="
				+ devMail + ", text=" + text + ", date=" + date
				+ ", belongingIssueId=" + belongingIssueId + "]";
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
		IssueComment other = (IssueComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
