package com.hd.pojo;

public class NyLonSon extends NyLon {
	
	public NyLonSon() {
		// TODO Auto-generated constructor stub
	}
	
	public NyLonSon(String batchNo, String createDate, String lineNo, String nlFormat, Double linearDensity,
			Double yarnlevelness, Double strength, Double elongation, Double crimpContraction, Double crimpStability,
			Double boilingWater, Double oliContent, Integer gridLine, Integer nlStatus, String productionDate,
			Double densityPercent, Double ldCV, Double strengthCV, Double elongationCV, Double waterRatio,
			String ranges) {
		super(batchNo, createDate, lineNo, nlFormat, linearDensity, yarnlevelness, strength, elongation, crimpContraction,
				crimpStability, boilingWater, oliContent, gridLine, nlStatus, productionDate, densityPercent, ldCV, strengthCV,
				elongationCV, waterRatio, ranges);
		// TODO Auto-generated constructor stub
	}
	private Integer inStFlag;			//强度的偏差状态
	private Integer inStvFlag;			//强度cv的偏差状态
	private Integer inElFlag;			//伸长率的偏差状态
	private Integer inElvFalg;			//伸长率cv的偏差状态
	private Integer inBoFlag;			//沸水的偏差状态
	private Integer inOlFlag;			//含油的偏差状态
	private Integer inYeFlag;			//条干的偏差状态
	private Integer inLiFlag;			//线密度的偏差状态
	private Integer inGliFalg;			//网络度的偏差状态
	private Integer inDeFlag;			//D%的偏差状态
	private Integer inLdFlag;			//线密度CV的偏差状态
	private Integer inWrFlag;			//含水率的偏差状态
	private Integer inCcFlag;			//卷曲收缩率
	private Integer inCsFlag;			//卷曲稳定度的偏差状态
	
	
	public Integer getInStFlag() {
		return inStFlag;
	}
	public void setInStFlag(Integer inStFlag) {
		this.inStFlag = inStFlag;
	}
	public Integer getInStvFlag() {
		return inStvFlag;
	}
	public void setInStvFlag(Integer inStvFlag) {
		this.inStvFlag = inStvFlag;
	}
	public Integer getInElFlag() {
		return inElFlag;
	}
	public void setInElFlag(Integer inElFlag) {
		this.inElFlag = inElFlag;
	}
	public Integer getInElvFalg() {
		return inElvFalg;
	}
	public void setInElvFalg(Integer inElvFalg) {
		this.inElvFalg = inElvFalg;
	}
	public Integer getInBoFlag() {
		return inBoFlag;
	}
	public void setInBoFlag(Integer inBoFlag) {
		this.inBoFlag = inBoFlag;
	}
	public Integer getInOlFlag() {
		return inOlFlag;
	}
	public void setInOlFlag(Integer inOlFlag) {
		this.inOlFlag = inOlFlag;
	}
	public Integer getInYeFlag() {
		return inYeFlag;
	}
	public void setInYeFlag(Integer inYeFlag) {
		this.inYeFlag = inYeFlag;
	}
	public Integer getInLiFlag() {
		return inLiFlag;
	}
	public void setInLiFlag(Integer inLiFlag) {
		this.inLiFlag = inLiFlag;
	}
	public Integer getInGliFalg() {
		return inGliFalg;
	}
	public void setInGliFalg(Integer inGliFalg) {
		this.inGliFalg = inGliFalg;
	}
	public Integer getInDeFlag() {
		return inDeFlag;
	}
	public void setInDeFlag(Integer inDeFlag) {
		this.inDeFlag = inDeFlag;
	}
	public Integer getInLdFlag() {
		return inLdFlag;
	}
	public void setInLdFlag(Integer inLdFlag) {
		this.inLdFlag = inLdFlag;
	}
	public Integer getInWrFlag() {
		return inWrFlag;
	}
	public void setInWrFlag(Integer inWrFlag) {
		this.inWrFlag = inWrFlag;
	}
	public Integer getInCcFlag() {
		return inCcFlag;
	}
	public void setInCcFlag(Integer inCcFlag) {
		this.inCcFlag = inCcFlag;
	}
	public Integer getInCsFlag() {
		return inCsFlag;
	}
	public void setInCsFlag(Integer inCsFlag) {
		this.inCsFlag = inCsFlag;
	}
	@Override
	public String toString() {
		return "NyLonSon [inStFlag=" + inStFlag + ", inStvFlag=" + inStvFlag + ", inElFlag=" + inElFlag + ", inElvFalg="
				+ inElvFalg + ", inBoFlag=" + inBoFlag + ", inOlFlag=" + inOlFlag + ", inYeFlag=" + inYeFlag
				+ ", inLiFlag=" + inLiFlag + ", inGliFalg=" + inGliFalg + ", inDeFlag=" + inDeFlag + ", inLdFlag="
				+ inLdFlag + ", inWrFlag=" + inWrFlag + ", inCcFlag=" + inCcFlag + ", inCsFlag=" + inCsFlag + "]";
	}
}
