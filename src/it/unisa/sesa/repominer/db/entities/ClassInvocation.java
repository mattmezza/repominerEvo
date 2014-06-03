package it.unisa.sesa.repominer.db.entities;

import net.sf.jeasyorm.annotation.Column;
import net.sf.jeasyorm.annotation.Table;

@Table(name = "class_invocations")
public class ClassInvocation {

	@Column(name = "invoked_class")
	private Integer invokedClassId;
	@Column(name = "invoker_class")
	private Integer invokerClassId;

	public ClassInvocation() {

	}

	public ClassInvocation(Integer invokedClassId, Integer invokerClassId) {
		this.invokedClassId = invokedClassId;
		this.invokerClassId = invokerClassId;
	}

	public Integer getInvokedClassId() {
		return invokedClassId;
	}

	public void setInvokedClassId(Integer invokedClassId) {
		this.invokedClassId = invokedClassId;
	}

	public Integer getInvokerClassId() {
		return invokerClassId;
	}

	public void setInvokerClassId(Integer invokerClassId) {
		this.invokerClassId = invokerClassId;
	}

	@Override
	public String toString() {
		return "ClassInvocation [invokedClassId=" + invokedClassId
				+ ", invokerClassId=" + invokerClassId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((invokedClassId == null) ? 0 : invokedClassId.hashCode());
		result = prime * result
				+ ((invokerClassId == null) ? 0 : invokerClassId.hashCode());
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
		ClassInvocation other = (ClassInvocation) obj;
		if (invokedClassId == null) {
			if (other.invokedClassId != null)
				return false;
		} else if (!invokedClassId.equals(other.invokedClassId))
			return false;
		if (invokerClassId == null) {
			if (other.invokerClassId != null)
				return false;
		} else if (!invokerClassId.equals(other.invokerClassId))
			return false;
		return true;
	}

}
