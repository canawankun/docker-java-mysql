package com.aksh.marketlog.dto;

import java.sql.Time;

public class HourlyAnalytics extends DailyAnalytics{
	private Time hour;

	public Time getHour() {
		return hour;
	}

	public void setHour(Time hour) {
		this.hour = hour;
	}
	
}
