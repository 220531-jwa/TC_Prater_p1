package dev.prater.controller;

import dev.prater.service.UserService;
import dev.prater.repository.UserDAO;
import dev.prater.models.UserAccount;

import io.javalin.http.Context;

public class UserController {
	private static UserDAO uDAO = new UserDAO();
	private static UserService uS = new UserService(uDAO);
	public UserController(UserService userve) {UserController.uS=userve;}
	
	public static void attemptLogin(Context ctx)
	{
		UserAccount visitor = ctx.bodyAsClass(UserAccount.class);
		UserAccount friend = uS.login(visitor.getUsername(), visitor.getPasskey());
		if (friend != null) 
		{
			ctx.status(200); 
			ctx.json(friend);
			ctx.sessionAttribute("loginAs", friend.getUsername());
			ctx.sessionAttribute("isManager",friend.isFinancialManager());
		}
		else 
		{
			ctx.status(404); 
			ctx.json("{failureState: userNotFound}");
		}
	}
	
	public static void attemptLogout(Context ctx) {ctx.status(200);	ctx.req.getSession().invalidate();}
	
	public static void getUser(Context ctx) 
	{
		try {
			UserAccount friend = uS.getUserAccount(Integer.parseInt(ctx.pathParam("{id0}")));
			ctx.json(friend);
			ctx.status(200);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.status(404);
		}
	}
}
