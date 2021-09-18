package com.littlepay.app.entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.littlepay.app.constants.enums.TripStatus;

/**
 * This class maps to a Trip object. Trip objects are written to the output
 * trips.csv file. Trips are created from the information from TapInfo objects
 * 
 * @author Harvinder Singh
 *
 */
public class Trip {

	/*
	 * start dateTime of the trip
	 */
	private Date startedDateTime;

	/*
	 * finish dateTime of the trip
	 */
	private Date finishedDateTime;

	/*
	 * trip duration in secs
	 */
	private Long durationSecs;

	/*
	 * stop id from where the trip started
	 */
	private String fromStopLabel;

	/*
	 * stop id where the trip finished
	 */
	private String toStopLabel;

	/*
	 * total amount charged for trip
	 */
	private String chargeAmount;

	/*
	 * company identifier for trip
	 */
	private String companyId;

	/*
	 * identifier for the bus
	 */
	private String busId;

	/*
	 * Primary Account Number
	 */
	private String pan;

	/*
	 * status of the trip
	 */
	private TripStatus status;

	public Date getStartedDateTime() {
		return startedDateTime;
	}

	public void setStartedDateTime(Date startedDateTime) {
		this.startedDateTime = startedDateTime;
	}

	public Date getFinishedDateTime() {
		return finishedDateTime;
	}

	public void setFinishedDateTime(Date finishedDateTime) {
		this.finishedDateTime = finishedDateTime;
	}

	public Long getDurationSecs() {
		return durationSecs;
	}

	public void setDurationSecs(Long durationSecs) {
		this.durationSecs = durationSecs;
	}

	public String getFromStopLabel() {
		return fromStopLabel;
	}

	public void setFromStopLabel(String fromStopLabel) {
		this.fromStopLabel = fromStopLabel;
	}

	public String getToStopLabel() {
		return toStopLabel;
	}

	public void setToStopLabel(String toStoplabel) {
		this.toStopLabel = toStoplabel;
	}

	public String getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(String chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getBusId() {
		return busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public TripStatus getStatus() {
		return status;
	}

	public void setStatus(TripStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Trip [Started=" + startedDateTime + ", Finished=" + finishedDateTime + ", DurationSecs=" + durationSecs
				+ ", FromStopId=" + fromStopLabel + ", ToStopId=" + toStopLabel + ", ChargeAmount=$" + chargeAmount
				+ ", CompanyId=" + companyId + ", BusId=" + busId + ", PAN=" + pan + ", Status=" + status + "]";
	}

	/**
	 * String representation of the Trip object - used to write to the output
	 * trips.csv file
	 * 
	 * @return
	 */
	public String toOutputFileLineString() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String startDateStr = getStartedDateTime() == null ? "" : dateFormat.format(getStartedDateTime());
		String finishDateStr = getFinishedDateTime() == null ? "" : dateFormat.format(getFinishedDateTime());
		String durationSecsStr = getDurationSecs() == null ? "" : getDurationSecs().toString();
		String toStopLabelStr = getToStopLabel() == null ? "" : getToStopLabel();
		return startDateStr + ", " + finishDateStr + ", " + durationSecsStr + ", " + fromStopLabel + ", "
				+ toStopLabelStr + ", $" + chargeAmount + ", " + companyId + ", " + busId + ", " + pan + ", " + status;
	}

}
