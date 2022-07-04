package dev.prater.controller;

import dev.prater.service.ReimbursementService;
import dev.prater.repository.ReimbursementDAO;
import dev.prater.models.ReimbursementRequest;

import java.util.List;
import io.javalin.http.Context;

public class ReimbursementController {
	private static ReimbursementDAO rDAO = new ReimbursementDAO();
	private static ReimbursementService rS = new ReimbursementService(rDAO);
	public ReimbursementController(ReimbursementService rserve) {rS=rserve;}
	
	public static void createRequest(Context ctx)
	{
		ReimbursementRequest incoming = ctx.bodyAsClass(ReimbursementRequest.class);
		int uID = Integer.parseInt(ctx.pathParam("{id0}"));
		incoming = rS.createRequest(uID, incoming);
		if (incoming==null) {ctx.status(404);} else {ctx.status(201);}
		ctx.json(incoming);
		
	}
	
	public static void getOneRequest(Context ctx) 
	{
		ReimbursementRequest sanityCheck = rS.getOneRequest(Integer.parseInt(ctx.pathParam("{id1}")));
		if (sanityCheck==null) {ctx.status(404);} else {ctx.status(200);}
		ctx.json(sanityCheck);
	}
	
	public static void getUsersRequests(Context ctx)
	{
		String listType = "";
		int id0 = Integer.parseInt(ctx.pathParam("{id0}"));
		List<ReimbursementRequest> rRL = null;
		if (id0 == 0) {listType="all";}
		else if (ctx.path().contains("users")) {listType="uID";}
		else if (ctx.path().contains("managers")) {listType="fID";}
		rRL = rS.getMultipleRequests(id0, listType);
//		rRL = rDAO.getAllRequests();
		if (rRL==null) {ctx.status(404);} else {ctx.status(200);}
		ctx.json(rRL);
	}

	
	public static void updateRequest(Context ctx)
	{
		ReimbursementRequest updatedReq = ctx.bodyAsClass(ReimbursementRequest.class);
		int rID = Integer.parseInt(ctx.pathParam("{id1}"));
		updatedReq = rS.updateRequest(rID, updatedReq);
		if (updatedReq==null) {ctx.status(404);} else {ctx.status(200);}
		ctx.json(updatedReq);
	}
}
