package it.unisa.sesa.repominer.db.entities;

import net.sf.jeasyorm.annotation.Column;
import net.sf.jeasyorm.annotation.Table;

@Table(name = "package_metrics")
public class PackageMetric extends Metric {
	@Column(name = "source_container")
	private Integer packageId;
	@Column(name = "metric")
	private Integer metricId;
	private Double value;

	public PackageMetric() {

	}

	public PackageMetric(Integer packageId, Integer metricId, Double value) {
		this.packageId = packageId;
		this.metricId = metricId;
		this.value = value;
	}

	public Integer getPackageId() {
		return packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

	public Integer getMetricId() {
		return metricId;
	}

	public void setMetricId(Integer metricId) {
		this.metricId = metricId;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "PackageMetric [packageId=" + packageId + ", metricId="
				+ metricId + ", value=" + value + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((metricId == null) ? 0 : metricId.hashCode());
		result = prime * result
				+ ((packageId == null) ? 0 : packageId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PackageMetric other = (PackageMetric) obj;
		if (metricId == null) {
			if (other.metricId != null)
				return false;
		} else if (!metricId.equals(other.metricId))
			return false;
		if (packageId == null) {
			if (other.packageId != null)
				return false;
		} else if (!packageId.equals(other.packageId))
			return false;
		return true;
	}

}
