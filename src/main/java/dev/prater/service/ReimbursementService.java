package dev.prater.service;

import dev.prater.repository.ReimbursementDAO;
import dev.prater.models.ReimbursementRequest;

import java.util.List;

public class ReimbursementService {
	private static int defaultFinanceID = 1;
	
	private static ReimbursementDAO rDAO;
	
	public ReimbursementService(ReimbursementDAO rdao) {rDAO=rdao;}
	
	//Note: event_Type and proofType were intended to be Strings, but Javascript needed them to be longs. Use Integer.parse as a compromise
	
	//Create
	public ReimbursementRequest createRequest(int uID, ReimbursementRequest incoming) 
	{
		ReimbursementRequest rr = incoming;
		rr.setUserID(uID);
		//rr.setFinanceID(defaultFinanceID); //use if database complains about x=0
		rr.setReqAmount(rr.getEventCost()*ReimbursementRequest.percentages[Integer.parseInt(rr.getEvent_Type())]);
		//Auto-closes ticket if EventDate is in the past, or within 1 week of expiring. If within 2 weeks, 
		//req.description = "SYSTEM:URGENT:NEAR_EXPIRY" + req.description; 
		//look at rDAO.getUsersRequests(uID), tally rList, make sure it's less than user.annual, else lower reqAmount to what's left and 
		// output a message into rr.financeComment to let them know it was adjusted. 
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
	
	//Delete

}
