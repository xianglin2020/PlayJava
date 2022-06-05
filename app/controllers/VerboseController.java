package controllers;

import controllers.actions.VerboseAction;
import controllers.annotations.VerboseAnnotation;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

/**
 * @author xianglin
 */

public class VerboseController extends Controller {

    @With(VerboseAction.class)
    public Result verboseIndex() {
        return ok("It works!");
    }

    @VerboseAnnotation
    public Result verboseHome() {
        return ok("It works too!");
    }
}
