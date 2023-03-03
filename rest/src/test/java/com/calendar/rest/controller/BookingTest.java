package com.calendar.rest.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.calendar.rest.model.Booking;
import com.calendar.rest.repo.BookingRepo;

public class BookingTest {

	private static class TestBookingRepo implements BookingRepo {

		private List<Booking> repoList = new ArrayList<>();

		@Override
		public void flush() {
			// TODO Auto-generated method stub

		}

		@Override
		public <S extends Booking> S saveAndFlush(S entity) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <S extends Booking> List<S> saveAllAndFlush(Iterable<S> entities) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void deleteAllInBatch(Iterable<Booking> entities) {
			// TODO Auto-generated method stub

		}

		@Override
		public void deleteAllByIdInBatch(Iterable<Long> ids) {
			// TODO Auto-generated method stub

		}

		@Override
		public void deleteAllInBatch() {
			// TODO Auto-generated method stub

		}

		@Override
		public Booking getOne(Long id) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Booking getById(Long id) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Booking getReferenceById(Long id) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <S extends Booking> List<S> findAll(Example<S> example) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <S extends Booking> List<S> findAll(Example<S> example, Sort sort) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <S extends Booking> List<S> saveAll(Iterable<S> entities) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Booking> findAll() {
			return Collections.unmodifiableList(repoList);
		}

		@Override
		public List<Booking> findAllById(Iterable<Long> ids) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <S extends Booking> S save(S entity) {
			repoList.add(entity);
			return entity;
		}

		@Override
		public Optional<Booking> findById(Long id) {
			// TODO Auto-generated method stub
			return Optional.empty();
		}

		@Override
		public boolean existsById(Long id) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public long count() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void deleteById(Long id) {
			// TODO Auto-generated method stub

		}

		@Override
		public void delete(Booking entity) {
			// TODO Auto-generated method stub

		}

		@Override
		public void deleteAllById(Iterable<? extends Long> ids) {
			// TODO Auto-generated method stub

		}

		@Override
		public void deleteAll(Iterable<? extends Booking> entities) {
			// TODO Auto-generated method stub

		}

		@Override
		public void deleteAll() {
			repoList.clear();
		}

		@Override
		public List<Booking> findAll(Sort sort) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Page<Booking> findAll(Pageable pageable) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <S extends Booking> Optional<S> findOne(Example<S> example) {
			// TODO Auto-generated method stub
			return Optional.empty();
		}

		@Override
		public <S extends Booking> Page<S> findAll(Example<S> example, Pageable pageable) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <S extends Booking> long count(Example<S> example) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public <S extends Booking> boolean exists(Example<S> example) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public <S extends Booking, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	private BookingRepo bookingRepo = new TestBookingRepo();

	@Test
	public void testBookingMoreThan3HoursLong() {
		Booking booking = new Booking().setId(1).setUserName("Geza").setTopic("Initial meeting").setDate(createDate())
				.setBeginTime(createTime(9, 30)).setEndTime(createTime(13, 0));
		String error = ApiControllers.checkBooking(booking, bookingRepo);
		System.out.println(booking);
		System.out.println(error);
		Assertions.assertNotNull(error);
	}

	@Test
	public void testBookingLengthNotMultiHalfHour() {
		Booking booking = new Booking().setId(1).setUserName("Geza").setTopic("Initial meeting").setDate(createDate())
				.setBeginTime(createTime(9, 29)).setEndTime(createTime(10, 1));
		String error = ApiControllers.checkBooking(booking, bookingRepo);
		System.out.println(booking);
		System.out.println(error);
		Assertions.assertNotNull(error);
	}

	@Test
	public void testBookingOutOfInterval() {
		testBookingBeginsBeforeInterval();
		testBookingEndsAfterInterval();
		testBookingBeforeInterval();
		testBookingAfterInterval();
	}

	private void testBookingBeginsBeforeInterval() {
		Booking booking = new Booking().setId(1).setUserName("Geza").setTopic("Initial meeting").setDate(createDate())
				.setBeginTime(createTime(8, 30)).setEndTime(createTime(9, 0));
		String error = ApiControllers.checkBooking(booking, bookingRepo);
		System.out.println(booking);
		System.out.println(error);
		Assertions.assertNotNull(error);
	}

