package org.fleetopgroup.persistence.model;

import java.io.Serializable;

public class CalenderEvent implements Serializable {

	/**
	 *  Default Serial Version
	 */
	private static final long serialVersionUID = 1L;
	
	private String eventDay;
	private String eventMonth;
	private String eventYear;
	private String eventTitle;
	private String eventUrl;
	private String eventColor;
	private String eventDescription;
	private String eventDate;
	private boolean isEventHistory;
	private long count;
	

	public boolean isEventHistory() {
		return isEventHistory;
	}
	public void setEventHistory(boolean isEventHistory) {
		this.isEventHistory = isEventHistory;
	}
	public String getEventDay() {
		return eventDay;
	}
	public void setEventDay(String eventDay) {
		this.eventDay = eventDay;
	}
	public String getEventMonth() {
		return eventMonth;
	}
	public void setEventMonth(String eventMonth) {
		this.eventMonth = eventMonth;
	}
	public String getEventYear() {
		return eventYear;
	}
	public void setEventYear(String eventYear) {
		this.eventYear = eventYear;
	}
	public String getEventTitle() {
		return eventTitle;
	}
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}
	public String getEventUrl() {
		return eventUrl;
	}
	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}
	public String getEventColor() {
		return eventColor;
	}
	public void setEventColor(String eventColor) {
		this.eventColor = eventColor;
	}
	public String getEventDescription() {
		return eventDescription;
	}
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
}