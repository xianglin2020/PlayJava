package controllers.security;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.Collections;

/**
 * 认证和授权
 *
 * @author xianglin
 */
public class SecurityController extends Controller {

    public Result index() {
        return ok("It's ok!");
    }

    public Result authenticate() {
        return ok("has login!").withSession(Collections.singletonMap(Security.SESSION_ID_KEY, "62810591422af676284c00d5"));
    }

    @Security.Authenticated
    public Result authenticated(Http.Request request) {
        var user = request.attrs().get(Security.CURRENT_USER);
        return ok(user.getFirstname() + " It' ok !");
    }

    @Security.Authenticated(permissions = {"user:add"})
    public Result permissions(Http.Request request) {
        var user = request.attrs().get(Security.CURRENT_USER);
        return ok(user.getFirstname() + " has permission !");
    }

    @Security.Authenticated(pathPermission = true)
    public Result pathPermission(Http.Request request) {
        var user = request.attrs().get(Security.CURRENT_USER);
        return ok(user.getFirstname() + " has pathPermission !");
    }
}
