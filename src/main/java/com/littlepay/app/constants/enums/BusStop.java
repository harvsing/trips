package com.littlepay.app.constants.enums;

/**
 * enum to represent a BusStop
 * 
 * @author Harvinder Singh
 *
 */
public enum BusStop {
	STOP1(0, "Stop1"), STOP2(1, "Stop2"), STOP3(2, "Stop3");

	Integer stopId;
	String label;

	BusStop(Integer stopId, String label) {
		this.stopId = stopId;
		this.label = label;
	}

	public Integer getStopId() {
		return this.stopId;
	}

	public String getLabel() {
		return this.label;
	}

	/**
	 * 
	 * @param label - Name of the stop from csv file
	 * @return id corresponding to stop label
	 */
	public static Integer getStopIdFromLabel(String label) {

		for (BusStop busStop : BusStop.values()) {
			if (busStop.getLabel().equals(label)) {
				return busStop.getStopId();
			}
		}
		return null;
	}
}
