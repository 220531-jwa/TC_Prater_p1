package dev.prater.utils;

import dev.prater.controller.UserController;

import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.delete;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.patch;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.put;


/*for those who don't get the joke: an amentum increases javelin speed much like a sling increases rock speed*/
public class Amentum { 
	public void serverRequestHandler() {
		Javalin app = Javalin.create(config -> config.enableCorsForAllOrigins());
		app.start(8082);
		//app.[request type here]("[url extension here, ex: /games/]", ctx -> {[lambda function here]});
		
		app.routes(() -> {
			get((ctx) ->ctx.result("HOMEPAGE WIP"));
			path("/login", () -> {
//				get(UserAccController::getAllUserAccounts); //this is a format reference
				post(UserController::attemptLogin);
			});
		});
		
		app.exception(Exception.class, (e, ctx) -> {
		    ctx.status(404);
		    ctx.result("<h1>User not found</h1>");
		});
		
		//app.close();
	}
}
