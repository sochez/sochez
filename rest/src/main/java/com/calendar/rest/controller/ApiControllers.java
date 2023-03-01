package com.calendar.rest.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.calendar.rest.model.Booking;
import com.calendar.rest.repo.BookingRepo;
import com.calendar.rest.view.Views;

@RestController
public class ApiControllers {

	@Autowired
	private BookingRepo bookingRepo;

	@GetMapping(value = "/")
	public String getPage() {
		return Views.getOpeningPage();
	}

	@GetMapping(value = "/bookings")
	public String getBookings() { // return List<Booking>
		return Views.getBookingsPage(bookingRepo.findAll());
	}

	@PostMapping(value = "/save")
	public String saveBooking(@RequestBody Booking booking) {
		checkBooking(booking);
		bookingRepo.save(booking);
		return "Saved";
	}

	@PutMapping(value = "/update/{id}")
	public String updateBooking(@PathVariable long id, @RequestBody Booking booking) {
		Optional<Booking> optionalBooking = bookingRepo.findById(id);
		if (optionalBooking.isPresent()) {
			checkBooking(booking);
			Booking updatedBooking = optionalBooking.get();
			updatedBooking.setUserName(booking.getUserName()).setTopic(booking.getTopic()).setDate(booking.getDate())
					.setBeginTime(booking.getBeginTime()).setEndTime(booking.getEndTime());
			bookingRepo.save(updatedBooking);
			return "Updated";
		} else {
			return "Not found";
		}
	}

	@DeleteMapping(value = "/delete/{id}")
	public String deleteBooking(@PathVariable long id) {
		Optional<Booking> optionalUser = bookingRepo.findById(id);
		if (optionalUser.isPresent()) {
			Booking deletedUser = optionalUser.get();
			bookingRepo.delete(deletedUser);
			return "Deleted";
		} else {
			return "Not found";
		}
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping(value = "/")
	public String getBookingsForThisWeek() {
		Date today = new Date();
		int dayOfWeek = today.getDay();
		today.setDate(today.getDate() + 1 - dayOfWeek);
		java.sql.Date monday = new java.sql.Date(today.getYear(), today.getMonth(), today.getDate());
		java.sql.Date friday = new java.sql.Date(monday.getYear(), monday.getMonth(), monday.getDate());
		friday.setDate(friday.getDate() + 4);
		List<Booking> bookings = bookingRepo.findAll().stream().filter(b -> (!b.getBeginTime().before(monday)) && (!b.getEndTime().after(friday))).collect(Collectors.toList());
		return Views.getWeeklyBookingsPage(bookings);
	}
	
	@SuppressWarnings("deprecation")
	private String checkBooking(Booking booking) {
		StringBuilder sb = new StringBuilder();
		
		int beginHour = booking.getBeginTime().getHours();
		int beginMinute = booking.getBeginTime().getMinutes();
		int endHour = booking.getEndTime().getHours();
		int endMinute = booking.getEndTime().getMinutes();
		
		// Booking begins before it ends
		
		if (!booking.getBeginTime().before(booking.getEndTime())) {
			sb.append(" Booking begins later than it ends ");
		}
		
		// Booking between 9:00 and 17:00
		
		if (beginHour < 9) {
			sb.append(" Booking begins before 9:00 ");
		}
		if ((beginHour >= 16) && (beginMinute > 30)) {
			sb.append(" Booking begins after 16:30 ");
		}
		if ((endHour <= 9) && (endMinute < 30)) {
			sb.append(" Booking ends before 9:30 ");
		}
		if ((endHour >= 17) && (endMinute > 0)) {
			sb.append(" Booking ends after 17:00 ");
		}
		
		// Booking from X:00 or X:30 to X:00 or X:30
		
		if ((beginMinute != 0) && (beginMinute != 30)) {
			sb.append(" Booking begins at minute " + beginMinute + " ");
		}
		if ((endMinute != 0) && (endMinute != 30)) {
			sb.append(" Booking ends at minute " + endMinute + " ");
		}
		
		// Booking may be 30 minutes long or multiplication of it up to 3 hours
		
		int durationInMinutes = 60 * (endHour - beginHour) + endMinute - beginMinute;
		if (durationInMinutes > 60 * 3) {
			sb.append(" Booking lasts more than 3 hours ");
		}
		if (durationInMinutes < 30) {
			sb.append(" Booking lasts less than 30 minutes ");
		}
		if ((durationInMinutes % 30) != 0) {
			sb.append(" Booking does not last multiplication of 30 minutes ");
		}
		
		// No overlapping between bookings
		
		Optional<Booking> overlap = bookingRepo.findAll().stream().filter(b -> isOverlapping(b, booking)).findFirst();
		if (overlap.isPresent()) {
			sb.append(" Booking is in overlapping with an existing one ");
		}
		
		if (sb.isEmpty()) {
			return null;
		} else {
			return sb.toString().trim().replace("  ", "\n");
		}
	}
	
	private static boolean isOverlapping(Booking existingBooking, Booking newBooking) {
		// new.begin <= existing.begin && new.end > existing.begin
		if ((!newBooking.getBeginTime().after(existingBooking.getBeginTime())) && (newBooking.getEndTime().after(existingBooking.getBeginTime()))) {
			return true;
		}
		// new.begin >= existing.begin && new.begin < existing.end
		if (!newBooking.getBeginTime().before(existingBooking.getBeginTime()) && (newBooking.getBeginTime().before(existingBooking.getEndTime()))) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		new ApiControllers().getBookingsForThisWeek();
	}

}
