package com.calendar.rest.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calendar.rest.model.Booking;

public interface BookingRepo extends JpaRepository<Booking, Long> {

}
