package dev.prater.models;

import java.time.LocalDateTime;

public class ReimbursementRequest {
	//University Courses 80%, Seminars 60%, Certification Preparation Classes 75%, Certification 100%, Technical Training 90%, Other 30%
	//user cannot SET requested reimbursement amount to be > (1000-pendingReimbursements-awardedReimbursements), but may receive in excess. 
	public static double[] percentages = {0.8,0.6,0.75,1,0.9,0.3};
	public static String[] types = {"University Course","Seminar","Certification Prep Class","Certification","Technical Training","Other"};
	
	int requestID;
	int userID;
	int financeID;
	int statusCode; //index for statusStates, set by financial manager
	double eventCost;
	double reqAmount;
	double approvedAmount;
	double amountExceeded;
	String description;
	String location; 
	String justification; 
	String event_Type; //was eventType, but javascript refused to read that in JSONs for some reason
	String proofType; //Letter grade, percent grade, pass/fail, presentation, etc.
	String proofGrade; //A-F for Letter grade, pass/fail for pass/fail, delivered/pending for presentation, etc.
	String financeComment;
	LocalDateTime eventDate;
	LocalDateTime openDate;
	LocalDateTime closeDate;
	
	public ReimbursementRequest() {;}
	public ReimbursementRequest(int requestID,int userID,int fID,int sCode,double eventCost,double reqAmount,double aAmount,double aExceeded,String desc,
			String location,String justification,String eventType,String pType,String pGrade,String fComment,LocalDateTime eventDate,LocalDateTime oDate,
			LocalDateTime cDate)
	{
		this.requestID = requestID; this.userID = userID; this.financeID=fID; this.statusCode = sCode; 
		this.eventCost = eventCost; this.reqAmount = reqAmount; this.approvedAmount=aAmount; this.amountExceeded=aExceeded;
		this.description = desc; this.location=location; this.justification=justification; this.event_Type = eventType;
		this.proofType=pType; this.proofGrade=pGrade; this.financeComment=fComment;
		this.eventDate = eventDate; this.openDate = oDate; this.closeDate = cDate;
	}
	
	public int getRequestID() {return requestID;}
	public int getUserID() {return userID;}
	public int getFinanceID() {return financeID;}
	public int getStatusCode() {return statusCode;}
	public double getEventCost() {return eventCost;}
	public double getReqAmount() {return reqAmount;}
	public double getApprovedAmount() {return approvedAmount;}
	public double getAmountExceeded() {return amountExceeded;}
	public String getProofType() {return proofType;}
	public String getProofGrade() {return proofGrade;}
	public String getFinanceComment() {return financeComment;}
	public String getDescription() {return description;}
	public String getLocation() {return location;}
	public String getJustification() {return justification;}
	public String getEvent_Type() {return event_Type;}
	public LocalDateTime getOpenDate() {return openDate;}
	public LocalDateTime getCloseDate() {return closeDate;}
	public LocalDateTime getEventDate() {return eventDate;}
	
	public void setRequestID(int requestID) {this.requestID = requestID;}
	public void setUserID(int userID) {this.userID = userID;}
	public void setFinanceID(int financeID) {this.financeID = financeID;}
	public void setStatusCode(int statusCode) {this.statusCode = statusCode;}
	public void setEventCost(double eventCost) {this.eventCost = eventCost;}
	public void setReqAmount(double reqAmount) {this.reqAmount = reqAmount;}
	public void setApprovedAmount(double approvedAmount) {this.approvedAmount = approvedAmount;}
	public void setAmountExceeded(double amountExceeded) {this.amountExceeded = amountExceeded;}
	public void setProofType(String proofType) {this.proofType = proofType;}
	public void setProofGrade(String proofGrade) {this.proofGrade = proofGrade;}
	public void setFinanceComment(String financeComment) {this.financeComment = financeComment;}
	public void setDescription(String description) {this.description = description;}
	public void setLocation(String location) {this.location = location;}
	public void setJustification(String justification) {this.justification = justification;}
	public void setEvent_Type(String event_Type) {this.event_Type=event_Type;}
	public void setOpenDate(LocalDateTime openDate) {this.openDate = openDate;}
	public void setCloseDate(LocalDateTime closeDate) {this.closeDate = closeDate;}
	public void setEventDate(LocalDateTime eventDate) {this.eventDate = eventDate;}

	@Override
	public String toString() {
		return "ReimbursementRequest [requestID=" + requestID + ", userID=" + userID + ", financeID=" + financeID
				+ ", statusCode=" + statusCode + ", eventCost=" + eventCost + ", reqAmount=" + reqAmount
				+ ", approvedAmount=" + approvedAmount + ", amountExceeded=" + amountExceeded + ", description="
				+ description + ", location=" + location + ", justification=" + justification + ", eventType="
				+ event_Type + ", proofType=" + proofType + ", proofGrade=" + proofGrade + ", financeComment="
				+ financeComment + ", eventDate=" + eventDate + ", openDate="+ openDate + ", closeDate=" + closeDate + "]";
	}
	
}
