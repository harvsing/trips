package com.littlepay.app;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.littlepay.app.entities.TapInfo;
import com.littlepay.app.entities.Trip;
import com.littlepay.app.service.TripService;
import com.littlepay.app.utils.TripsUtils;

/**
 * This class is the entry point of the application. This has calls to different
 * utilities and services to read/write files and create trips.
 * 
 * @author Harvinder Singh
 *
 */
public class TripsApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(TripsApplication.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		LOGGER.info("TripsApplication main method called.");
		TripsUtils tripsUtils = new TripsUtils();
		TripService tripService = new TripService();
		File srcFile = new File("taps.csv");
		List<TapInfo> inputData = tripsUtils.convertInputFileToTapInfoList(srcFile);
		List<Trip> outputData = tripService.createTrips(inputData);
		tripsUtils.writeTripsToOutputFile(outputData);
	}

}
