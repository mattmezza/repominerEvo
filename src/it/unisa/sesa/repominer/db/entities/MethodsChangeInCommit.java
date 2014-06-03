package it.unisa.sesa.repominer.db.entities;

import net.sf.jeasyorm.annotation.Column;
import net.sf.jeasyorm.annotation.Table;

@Table(name = "methods_change_in_commit")
public class MethodsChangeInCommit {

	@Column(name = "id")
	private Integer id;
	@Column(name = "modified_method")
	private String modifiedMethod;
	@Column(name = "proprietary_file")
	private Integer proprietaryFileId;

	public MethodsChangeInCommit(Integer id, String modifiedMethod,
			Integer proprietaryFileId) {
		this.id = id;
		this.modifiedMethod = modifiedMethod;
		this.proprietaryFileId = proprietaryFileId;
	}

	public MethodsChangeInCommit() {
	}

	@Override
	public String toString() {
		return "MethodsChangeInCommit [id=" + id + ", modifiedMethod="
				+ modifiedMethod + ", proprietaryFileId=" + proprietaryFileId
				+ "]";
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
		MethodsChangeInCommit other = (MethodsChangeInCommit) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
