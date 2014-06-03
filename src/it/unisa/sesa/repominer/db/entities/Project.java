package it.unisa.sesa.repominer.db.entities;

import net.sf.jeasyorm.annotation.Column;
import net.sf.jeasyorm.annotation.Table;

@Table(name = "projects")
public class Project {

	@Column(name = "id")
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "versioning_url")
	private String versioningUrl;
	@Column(name = "bugtracker_url")
	private String bugtrackerUrl;

	public Project(Integer id, String name, String versioningUrl,
			String bugtrackerUrl) {
		this.id = id;
		this.name = name;
		this.versioningUrl = versioningUrl;
		this.bugtrackerUrl = bugtrackerUrl;
	}

	public Project() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersioningUrl() {
		return versioningUrl;
	}

	public void setVersioningUrl(String versioningUrl) {
		this.versioningUrl = versioningUrl;
	}

	public String getBugtrackerUrl() {
		return bugtrackerUrl;
	}

	public void setBugtrackerUrl(String bugtrackerUrl) {
		this.bugtrackerUrl = bugtrackerUrl;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", versioningUrl="
				+ versioningUrl + ", bugtrackerUrl=" + bugtrackerUrl + "]";
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
		Project other = (Project) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
