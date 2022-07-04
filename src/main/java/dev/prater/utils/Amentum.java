package dev.prater.utils;

import dev.prater.controller.UserController;
import dev.prater.controller.ReimbursementController;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import static io.javalin.apibuilder.ApiBuilder.delete;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.patch;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.put;


/*for those who don't get the joke: an amentum increases javelin speed much like a sling increases rock speed*/
public class Amentum { 
	public void serverRequestHandler() {
		Javalin app = Javalin.create(config -> config.addStaticFiles("/public", Location.CLASSPATH));
		app.start(8082);
		
		app.routes(() -> {
			get((ctx) ->ctx.result("HOMEPAGE WIP"));
			path("/login", () -> {
				post(UserController::attemptLogin);
			});
			path("/logout", () -> {
				post(UserController::attemptLogout);
			});
			path("/users", () -> {
				get((ctx) -> ctx.result("USERS PAGE WIP"));
				path ("/{id0}", () -> {
					get(UserController::getUser);
					path("/requests", () -> {
						get(ReimbursementController::getUsersRequests);
						path("/{id1}", () -> {
							get(ReimbursementController::getOneRequest);
							post(ReimbursementController::createRequest);
							put(ReimbursementController::updateRequest);
						});
					});
				});
			});
			path("/managers", () -> {
				get((ctx) -> ctx.result("MANAGERS PAGE WIP"));
				path ("/{id0}", () -> {
					path("/requests", () -> {
						get(ReimbursementController::getUsersRequests);
						path("/{id1}", () -> {
							put(ReimbursementController::updateRequest);
						});
					});
				});
			});
		});
		
		app.exception(Exception.class, (e, ctx) -> {
		    ctx.status(404);
		    ctx.result("<h1>User not found</h1>");
		});
		
		//app.close();
	}
}
