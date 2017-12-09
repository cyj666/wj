package com.hd.pojo;





public class Other {

	private String batchNo;			//批号
	private String lineNo;			//线位号
	private Integer otStatus;			//状态
	private double yarnlevelness;		//条干
	private double linearDensity;		//线密度
	private Integer gridLine;			//网络线（平均）
	private String createDate;		//日期
	
	/**
	 * 后续添加
	 * @return
	 */
	private String productionDate;  //生产时间
	private double densityPercent;     //D%
	private double ldCV;               //CV%
	private double waterRatio;         //含水率
	private String ranges;         //网络线（范围）
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
	public Integer getOtStatus() {
		return otStatus;
	}
	public void setOtStatus(Integer otStatus) {
		this.otStatus = otStatus;
	}
	public double getYarnlevelness() {
		return yarnlevelness;
	}
	public void setYarnlevelness(double yarnlevelness) {
		this.yarnlevelness = yarnlevelness;
	}
	public double getLinearDensity() {
		return linearDensity;
	}
	public void setLinearDensity(double linearDensity) {
		this.linearDensity = linearDensity;
	}
	public Integer getGridLine() {
		return gridLine;
	}
	public void setGridLine(Integer gridLine) {
		this.gridLine = gridLine;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}
	public double getDensityPercent() {
		return densityPercent;
	}
	public void setDensityPercent(double densityPercent) {
		this.densityPercent = densityPercent;
	}
	public double getLdCV() {
		return ldCV;
	}
	public void setLdCV(double ldCV) {
		this.ldCV = ldCV;
	}
	public double getWaterRatio() {
		return waterRatio;
	}
	public void setWaterRatio(double waterRatio) {
		this.waterRatio = waterRatio;
	}
	public String getRanges() {
		return ranges;
	}
	public void setRanges(String ranges) {
		this.ranges = ranges;
	}
	@Override
	public String toString() {
		return "Other [batchNo=" + batchNo + ", lineNo=" + lineNo + ", otStatus=" + otStatus + ", yarnlevelness="
				+ yarnlevelness + ", linearDensity=" + linearDensity + ", gridLine=" + gridLine + ", createDate="
				+ createDate + ", productionDate=" + productionDate + ", densityPercent=" + densityPercent + ", ldCV="
				+ ldCV + ", waterRatio=" + waterRatio + ", ranges=" + ranges + "]";
	}
	
}
