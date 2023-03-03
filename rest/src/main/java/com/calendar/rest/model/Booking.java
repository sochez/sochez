package com.calendar.rest.model;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String userName;

	@Column
	private String topic;
	
	@Column
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date;
	
	@Column
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private Time beginTime;
	
	@Column
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private Time endTime;

	public Booking() {}
	
	public Booking(String userName, String topic, Date date, Time beginTime, Time endTime) {
		this.userName = userName;
		this.topic = topic;
		this.date = date;
		this.beginTime = beginTime;
		this.endTime = endTime;
	}

	public long getId() {
		return id;
	}

	public Booking setId(long id) {
		this.id = id;
		return this;
	}

	public String getUserName() {
		return userName;
	}

	public Booking setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getTopic() {
		return topic;
	}

	public Booking setTopic(String topic) {
		this.topic = topic;
		return this;
	}

	public Date getDate() {
		return date;
	}

	public Booking setDate(Date date) {
		this.date = date;
		return this;
	}

	public Time getBeginTime() {
		return beginTime;
	}

	public Booking setBeginTime(Time beginTime) {
		this.beginTime = beginTime;
		return this;
	}

	public Time getEndTime() {
		return endTime;
	}

	public Booking setEndTime(Time endTime) {
		this.endTime = endTime;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(beginTime, date, endTime, id, topic, userName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Booking other = (Booking) obj;
		return Objects.equals(beginTime, other.beginTime) && Objects.equals(date, other.date)
				&& Objects.equals(endTime, other.endTime) && id == other.id && Objects.equals(topic, other.topic)
				&& Objects.equals(userName, other.userName);
	}

	@Override
	public String toString() {
		return "Booking [id=" + id + ", userName=" + userName + ", topic=" + topic + ", date=" + date + ", beginTime="
				+ beginTime + ", endTime=" + endTime + "]";
	}
	
}
