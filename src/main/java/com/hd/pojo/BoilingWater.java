package com.hd.pojo;

public class BoilingWater {
	private String batchNo; 	// 产品的批号
	private String lineNo; 		// 产品的线位号
	private int bwStatus;		//产品的状态 0:代表未修改,1:代表复测数据,21:代表研究加测数据,31:代表车间加测数据,41:异常丝
	private int boilingWater; 	// 产品的沸水
	private String createDate;	// 表单的生成时间
	
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getLineNo() {
		return lineNo;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	public int getBwStatus() {
		return bwStatus;
	}
	public void setBwStatus(int bwStatus) {
		this.bwStatus = bwStatus;
	}
	public int getBoilingWater() {
		return boilingWater;
	}
	public void setBoilingWater(int boilingWater) {
		this.boilingWater = boilingWater;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
	@Override
	public String toString() {
		return "BoilingWater [batchNo=" + batchNo + ", lineNo=" + lineNo + ", bwStatus=" + bwStatus + ", boilingWater="
				+ boilingWater + ", createDate=" + createDate + "]";
	}
}
