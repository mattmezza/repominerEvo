package it.unisa.sesa.repominer.db.entities;

import java.sql.Date;

import net.sf.jeasyorm.annotation.Column;

public class IssueAttachment {

	private Integer id;
	private Date date;
	private String name;
	@Column(name = "belonging_issue")
	private Integer belongingIssueId;

	public IssueAttachment() {

	}

	public IssueAttachment(Integer id, Date date, String name,
			Integer belongingIssueId) {
		this.id = id;
		this.date = date;
		this.name = name;
		this.belongingIssueId = belongingIssueId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBelongingIssueId() {
		return belongingIssueId;
	}

	public void setBelongingIssueId(Integer belongingIssueId) {
		this.belongingIssueId = belongingIssueId;
	}

	@Override
	public String toString() {
		return "IssueAttachment [id=" + id + ", date=" + date + ", name="
				+ name + ", belongingIssueId=" + belongingIssueId + "]";
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
		IssueAttachment other = (IssueAttachment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
