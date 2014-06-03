package it.unisa.sesa.repominer.db.entities;

import java.util.Date;

import net.sf.jeasyorm.annotation.Column;

public class Issue {

	private Integer id;
	private String type;
	private String priority;
	private String status;
	private String resolution;
	private String affectedVersion;
	private String component;
	private String fixVersion;
	private String assignee;
	private String reporter;
	private Date updated;
	private Date closed;
	private Date created;
	@Column(name = "project")
	private Integer projectId;

	public Issue() {
	}

	public Issue(Integer id, String type, String priority, String status,
			String resolution, String affectedVersion, String component,
			String fixVersion, String assignee, String reporter, Date updated,
			Date closed, Date created, Integer projectId) {
		this.id = id;
		this.type = type;
		this.priority = priority;
		this.status = status;
		this.resolution = resolution;
		this.affectedVersion = affectedVersion;
		this.component = component;
		this.fixVersion = fixVersion;
		this.assignee = assignee;
		this.reporter = reporter;
		this.updated = updated;
		this.closed = closed;
		this.created = created;
		this.projectId = projectId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getAffectedVersion() {
		return affectedVersion;
	}

	public void setAffectedVersion(String affectedVersion) {
		this.affectedVersion = affectedVersion;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getFixVersion() {
		return fixVersion;
	}

	public void setFixVersion(String fixVersion) {
		this.fixVersion = fixVersion;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Date getClosed() {
		return closed;
	}

	public void setClosed(Date closed) {
		this.closed = closed;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	@Override
	public String toString() {
		return "Issue [id=" + id + ", type=" + type + ", priority=" + priority
				+ ", status=" + status + ", resolution=" + resolution
				+ ", affectedVersion=" + affectedVersion + ", component="
				+ component + ", fixVersion=" + fixVersion + ", assignee="
				+ assignee + ", reporter=" + reporter + ", updated=" + updated
				+ ", closed=" + closed + ", created=" + created
				+ ", projectId=" + projectId + "]";
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
		Issue other = (Issue) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
