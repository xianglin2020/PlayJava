package controllers.async;

import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * HTTP 异步编程
 *
 * @author xianglin
 */
public class AsyncController extends Controller {
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public AsyncController(HttpExecutionContext httpExecutionContext) {
        this.httpExecutionContext = httpExecutionContext;
    }

    public CompletionStage<Result> index() {
        CompletionStage<Integer> promiseOfInt = CompletableFuture.supplyAsync(this::intensiveComputation, httpExecutionContext.current());
        return promiseOfInt.thenApply(integer -> ok(String.valueOf(integer)));
    }

    private Integer intensiveComputation() {
        int value = Integer.MIN_VALUE;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            value++;
        }
        return value;
    }
}
