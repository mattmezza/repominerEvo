package it.unisa.sesa.repominer.db.entities;

import net.sf.jeasyorm.annotation.Column;
import net.sf.jeasyorm.annotation.Table;

@Table(name = "metrics_method")
public class MetricMethod {

	@Column(name = "method")
	private Integer methodId;
	@Column(name = "metric")
	private Integer metricId;
	@Column(name = "value")
	private String value;

	public MetricMethod(Integer methodId, Integer metricId, String value) {
		this.methodId = methodId;
		this.metricId = metricId;
		this.value = value;
	}

	public MetricMethod() {
	}

	public Integer getMethodId() {
		return methodId;
	}

	public void setMethodId(Integer methodId) {
		this.methodId = methodId;
	}

	public Integer getMetricId() {
		return metricId;
	}

	public void setMetricId(Integer metricId) {
		this.metricId = metricId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "MetricMethod [methodId=" + methodId + ", metricId=" + metricId
				+ ", value=" + value + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((methodId == null) ? 0 : methodId.hashCode());
		result = prime * result
				+ ((metricId == null) ? 0 : metricId.hashCode());
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
		MetricMethod other = (MetricMethod) obj;
		if (methodId == null) {
			if (other.methodId != null)
				return false;
		} else if (!methodId.equals(other.methodId))
			return false;
		if (metricId == null) {
			if (other.metricId != null)
				return false;
		} else if (!metricId.equals(other.metricId))
			return false;
		return true;
	}

}
