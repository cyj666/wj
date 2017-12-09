package com.hd.pojo;

public class PercentCrimp {
	
	private String batchNo;				//批号
	private String lineNo;				//线位号
	private int pcStatus;				//产品的状态 0:代表未修改,1:代表复测数据,21:代表研究加测数据,31:代表车间加测数据,41:异常丝
	private int crimpContraction;		//卷曲收缩率
	private int crimpStability;			//卷曲稳定度
	private String createDate;			//创建日期
	
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
	public int getPcStatus() {
		return pcStatus;
	}
	public void setPcStatus(int pcStatus) {
		this.pcStatus = pcStatus;
	}
	public int getCrimpContraction() {
		return crimpContraction;
	}
	public void setCrimpContraction(int crimpContraction) {
		this.crimpContraction = crimpContraction;
	}
	public int getCrimpStability() {
		return crimpStability;
	}
	public void setCrimpStability(int crimpStability) {
		this.crimpStability = crimpStability;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	@Override
	public String toString() {
		return "PercentCrimp [batchNo=" + batchNo + ", lineNo=" + lineNo + ", pcStatus=" + pcStatus
				+ ", crimpContraction=" + crimpContraction + ", crimpStability=" + crimpStability + ", createDate="
				+ createDate + "]";
	}
	
}
