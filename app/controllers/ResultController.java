package controllers;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.time.Duration;

/**
 * 操作返回值
 *
 * @author xianglin
 */
public class ResultController extends Controller {

    public Result contentType() {
        String json = "{\"success\":\"true\"}";
        return ok(json).as(Http.MimeTypes.JSON);
    }

    public Result header() {
        return ok("Add headers").withHeader("headerName", "headerValue");
    }

    public Result cookie(Http.Request request) {
        String value = request.getCookie("cookieName").orElseGet(() -> Http.Cookie.builder("cookieName", "cookieValue").build()).value();
        return ok("Add cookies").withCookies(Http.Cookie
                .builder("cookieName", value)
                .withMaxAge(Duration.ofMinutes(1L))
                .build());
    }
}
