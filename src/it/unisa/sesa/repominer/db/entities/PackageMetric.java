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
	private Date start;
	private Date end;

	public PackageMetric() {

	}

	public PackageMetric(Integer id, String name, String description,
			Integer packageId, Integer metricId, Double value, Date startDate,
			Date endDate) {
		super(id, name, description);
		this.packageId = packageId;
		this.metricId = metricId;
		this.value = value;
		this.start = startDate;
		this.end = endDate;
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

	public Date getStart() {
		return start;
	}

	public void setStart(Date startDate) {
		this.start = startDate;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date endDate) {
		this.end = endDate;
	}

	@Override
	public String toString() {
		return "PackageMetric [packageId=" + packageId + ", metricId="
				+ metricId + ", value=" + value + ", startDate=" + start
				+ ", endDate=" + end + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result
				+ ((metricId == null) ? 0 : metricId.hashCode());
		result = prime * result
				+ ((packageId == null) ? 0 : packageId.hashCode());
		result = prime * result
				+ ((start == null) ? 0 : start.hashCode());
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
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
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
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
