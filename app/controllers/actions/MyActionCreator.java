package controllers.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.http.ActionCreator;
import play.mvc.Action;
import play.mvc.Http;

import java.lang.reflect.Method;

/**
 * @author xianglin
 */
public class MyActionCreator implements ActionCreator {
    private final static Logger logger = LoggerFactory.getLogger(VerboseAction.class);

    @Override
    public Action<Void> createAction(Http.Request request, Method actionMethod) {
        return null;
    }
}
