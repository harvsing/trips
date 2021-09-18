package com.littlepay.app.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.littlepay.app.constants.enums.TripStatus;
import com.littlepay.app.entities.TapInfo;
import com.littlepay.app.entities.Trip;

/**
 * unit tests for TripService class
 * 
 * @author Harvinder Singh
 *
 */
public class TripServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(TripServiceTest.class);

	private static final String DUMMY_STOP1_TAP_INFO = "1, 22-01-2018 13:00:00, ON, Stop1, Company1, Bus37, 5500005555555559";

	private TripService tripService = new TripService();

	@Test
	public void testGetDurationInSecsSuccess() throws ParseException {
		DateFormat df = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
		String startDateStr = "22-01-2018 13:00:00";
		String finishDateStr = "22-01-2018 13:05:00";
		try {
			Date startDate = df.parse(startDateStr);
			Date finishDate = df.parse(finishDateStr);
			Long diffInMillis = tripService.getDurationInSecs(finishDate, startDate);
			assertEquals(Long.valueOf(300), diffInMillis);
		} catch (ParseException e) {
			LOGGER.error("Error occured while parsing date : {}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void testGetFareBetweenStopsSuccess() {
		String stop1 = "Stop1";
		String stop2 = "Stop2";
		String fareBetweenStops = tripService.getFareBetweenStops(stop1, stop2);
		assertEquals("3.25", fareBetweenStops);
	}

	@Test
	public void testGetMaxFarePossibleSuccess() {
		String stop1 = "Stop1";
		String maxFarePossible = tripService.getMaxFarePossible(stop1);
		assertEquals("7.30", maxFarePossible);
	}

	@Test
	public void testCreateTripCompletedSuccess() throws ParseException {
		TapInfo firstTap = new TapInfo(DUMMY_STOP1_TAP_INFO);
		TapInfo secondTap = new TapInfo("2, 22-01-2018 13:05:00, OFF, Stop2, Company1, Bus37, 5500005555555559");

		Trip trip = tripService.createTrip(firstTap, secondTap, TripStatus.COMPLETED);

		assertTrue(trip.getStatus().equals(TripStatus.COMPLETED));
		assertEquals("3.25", trip.getChargeAmount());
		assertEquals(Long.valueOf(300), trip.getDurationSecs());
	}

	@Test
	public void testCreateTripCancelledSuccess() throws ParseException {
		TapInfo firstTap = new TapInfo(DUMMY_STOP1_TAP_INFO);
		TapInfo secondTap = new TapInfo("2, 22-01-2018 13:05:00, OFF, Stop1, Company1, Bus37, 5500005555555559");

		Trip trip = tripService.createTrip(firstTap, secondTap, TripStatus.CANCELLED);

		assertTrue(trip.getStatus().equals(TripStatus.CANCELLED));
		assertEquals("0.00", trip.getChargeAmount());
		assertEquals(Long.valueOf(300), trip.getDurationSecs());
	}

	@Test
	public void testCreateTripIncompleteSuccess() throws ParseException {
		TapInfo firstTap = new TapInfo(DUMMY_STOP1_TAP_INFO);

		Trip trip = tripService.createTrip(firstTap, null, TripStatus.INCOMPLETE);

		assertTrue(trip.getStatus().equals(TripStatus.INCOMPLETE));
		assertEquals("7.30", trip.getChargeAmount());
		assertNull(trip.getDurationSecs());
	}

}