	private void testBookingEndsAfterInterval() {
		Booking booking = new Booking().setId(1).setUserName("Geza").setTopic("Initial meeting").setDate(createDate())
				.setBeginTime(createTime(16, 30)).setEndTime(createTime(17, 30));
		String error = ApiControllers.checkBooking(booking, bookingRepo);
		System.out.println(booking);
		System.out.println(error);
		Assertions.assertNotNull(error);
	}

	private void testBookingBeforeInterval() {
		Booking booking = new Booking().setId(1).setUserName("Geza").setTopic("Initial meeting").setDate(createDate())
				.setBeginTime(createTime(8, 30)).setEndTime(createTime(9, 0));
		String error = ApiControllers.checkBooking(booking, bookingRepo);
		System.out.println(booking);
		System.out.println(error);
		Assertions.assertNotNull(error);
	}

	private void testBookingAfterInterval() {
		Booking booking = new Booking().setId(1).setUserName("Geza").setTopic("Initial meeting").setDate(createDate())
				.setBeginTime(createTime(17, 0)).setEndTime(createTime(17, 30));
		String error = ApiControllers.checkBooking(booking, bookingRepo);
		System.out.println(booking);
		System.out.println(error);
		Assertions.assertNotNull(error);
	}

	@Test
	public void testBookingOverlap() {
		/*
		 * Existing: 13:00 - 15:00
		 * 
		 * Overlap: 12:30 - 13:30, 12:30 - 15:00, 12:30 - 15:30, 13:00 - 13:30, 13:00 -
		 * 15:00, 13:00 - 15:30, 13:30 - 14:30, 13:30 - 15:00, 13:30 - 15:30
		 */
		Booking booking = new Booking().setId(1).setUserName("Jakab").setTopic("Existing meeting").setDate(createDate())
				.setBeginTime(createTime(13, 0)).setEndTime(createTime(15, 00));
		bookingRepo.save(booking);
		testBookingOverlapBeginsBeforeEndsBefore();
		testBookingOverlapBeginsBeforeEndsSame();
		testBookingOverlapBeginsBeforeEndsAfter();
		testBookingOverlapBeginsSameEndsBefore();
		testBookingOverlapBeginsSameEndsSame();
		testBookingOverlapBeginsSameEndsAfter();
		testBookingOverlapBeginsAfterEndsBefore();
		testBookingOverlapBeginsAfterEndsSame();
		testBookingOverlapBeginsAfterEndsAfter();
	}

	private void testBookingOverlapBeginsBeforeEndsBefore() {
		// 12:30 - 13:30
		Booking booking = new Booking().setId(1).setUserName("Geza").setTopic("Overlapping meeting")
				.setDate(createDate()).setBeginTime(createTime(12, 30)).setEndTime(createTime(13, 30));
		String error = ApiControllers.checkBooking(booking, bookingRepo);
		System.out.println(booking);
		System.out.println(error);
		Assertions.assertNotNull(error);
	}

	private void testBookingOverlapBeginsBeforeEndsSame() {
		// 12:30 - 15:00
		Booking booking = new Booking().setId(1).setUserName("Geza").setTopic("Overlapping meeting")
				.setDate(createDate()).setBeginTime(createTime(12, 30)).setEndTime(createTime(15, 0));
		String error = ApiControllers.checkBooking(booking, bookingRepo);
		System.out.println(booking);
		System.out.println(error);
		Assertions.assertNotNull(error);
	}

	private void testBookingOverlapBeginsBeforeEndsAfter() {
		// 12:30 - 15:30
		Booking booking = new Booking().setId(1).setUserName("Geza").setTopic("Overlapping meeting")
				.setDate(createDate()).setBeginTime(createTime(12, 30)).setEndTime(createTime(15, 30));
		String error = ApiControllers.checkBooking(booking, bookingRepo);
		System.out.println(booking);
		System.out.println(error);
		Assertions.assertNotNull(error);
	}

	private void testBookingOverlapBeginsSameEndsBefore() {
		// 13:00 - 13:30
		Booking booking = new Booking().setId(1).setUserName("Geza").setTopic("Overlapping meeting")
				.setDate(createDate()).setBeginTime(createTime(13, 0)).setEndTime(createTime(13, 30));
		String error = ApiControllers.checkBooking(booking, bookingRepo);
		System.out.println(booking);
		System.out.println(error);
		Assertions.assertNotNull(error);
	}

	private void testBookingOverlapBeginsSameEndsSame() {
		// 13:00 - 15:00
		Booking booking = new Booking().setId(1).setUserName("Geza").setTopic("Overlapping meeting")
				.setDate(createDate()).setBeginTime(createTime(13, 0)).setEndTime(createTime(15, 0));
		String error = ApiControllers.checkBooking(booking, bookingRepo);
		System.out.println(booking);
		System.out.println(error);
		Assertions.assertNotNull(error);
	}

