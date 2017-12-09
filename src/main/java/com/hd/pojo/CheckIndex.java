package com.hd.pojo;

public class CheckIndex {
	private String batchNo;			//丝的批号
	private String checkIndex;		//检测丝的指标
	private double standardValue;		//丝的标准值
	private double deviationValue;		//丝的偏差值
	
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getCheckIndex() {
		return checkIndex;
	}
	public void setCheckIndex(String checkIndex) {
		this.checkIndex = checkIndex;
	}
	public double getStandardValue() {
		return standardValue;
	}
	public void setStandardValue(double standardValue) {
		this.standardValue = standardValue;
	}
	public double getDeviationValue() {
		return deviationValue;
	}
	public void setDeviationValue(double deviationValue) {
		this.deviationValue = deviationValue;
	}
	@Override
	public String toString() {
		return "CheckIndex [batchNo=" + batchNo + ", checkIndex=" + checkIndex + ", standardValue=" + standardValue
				+ ", deviationValue=" + deviationValue + "]";
	}
	
}
