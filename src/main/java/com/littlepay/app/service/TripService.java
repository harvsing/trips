package com.littlepay.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.littlepay.app.constants.Constants;
import com.littlepay.app.constants.enums.BusStop;
import com.littlepay.app.constants.enums.TripStatus;
import com.littlepay.app.entities.TapInfo;
import com.littlepay.app.entities.Trip;
import com.littlepay.app.utils.TripsUtils;

/**
 * This class provides the methods to create trips and other helper methods
 * required to create trips.
 * 
 * @author Harvinder Singh
 *
 */
public class TripService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TripService.class);

	private TripsUtils tripsUtils = new TripsUtils();

	/**
	 * creates a list of Trip objects from the List of TapInfo provided
	 * 
	 * @param inputData - List of TapInfo objects
	 * @return List of Trip objects
	 */
	public List<Trip> createTrips(List<TapInfo> inputData) {
		LOGGER.info("STARTED : creating trips.");
		List<Trip> trips = new ArrayList<>();
		if (inputData != null && !inputData.isEmpty()) {
			for (TapInfo originalTap : inputData) {
				if (Constants.TAP_TYPE_ON.equals(originalTap.getTapType())) {
					Optional<TapInfo> nextTapForPAN = inputData.stream().parallel()
							.filter(tapInfo -> tapInfo.getId().longValue() > originalTap.getId().longValue()
									&& tapInfo.getPan().equals(originalTap.getPan()))
							.findFirst();

					if (nextTapForPAN.isPresent()) {
						// found next tap for same PAN, hence this is a Cancelled or complete trip
						if (nextTapForPAN.get().getStopLabel().equals(originalTap.getStopLabel())) {
							// tap ON & OFF at the same stop, hence CANCELLED trip
							trips.add(createTrip(originalTap, nextTapForPAN.get(), TripStatus.CANCELLED));
						} else {
							// tap ON & OFF at two different stops, hence completed trip
							trips.add(createTrip(originalTap, nextTapForPAN.get(), TripStatus.COMPLETED));
						}
					} else {
						// matching tap missing for this PAN, hence this is an incomplete trip
						trips.add(createTrip(originalTap, null, TripStatus.INCOMPLETE));
					}
				} else if (Constants.TAP_TYPE_OFF.equals(originalTap.getTapType())) {
					// this tap info is already considered for other tap type = ON
					// do nothing
				}
			}
		}
		LOGGER.info("FINISHED : creating trips. Total trips created : {}", trips.size());
		return trips;
	}

	/**
	 * creates a Trip object from given params
	 * 
	 * @param firstTap   - TapInfo object created from input csv file data
	 * @param secondTap  - TapInfo object created from input csv file data
	 * @param tripStatus - status of the trip - COMPLETED, CANCELLED, INCOMPLETE
	 * @return Trip object containing information about chargeAmount, durationSecs,
	 *         Status, etc
	 */
	protected Trip createTrip(TapInfo firstTap, TapInfo secondTap, TripStatus tripStatus) {
		Trip trip = new Trip();
		trip.setStatus(tripStatus);
		trip.setStartedDateTime(firstTap.getDateTimeUTC());
		trip.setCompanyId(firstTap.getCompanyId());
		trip.setBusId(firstTap.getBusId());
		trip.setPan(firstTap.getPan());
		trip.setFromStopLabel(firstTap.getStopLabel());

		switch (tripStatus) {
		case CANCELLED:
		case COMPLETED:
			trip.setFinishedDateTime(secondTap.getDateTimeUTC());
			trip.setToStopLabel(secondTap.getStopLabel());
			trip.setChargeAmount(getFareBetweenStops(firstTap.getStopLabel(), secondTap.getStopLabel()));
			trip.setDurationSecs(getDurationInSecs(secondTap.getDateTimeUTC(), firstTap.getDateTimeUTC()));
			break;
		case INCOMPLETE:
			trip.setChargeAmount(getMaxFarePossible(firstTap.getStopLabel()));
			break;
		}

		return trip;
	}

	/**
	 * 
	 * @param originatingStopLabel - Name of the stop from csv file
	 * @return String - value of maximum possible fare from given stop
	 */
	protected String getMaxFarePossible(String originatingStopLabel) {
		String[][] fareChart = tripsUtils.getTripChargeChart();
		if (Constants.STOP_1.equals(originatingStopLabel)) {
			return fareChart[BusStop.STOP1.getStopId()][BusStop.STOP3.getStopId()]; // return fare between stop1 and
																					// stop3
		} else if (Constants.STOP_2.equals(originatingStopLabel)) {
			return fareChart[BusStop.STOP2.getStopId()][BusStop.STOP3.getStopId()]; // return fare between stop2 and
																					// stop3
		} else if (Constants.STOP_3.equals(originatingStopLabel)) {
			return fareChart[BusStop.STOP3.getStopId()][BusStop.STOP1.getStopId()]; // return fare between stop3 and
																					// stop1
		}
		return "7.30"; // max fare possible
	}

	/**
	 * 
	 * @param firstStopLabel  - Name of the stop from csv file
	 * @param secondStopLabel - Name of the stop from csv file
	 * @return String - value of fare between two provided stops
	 */
	protected String getFareBetweenStops(String firstStopLabel, String secondStopLabel) {
		String[][] fareChart = tripsUtils.getTripChargeChart();
		Integer firstStopId = BusStop.getStopIdFromLabel(firstStopLabel);
		Integer secondStopId = BusStop.getStopIdFromLabel(secondStopLabel);
		return fareChart[firstStopId][secondStopId];
	}

	/**
	 * 
	 * @param endDateTime   - dateTime to calculate difference to
	 * @param beginDateTime - dateTime to calculate difference from
	 * @return - difference between two dateTime values in seconds
	 */
	protected Long getDurationInSecs(Date endDateTime, Date beginDateTime) {
		long diffInMillis = endDateTime.getTime() - beginDateTime.getTime();
		return TimeUnit.MILLISECONDS.toSeconds(diffInMillis);
	}
}
