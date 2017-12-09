package com.hd.pojo;

/**
 * 该类是含油率的实体类
 * 
 * @author Bob
 *
 */
public class OliContent {	
	
	private String batchNo; 	// 产品的批号
	private String lineNo; 		// 产品的线位号
	private int ocStatus;		//产品的状态 0:代表未修改,1:代表复测数据,21:代表研究加测数据,31:代表车间加测数据,41:异常丝
	private int oliContent; 	// 产品的含油率
	private String createDate;	// 表单的生成时间
	
	
	public int getOcStatus() {
		return ocStatus;
	}
	public void setOcStatus(int ocStatus) {
		this.ocStatus = ocStatus;
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
	public int getOliContent() {
		return oliContent;
	}
	public void setOliContent(int oliContent) {
		this.oliContent = oliContent;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
	@Override
	public String toString() {
		return "OliContent [batchNo=" + batchNo + ", lineNo=" + lineNo + ", ocStatus=" + ocStatus + ", oliContent="
				+ oliContent + ", createDate=" + createDate + "]";
	}

}
