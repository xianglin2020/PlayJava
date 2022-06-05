package controllers.async;

import play.libs.concurrent.Futures;
import play.libs.concurrent.HttpExecution;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import scala.concurrent.ExecutionContextExecutor;

import javax.inject.Inject;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static java.time.temporal.ChronoUnit.SECONDS;

/**
 * @author xianglin
 */
public class TimeoutController extends Controller {
    private final Futures futures;
    private final MyCustomExecutionContext myCustomExecutionContext;

    @Inject
    public TimeoutController(Futures futures, MyCustomExecutionContext myCustomExecutionContext) {
        this.futures = futures;
        this.myCustomExecutionContext = myCustomExecutionContext;
    }

    public CompletionStage<Result> index() {
        return futures.timeout(delayedResult(), Duration.ofSeconds(1)).handleAsync((result, throwable) -> internalServerError("请求超时！"));
    }

    public CompletionStage<Result> delayedResult() {
        ExecutionContextExecutor executor = HttpExecution.fromThread(myCustomExecutionContext);
        long start = System.currentTimeMillis();
        return futures.delayed(() -> CompletableFuture.supplyAsync(() -> {
            long end = System.currentTimeMillis();
            long seconds = end - start;
            return "rendered after " + seconds + " seconds";
        }, executor), Duration.of(3, SECONDS)).thenApply(Results::ok);
    }
}
