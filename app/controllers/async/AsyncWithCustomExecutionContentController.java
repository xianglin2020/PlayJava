package controllers.async;

import play.libs.concurrent.HttpExecution;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author xianglin
 */
public class AsyncWithCustomExecutionContentController extends Controller {
    private final MyCustomExecutionContext myCustomExecutionContext;

    @Inject
    public AsyncWithCustomExecutionContentController(MyCustomExecutionContext myCustomExecutionContext) {
        this.myCustomExecutionContext = myCustomExecutionContext;
    }

    public CompletionStage<Result> index() {
        var executor = HttpExecution.fromThread(myCustomExecutionContext);
        return CompletableFuture.supplyAsync(() -> ok("It's work!"), executor);
    }
}
