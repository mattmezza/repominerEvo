package it.unisa.sesa.repominer.db.entities;

import net.sf.jeasyorm.annotation.Column;
import net.sf.jeasyorm.annotation.Table;

@Table(name = "method_invocations")
public class Method_invocation {

	@Column(name = "invoker_method")
	private Integer invokerMethodId;
	@Column(name = "invoked_method")
	private Integer invokedMethodId;

	public Method_invocation(Integer invokerMethodId, Integer invokedMethodId) {
		this.invokerMethodId = invokerMethodId;
		this.invokedMethodId = invokedMethodId;
	}

	public Method_invocation() {
	}

	public Integer getInvokerMethodId() {
		return invokerMethodId;
	}

	public void setInvokerMethodId(Integer invokerMethodId) {
		this.invokerMethodId = invokerMethodId;
	}

	public Integer getInvokedMethodId() {
		return invokedMethodId;
	}

	public void setInvokedMethodId(Integer invokedMethodId) {
		this.invokedMethodId = invokedMethodId;
	}

	@Override
	public String toString() {
		return "Method_invocation [invokerMethodId=" + invokerMethodId
				+ ", invokedMethodId=" + invokedMethodId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((invokedMethodId == null) ? 0 : invokedMethodId.hashCode());
		result = prime * result
				+ ((invokerMethodId == null) ? 0 : invokerMethodId.hashCode());
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
		Method_invocation other = (Method_invocation) obj;
		if (invokedMethodId == null) {
			if (other.invokedMethodId != null)
				return false;
		} else if (!invokedMethodId.equals(other.invokedMethodId))
			return false;
		if (invokerMethodId == null) {
			if (other.invokerMethodId != null)
				return false;
		} else if (!invokerMethodId.equals(other.invokerMethodId))
			return false;
		return true;
	}

}
