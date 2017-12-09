package com.hd.pojo;

public class NyLon {
	
	private String batchNo;				//批号
	private String createDate;			//测量时间
	private String lineNo;				//线位号
	private String nlFormat;			//规格
	private Double linearDensity;			//线密度
	private Double yarnlevelness;			//条干U%
	private Double strength;				//强度(平均值)
	private Double elongation;				//伸长率(平均值)
	private Double crimpContraction;		//卷曲收缩率
	private Double crimpStability;			//卷曲稳定度
	private Double boilingWater;			//沸水
	private Double oliContent;				//含油率
	private Integer gridLine;				//网络线(平均)
	private Integer nlStatus;				//状态
	
	/**
	 * 后续添加
	 * @return
	 */
	private String productionDate;      //生产时间
	private Double densityPercent;     //线密度的D%
	private Double ldCV;               //线密度的CV%
	private Double strengthCV;         //强度的CV%
	private Double elongationCV;       //伸长率的CV%
	private Double waterRatio;         //含水率
	private String ranges;              //网络线（范围）
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getLineNo() {
		return lineNo;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	public String getNlFormat() {
		return nlFormat;
	}
	public void setNlFormat(String nlFormat) {
		this.nlFormat = nlFormat;
	}
	public Double getLinearDensity() {
		return linearDensity;
	}
	public void setLinearDensity(Double linearDensity) {
		this.linearDensity = linearDensity;
	}
	public Double getYarnlevelness() {
		return yarnlevelness;
	}
	public void setYarnlevelness(Double yarnlevelness) {
		this.yarnlevelness = yarnlevelness;
	}
	public Double getStrength() {
		return strength;
	}
	public void setStrength(Double strength) {
		this.strength = strength;
	}
	public Double getElongation() {
		return elongation;
	}
	public void setElongation(Double elongation) {
		this.elongation = elongation;
	}
	public Double getCrimpContraction() {
		return crimpContraction;
	}
	public void setCrimpContraction(Double crimpContraction) {
		this.crimpContraction = crimpContraction;
	}
	public Double getCrimpStability() {
		return crimpStability;
	}
	public void setCrimpStability(Double crimpStability) {
		this.crimpStability = crimpStability;
	}
	public Double getBoilingWater() {
		return boilingWater;
	}
	public void setBoilingWater(Double boilingWater) {
		this.boilingWater = boilingWater;
	}
	public Double getOliContent() {
		return oliContent;
	}
	public void setOliContent(Double oliContent) {
		this.oliContent = oliContent;
	}
	public Integer getGridLine() {
		return gridLine;
	}
	public void setGridLine(Integer gridLine) {
		this.gridLine = gridLine;
	}
	public Integer getNlStatus() {
		return nlStatus;
	}
	public void setNlStatus(Integer nlStatus) {
		this.nlStatus = nlStatus;
	}
	public String getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}
	public Double getDensityPercent() {
		return densityPercent;
	}
	public void setDensityPercent(Double densityPercent) {
		this.densityPercent = densityPercent;
	}
	public Double getLdCV() {
		return ldCV;
	}
	public void setLdCV(Double ldCV) {
		this.ldCV = ldCV;
	}
	public Double getStrengthCV() {
		return strengthCV;
	}
	public void setStrengthCV(Double strengthCV) {
		this.strengthCV = strengthCV;
	}
	public Double getElongationCV() {
		return elongationCV;
	}
	public void setElongationCV(Double elongationCV) {
		this.elongationCV = elongationCV;
	}
	public Double getWaterRatio() {
		return waterRatio;
	}
	public void setWaterRatio(Double waterRatio) {
		this.waterRatio = waterRatio;
	}
	public String getRanges() {
		return ranges;
	}
	public void setRanges(String ranges) {
		this.ranges = ranges;
	}
	
}
