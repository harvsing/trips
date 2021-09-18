package com.littlepay.app.entities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class maps the Taps from the input csv file. Each line in the csv file
 * is represented by a TapInfo object except the header line in csv file.
 * 
 * @author Harvinder Singh
 *
 */
public class TapInfo {

	public TapInfo() {
		super();
	}

	/**
	 * constructor for creating TapInfo object directly from input file line
	 * 
	 * @param tapInfoStr - input file line from taps.csv file
	 * @throws ParseException - thrown when parsing DateTimeUTC from csv file line
	 */
	public TapInfo(String tapInfoStr) throws ParseException {
		super();
		String[] tapInfoData = tapInfoStr.split(",");
		this.setId(tapInfoData[0] == null ? null : Long.parseLong(tapInfoData[0].trim()));
		if (tapInfoData[1] != null) {
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			try {
				this.setDateTimeUTC(df.parse(tapInfoData[1].trim()));
			} catch (ParseException e) {
				e.printStackTrace();
				throw e;
			}
		}
		this.setTapType(tapInfoData[2] == null ? null : tapInfoData[2].trim());
		this.setStopLabel(tapInfoData[3] == null ? null : tapInfoData[3].trim());
		this.setCompanyId(tapInfoData[4] == null ? null : tapInfoData[4].trim());
		this.setBusId(tapInfoData[5] == null ? null : tapInfoData[5].trim());
		this.setPan(tapInfoData[6] == null ? null : tapInfoData[6].trim());
	}

	/*
	 * id of the tap
	 */
	private Long id;

	/*
	 * timestamp of the tap
	 */
	private Date dateTimeUTC;

	/*
	 * tap type ON/OFF
	 */
	private String tapType;

	/*
	 * identifier of stop where the tap occured
	 */
	private String stopLabel;

	/*
	 * company identifier
	 */
	private String companyId;

	/*
	 * bus identifier
	 */
	private String busId;

	/*
	 * Primary Account Number
	 */
	private String pan;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateTimeUTC() {
		return dateTimeUTC;
	}

	public void setDateTimeUTC(Date dateTimeUTC) {
		this.dateTimeUTC = dateTimeUTC;
	}

	public String getTapType() {
		return tapType;
	}

	public void setTapType(String tapType) {
		this.tapType = tapType;
	}

	public String getStopLabel() {
		return stopLabel;
	}

	public void setStopLabel(String stopLabel) {
		this.stopLabel = stopLabel;
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

	@Override
	public String toString() {
		return "TapInfo [ID=" + id + ", DateTimeUTC=" + dateTimeUTC + ", TapType=" + tapType + ", StopId=" + stopLabel
				+ ", CompanyId=" + companyId + ", BusId=" + busId + ", PAN=" + pan + "]";
	}

}
