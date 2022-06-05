package controllers;

import com.typesafe.config.Config;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

/**
 * @author xianglin
 */
public class ConfigController extends Controller {
    private final Config config;

    @Inject
    public ConfigController(Config config) {
        this.config = config;
    }

    public Result config() {
        return ok(Json.toJson(config));
    }

    public play.mvc.Result index(play.mvc.Http.Request request) {
        return play.mvc.Results.ok("Got request " + request + "!");
    }
}
