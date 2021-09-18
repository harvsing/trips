package com.littlepay.app.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.littlepay.app.constants.Constants;
import com.littlepay.app.constants.enums.BusStop;
import com.littlepay.app.entities.TapInfo;
import com.littlepay.app.entities.Trip;

/**
 * This class holds the utility methods used to perform read/write of files and
 * provide charge/fare info
 * 
 * @author Harvinder Singh
 *
 */
public class TripsUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(TripsUtils.class);

	/**
	 * 
	 * @param inputFile - file to convert to list of TapInfo
	 * @return list of TapInfo objects
	 * @throws IOException    - in case of any error in reading the csv file
	 * @throws ParseException - in case of parsing DateTimeUTC from csv file
	 */
	public List<TapInfo> convertInputFileToTapInfoList(File inputFile) throws IOException, ParseException {
		LOGGER.info("STARTED : Converting input file to TapInfo list.");
		List<TapInfo> tapsList = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("ID")) {
					// this is the header line of the csv file
					// DO nothing
				} else {
					tapsList.add(new TapInfo(line));
				}
			}
		} catch (IOException e) {
			LOGGER.error("Error occured while converting input file to TapInfo list : {}", e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (ParseException e) {
			LOGGER.error("Error occured while creating TapInfo object : {}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		LOGGER.info("FINISHED : Converting input file to TapInfo list. Total taps : {}", tapsList.size());
		return tapsList;
	}

	/**
	 * writes the created Trip objects to trips.csv file
	 * 
	 * @param outputData - trips to be written to the trips.csv file
	 * @throws IOException - in case of any error while writing the file
	 */
	public void writeTripsToOutputFile(List<Trip> outputData) throws IOException {
		LOGGER.info("STARTED : writing outputData to trips.csv file.");
		File fout = new File("trips.csv");
		FileOutputStream fos = new FileOutputStream(fout);

		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos))) {
			//@formatter:off
			// Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN, Status
			//@formatter:on
			bw.write(Constants.OUTPUT_FILE_HEADER);
			bw.newLine();
			if (outputData != null && !outputData.isEmpty()) {
				for (Trip trip : outputData) {
					bw.write(trip.toOutputFileLineString());
					bw.newLine();
				}
			}
		} catch (IOException e) {
			LOGGER.error("Error occured while writing output file : {}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		LOGGER.info("FINISHED : writing outputData to trips.csv file.");
	}

	//@formatter:off
	/**
	 * 
	 * 			stop1 	stop2 	stop3 
	 * stop1 	0.00 	3.25 	7.30 
	 * stop2 	3.25 	0.00 	5.50 
	 * stop3 	7.30 	5.50 	0.00
	 *
	 *@return - a multidimensional String array containing fare/charge for
	 *         different stop combinations
	 *
	 */
	//@formatter:on
	public String[][] getTripChargeChart() {
		String[][] tripChargeChart = new String[3][3];
		tripChargeChart[BusStop.STOP1.getStopId()][BusStop.STOP1.getStopId()] = "0.00";
		tripChargeChart[BusStop.STOP1.getStopId()][BusStop.STOP2.getStopId()] = "3.25";
		tripChargeChart[BusStop.STOP1.getStopId()][BusStop.STOP3.getStopId()] = "7.30";
		tripChargeChart[BusStop.STOP2.getStopId()][BusStop.STOP1.getStopId()] = "3.25";
		tripChargeChart[BusStop.STOP2.getStopId()][BusStop.STOP2.getStopId()] = "0.00";
		tripChargeChart[BusStop.STOP2.getStopId()][BusStop.STOP3.getStopId()] = "5.50";
		tripChargeChart[BusStop.STOP3.getStopId()][BusStop.STOP1.getStopId()] = "7.30";
		tripChargeChart[BusStop.STOP3.getStopId()][BusStop.STOP2.getStopId()] = "5.50";
		tripChargeChart[BusStop.STOP3.getStopId()][BusStop.STOP3.getStopId()] = "0.00";
		return tripChargeChart;
	}
}
