package com.littlepay.app.utils;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.littlepay.app.constants.enums.BusStop;
import com.littlepay.app.entities.TapInfo;

/**
 * unit tests for TripsUtils class
 * 
 * @author Harvinder Singh
 *
 */
public class TripsUtilsTest {

	private TripsUtils tripsUtils = new TripsUtils();

	@Test
	public void testConvertInputFileToTapInfoListSuccess() throws Exception {
		String inputFileName = "src\\test\\resources\\input\\taps.csv";
		File srcFile = new File(inputFileName);
		List<TapInfo> inputData = tripsUtils.convertInputFileToTapInfoList(srcFile);
		assertEquals(2, inputData.size());
	}

	@Test
	public void testGetTripChargeChartSuccess() {
		String[][] tripChargeChart = tripsUtils.getTripChargeChart();
		assertEquals("7.30", tripChargeChart[BusStop.STOP1.getStopId()][BusStop.STOP3.getStopId()]);
	}
}
