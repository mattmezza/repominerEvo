package it.unisa.sesa.repominer.db.entities;

import net.sf.jeasyorm.annotation.Column;
import net.sf.jeasyorm.annotation.Table;

@Table(name = "catched_exceptions")
public class CatchedException {

	@Column(name = "method")
	private Integer methodId;
	@Column(name = "exception_type")
	private Integer exceptionTypeId;

	public CatchedException() {

	}

	public CatchedException(Integer methodId, Integer exceptionTypeId) {
		this.methodId = methodId;
		this.exceptionTypeId = exceptionTypeId;
	}

	public Integer getMethodId() {
		return methodId;
	}

	public void setMethodId(Integer methodId) {
		this.methodId = methodId;
	}

	public Integer getExceptionTypeId() {
		return exceptionTypeId;
	}

	public void setExceptionTypeId(Integer exceptionTypeId) {
		this.exceptionTypeId = exceptionTypeId;
	}

	@Override
	public String toString() {
		return "CatchedException [methodId=" + methodId + ", exceptionTypeId="
				+ exceptionTypeId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((exceptionTypeId == null) ? 0 : exceptionTypeId.hashCode());
		result = prime * result
				+ ((methodId == null) ? 0 : methodId.hashCode());
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
		CatchedException other = (CatchedException) obj;
		if (exceptionTypeId == null) {
			if (other.exceptionTypeId != null)
				return false;
		} else if (!exceptionTypeId.equals(other.exceptionTypeId))
			return false;
		if (methodId == null) {
			if (other.methodId != null)
				return false;
		} else if (!methodId.equals(other.methodId))
			return false;
		return true;
	}

}
