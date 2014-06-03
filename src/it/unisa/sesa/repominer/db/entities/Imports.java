package it.unisa.sesa.repominer.db.entities;

import net.sf.jeasyorm.annotation.Column;
import net.sf.jeasyorm.annotation.Table;

@Table(name = "imports")
public class Imports {

	@Column(name = "importer")
	private Integer importerId;
	@Column(name = "imported")
	private Integer importedId;

	public Imports() {
	}

	public Imports(Integer importerId, Integer importedId) {
		super();
		this.importerId = importerId;
		this.importedId = importedId;
	}

	public Integer getImporterId() {
		return importerId;
	}

	public void setImporterId(Integer importerId) {
		this.importerId = importerId;
	}

	public Integer getImportedId() {
		return importedId;
	}

	public void setImportedId(Integer importedId) {
		this.importedId = importedId;
	}

	@Override
	public String toString() {
		return "Imports [importerId=" + importerId + ", importedId="
				+ importedId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((importedId == null) ? 0 : importedId.hashCode());
		result = prime * result
				+ ((importerId == null) ? 0 : importerId.hashCode());
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
		Imports other = (Imports) obj;
		if (importedId == null) {
			if (other.importedId != null)
				return false;
		} else if (!importedId.equals(other.importedId))
			return false;
		if (importerId == null) {
			if (other.importerId != null)
				return false;
		} else if (!importerId.equals(other.importerId))
			return false;
		return true;
	}

}
