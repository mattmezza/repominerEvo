package it.unisa.sesa.repominer.db.entities;

import net.sf.jeasyorm.annotation.Column;
import net.sf.jeasyorm.annotation.Table;

@Table(name = "methods")
public class Method {

	@Column(name = "id")
	private Integer id;
	@Column(name = "line_number")
	private Integer lineNumber;
	@Column(name = "is_constructor")
	private String isConstructor;
	@Column(name = "belonging_type")
	private Integer belongingTypeId;
	@Column(name = "return_type")
	private Integer returnTypeId;

	public Method(Integer id, Integer lineNumber, String isConstructor,
			Integer belongingTypeId, Integer returnTypeId) {
		this.id = id;
		this.lineNumber = lineNumber;
		this.isConstructor = isConstructor;
		this.belongingTypeId = belongingTypeId;
		this.returnTypeId = returnTypeId;
	}

	public Method() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getIsConstructor() {
		return isConstructor;
	}

	public void setIsConstructor(String isConstructor) {
		this.isConstructor = isConstructor;
	}

	public Integer getBelongingTypeId() {
		return belongingTypeId;
	}

	public void setBelongingTypeId(Integer belongingTypeId) {
		this.belongingTypeId = belongingTypeId;
	}

	public Integer getReturnTypeId() {
		return returnTypeId;
	}

	public void setReturnTypeId(Integer returnTypeId) {
		this.returnTypeId = returnTypeId;
	}

	@Override
	public String toString() {
		return "Method [id=" + id + ", lineNumber=" + lineNumber
				+ ", isConstructor=" + isConstructor + ", belongingTypeId="
				+ belongingTypeId + ", returnTypeId=" + returnTypeId + "]";
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
		Method other = (Method) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
