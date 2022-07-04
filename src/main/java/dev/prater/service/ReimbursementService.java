package dev.prater.service;

import dev.prater.repository.ReimbursementDAO;
import dev.prater.models.ReimbursementRequest;
import dev.prater.models.UserAccount;

import java.time.LocalDateTime;
import java.util.List;

public class ReimbursementService {
	private static int defaultFinanceID = 1;
	
	private static ReimbursementDAO rDAO;
	
	public ReimbursementService(ReimbursementDAO rdao) {rDAO=rdao;}
	
	//Note: event_Type and proofType were intended to be Strings, but Javascript needed them to be longs. Use Integer.parse as a compromise
	
	//Create
	public ReimbursementRequest createRequest(int uID, ReimbursementRequest incoming) 
	{
		List<ReimbursementRequest> rL = rDAO.getUsersRequests(uID);
		ReimbursementRequest rr = incoming;
		int tally = 0;
		rr.setUserID(uID);
		if (rr.getFinanceID()==0) rr.setFinanceID(defaultFinanceID); //use if database complains about x=0
		if (rr.getEventDate().compareTo(rr.getOpenDate().plusWeeks(1L))<0) {rr.setStatusCode(7);} //close if too event isn't at least a week out
		else if (rr.getEventDate().compareTo(rr.getOpenDate().plusWeeks(2L))<0)	{rr.setDescription("SYSTEM:URGENT:NEAR_EXPIRY" + rr.getDescription());}
		
		if (rr.getReqAmount()>rr.getEventCost()*ReimbursementRequest.percentages[Integer.parseInt(rr.getEvent_Type())]) 
		{
			rr.setReqAmount(rr.getEventCost()*ReimbursementRequest.percentages[Integer.parseInt(rr.getEvent_Type())]);
			rr.setFinanceComment("SYSTEM:Request Amount Lowered; previously exceeded percentage of event cost covered.");
		}
		
		for (int i=0;i<rL.size();i++) {if (rL.get(i).getOpenDate().compareTo(rr.getOpenDate().minusYears(1L))>0) {tally += rL.get(i).getReqAmount();}}
		if (rr.getReqAmount()<(UserAccount.annualReimbursement-tally)) {;}
		else {rr.setReqAmount(UserAccount.annualReimbursement-tally); rr.setFinanceComment("SYSTEM:Request Amount Lowered; previously exceeded annual cap.");}
		rr.setReqAmount(Math.min(rr.getReqAmount(), UserAccount.annualReimbursement-tally));
		if (rr.getReqAmount()<0) {rr.setReqAmount(0);}
		rr.setCloseDate(rr.getOpenDate().plusYears(1L)); 
		return rDAO.createRequest(rr);
	}
	
	//Read
	public ReimbursementRequest getOneRequest(int rID) {return rDAO.getOneRequest(rID);}
	
	public List<ReimbursementRequest> getMultipleRequests(int id, String type)
	{
		if (type.charAt(0)=='a') {return rDAO.getAllRequests();}
		else if (type.charAt(0)=='u') {return rDAO.getUsersRequests(id);}
		else if (type.charAt(0)=='f') {return rDAO.getManagersRequests(id);}
		else {return null;}
	}

	//Update 
	public ReimbursementRequest updateRequest(int rID, ReimbursementRequest updatedReq) 
	{
		ReimbursementRequest rr = updatedReq;
		//normally I'd put logic here but frankly using JS to make inputs read-only based on account type/status code makes more sense in the long run
		//unfortunately I don't have TIME for that at the moment but such is life
		//...there is one thing that makes sense to put here though!
		if (rr.getCloseDate()!=null) 
		{
			if (LocalDateTime.now().compareTo(rr.getCloseDate())>0 || rr.getStatusCode()<4) 
			{rr.setStatusCode(4); rr.setFinanceComment("SYSTEM:Auto-Approving ancient request");} 
		}
		rr = rDAO.updateRequest(rID, rr);
		return rr;
	}
		
	//Delete

}
