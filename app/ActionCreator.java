import controllers.actions.VerboseAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.lang.reflect.Method;
import java.util.concurrent.CompletionStage;

/**
 * @author xianglin
 */
public class ActionCreator implements play.http.ActionCreator {
    private final static Logger logger = LoggerFactory.getLogger(VerboseAction.class);

    @Override
    public Action createAction(Http.Request request, Method actionMethod) {
        logger.info("createAction");
        return new Action.Simple() {
            @Override
            public CompletionStage<Result> call(Http.Request req) {
                return delegate.call(req);
            }
        };
    }
}
