package it.unisa.sesa.repominer.db.entities;

import net.sf.jeasyorm.annotation.Column;
import net.sf.jeasyorm.annotation.Table;

@Table(name = "source_containers")
public class SourceContainer {

	private Integer id;
	@Column(name = "project")
	private Integer projectId;
	private Integer importId;
	private String name;

	public SourceContainer() {
	}

	public SourceContainer(Integer id, Integer projectId, Integer importId) {
		this.id = id;
		this.projectId = projectId;
		this.importId = importId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getImportId() {
		return importId;
	}

	public void setImportId(Integer importId) {
		this.importId = importId;
	}

	@Override
	public String toString() {
		return "SourceContainer [id=" + id + ", projectId=" + projectId
				+ ", importId=" + importId + ", name=" + name + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		SourceContainer other = (SourceContainer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