	private void testBookingOverlapBeginsSameEndsAfter() {
		// 13:00 - 15:30
		Booking booking = new Booking().setId(1).setUserName("Geza").setTopic("Overlapping meeting")
				.setDate(createDate()).setBeginTime(createTime(13, 0)).setEndTime(createTime(15, 30));
		String error = ApiControllers.checkBooking(booking, bookingRepo);
		System.out.println(booking);
		System.out.println(error);
		Assertions.assertNotNull(error);
	}

	private void testBookingOverlapBeginsAfterEndsBefore() {
		// 13:30 - 14:30
		Booking booking = new Booking().setId(1).setUserName("Geza").setTopic("Overlapping meeting")
				.setDate(createDate()).setBeginTime(createTime(13, 30)).setEndTime(createTime(14, 30));
		String error = ApiControllers.checkBooking(booking, bookingRepo);
		System.out.println(booking);
		System.out.println(error);
		Assertions.assertNotNull(error);
	}

	private void testBookingOverlapBeginsAfterEndsSame() {
		// 13:30 - 15:00
		Booking booking = new Booking().setId(1).setUserName("Geza").setTopic("Overlapping meeting")
				.setDate(createDate()).setBeginTime(createTime(13, 30)).setEndTime(createTime(15, 0));
		String error = ApiControllers.checkBooking(booking, bookingRepo);
		System.out.println(booking);
		System.out.println(error);
		Assertions.assertNotNull(error);
	}

	private void testBookingOverlapBeginsAfterEndsAfter() {
		// 13:30 - 15:30
		Booking booking = new Booking().setId(1).setUserName("Geza").setTopic("Overlapping meeting")
				.setDate(createDate()).setBeginTime(createTime(13, 30)).setEndTime(createTime(15, 30));
		String error = ApiControllers.checkBooking(booking, bookingRepo);
		System.out.println(booking);
		System.out.println(error);
		Assertions.assertNotNull(error);
	}

	@Test
	public void testBookingNotFrom0Or30Minutes() {
		Booking booking = new Booking().setId(1).setUserName("Geza").setTopic("Initial meeting").setDate(createDate())
				.setBeginTime(createTime(10, 15)).setEndTime(createTime(10, 45));
		String error = ApiControllers.checkBooking(booking, bookingRepo);
		System.out.println(booking);
		System.out.println(error);
		Assertions.assertNotNull(error);
	}

	@Test
	public void test4X2BookingForTheSameDay() {
		Booking booking = new Booking().setId(1).setUserName("Geza").setTopic("Initial meeting").setDate(createDate())
				.setBeginTime(createTime(11, 0)).setEndTime(createTime(13, 0));
		String error = ApiControllers.checkBooking(booking, bookingRepo);
		Assertions.assertNull(error);
		bookingRepo.save(booking);

		booking = new Booking().setId(1).setUserName("Pista").setTopic("Other meeting").setDate(createDate())
				.setBeginTime(createTime(9, 0)).setEndTime(createTime(11, 0));
		error = ApiControllers.checkBooking(booking, bookingRepo);
		Assertions.assertNull(error);
		bookingRepo.save(booking);

		booking = new Booking().setId(1).setUserName("Joska").setTopic("Conference").setDate(createDate())
				.setBeginTime(createTime(15, 0)).setEndTime(createTime(17, 0));
		error = ApiControllers.checkBooking(booking, bookingRepo);
		Assertions.assertNull(error);
		bookingRepo.save(booking);

		booking = new Booking().setId(1).setUserName("Bela").setTopic("Team building").setDate(createDate())
				.setBeginTime(createTime(13, 0)).setEndTime(createTime(15, 0));
		error = ApiControllers.checkBooking(booking, bookingRepo);
		Assertions.assertNull(error);
		bookingRepo.save(booking);
	}

	@AfterEach
	public void clearBookings() {
		bookingRepo.deleteAll();
	}

	private static Date createDate() {
		return createDate(2023, 3, 2);
	}

	private static Date createDate(int year, int month, int dayOfMonth) {
		return Date.valueOf("" + year + "-" + month + "-" + dayOfMonth);
	}

	private static Time createTime(int hour, int minute) {
		return Time.valueOf("" + hour + ":" + minute + ":00");
	}

}
