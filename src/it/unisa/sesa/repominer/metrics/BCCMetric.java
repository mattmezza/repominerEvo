package it.unisa.sesa.repominer.metrics;

import java.util.Date;

public class BCCMetric {

	private float value;
	private Date periodStart;
	private Date periodEnd;
	private long periodLength;
	private int periodType;

	public static final int PERIOD_MONTH = 0;
	public static final int PERIOD_WEEK = 1;
	public static final int PERIOD_DAY = 2;
	public static final int PERIOD_MINUTE = 3;
	public static final int PERIOD_SECOND = 4;
	public static final int PERIOD_MILLIS = 5;

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public Date getPeriodStart() {
		return periodStart;
	}

	public void setPeriodStart(Date periodStart) {
		this.periodStart = periodStart;
		if(this.periodEnd!=null) {
			this.periodLength = this.periodEnd.getTime() - this.periodStart.getTime();
			this.periodType = BCCMetric.PERIOD_MILLIS;
		}
	}

	public Date getPeriodEnd() {
		return periodEnd;
	}

	public void setPeriodEnd(Date periodEnd) {
		this.periodEnd = periodEnd;
		if(this.periodStart!=null) {
			this.periodLength = this.periodEnd.getTime() - this.periodStart.getTime();
			this.periodType = BCCMetric.PERIOD_MILLIS;
		}
	}

	public long getPeriodLength() {
		return periodLength;
	}

	public int getPeriodType() {
		return periodType;
	}

}
