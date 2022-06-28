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
		String test = ctx.body();
		UserAccount visitor = ctx.bodyAsClass(UserAccount.class);
//		uS.login(visitor.getUsername(), visitor.getPasskey());
		UserAccount friend = uS.login(visitor.getUsername(), visitor.getPasskey());
		if (friend != null) {ctx.status(200); ctx.json(friend);}
		else {ctx.status(404); ctx.result("Login failed, double-check username and password.");}
	}
}
