package controllers;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

/**
 * 操作 Session 和 Flash
 *
 * @author xianglin
 */
public class SessionAndFlashController extends Controller {


    public Result addSession(Http.Request request) {
        // 添加 Session
        return redirect("/session/read").addingToSession(request, "connected", "user@gmail.com");
    }

    public Result readSession(Http.Request request) {
        // 读取 Session
        return request
                .session()
                .get("connected")
                .map(user -> ok("Hello " + user))
                .orElseGet(() -> unauthorized("Oops, you are not connected"));
    }

    public Result clearSession() {
        // 清除 Session
        return redirect("/session/read").withNewSession();
    }

    public Result addFlash() {
        return redirect("/flash/read").flashing("success", "The item has been created");
    }

    public Result readFlash(Http.Request request) {
        return ok(request.flash().get("success").orElse("Welcome!"));
    }
}
