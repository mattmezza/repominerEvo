package it.unisa.sesa.repominer.db.entities;

import net.sf.jeasyorm.annotation.Column;
import net.sf.jeasyorm.annotation.Table;

@Table(name = "changes_for_commit")
public class ChangeForCommit {

	@Column(name = "id")
	private Integer id;
	@Column(name = "change_hash")
	private Integer changeHashId;
	@Column(name = "modified_file")
	String modifiedFile;

	public ChangeForCommit() {
	}

	public ChangeForCommit(Integer id, Integer changeHash, String modifiedFile) {
		this.id = id;
		this.changeHashId = changeHash;
		this.modifiedFile = modifiedFile;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getChangeHash() {
		return changeHashId;
	}

	public void setChangeHash(Integer changeHash) {
		this.changeHashId = changeHash;
	}

	public String getModifiedFile() {
		return modifiedFile;
	}

	public void setModifiedFile(String modifiedFile) {
		this.modifiedFile = modifiedFile;
	}

	@Override
	public String toString() {
		return "Change_for_commit [id=" + id + ", changeHashId=" + changeHashId
				+ ", modifiedFile=" + modifiedFile + "]";
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
		ChangeForCommit other = (ChangeForCommit) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
