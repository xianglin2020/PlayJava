package controllers;

import com.typesafe.config.Config;
import play.mvc.Controller;

import javax.inject.Inject;

/**
 * @author xianglin
 */
public class MyController extends Controller {
    private final Config config;

    @Inject
    public MyController(Config config) {
        this.config = config;
    }


    public play.mvc.Result index(play.mvc.Http.Request request) {
        return play.mvc.Results.ok("Got request " + request + "!");
    }
}
