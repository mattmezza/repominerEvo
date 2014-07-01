package it.unisa.sesa.repominer.db.entities;

import java.util.Date;

import net.sf.jeasyorm.annotation.Column;
import net.sf.jeasyorm.annotation.Table;

@Table(name = "package_metrics")
public class PackageMetric extends Metric {
	@Column(name = "source_container")
	private Integer packageId;
	@Column(name = "metric")
	private Integer metricId;
	private Double value;
	private Date startDate;
	private Date endDate;

	public PackageMetric() {

	}

	public PackageMetric(Integer id, String name, String description,
			Integer packageId, Integer metricId, Double value, Date startDate,
			Date endDate) {
		super(id, name, description);
		this.packageId = packageId;
		this.metricId = metricId;
		this.value = value;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "PackageMetric [packageId=" + packageId + ", metricId="
				+ metricId + ", value=" + value + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result
				+ ((metricId == null) ? 0 : metricId.hashCode());
		result = prime * result
				+ ((packageId == null) ? 0 : packageId.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
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
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
