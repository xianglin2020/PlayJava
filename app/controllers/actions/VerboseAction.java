package controllers.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

/**
 * @author xianglin
 */
public class VerboseAction extends Action.Simple {
    private final static Logger logger = LoggerFactory.getLogger(VerboseAction.class);
    @Override
    public CompletionStage<Result> call(Http.Request req) {
        logger.info("Calling action for {}", req);
        return delegate.call(req);
    }
}
