package com.hd.pojo;

public class StrElongation {
	
	private String batchNo;		//批号
	private String lineNo;		//线位号
	private String seFormat;		//规格
	private int seStatus;		//产品的状态 0:代表未修改,1:代表复测数据,21:代表研究加测数据,31:代表车间加测数据,41:异常丝
	private int strength;		//强度
	private int elongation;		//伸长率
	private String createDate;	//创建表单时间

	public String getSeFormat() {
		return seFormat;
	}
	
	public void setSeFormat(String seFormat) {
		this.seFormat = seFormat;
	}
	
	public int getSeStatus() {
		return seStatus;
	}

	public void setSeStatus(int seStatus) {
		this.seStatus = seStatus;
	}

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
	
	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getElongation() {
		return elongation;
	}

	public void setElongation(int elongation) {
		this.elongation = elongation;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "StrElongation [batchNo=" + batchNo + ", lineNo=" + lineNo + ", seFormat=" + seFormat + ", seStatus="
				+ seStatus + ", strength=" + strength + ", elongation=" + elongation + ", createDate=" + createDate
				+ "]";
	}
}
