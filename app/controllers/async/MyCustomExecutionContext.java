package controllers.async;

import akka.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

import javax.inject.Inject;

/**
 * @author xianglin
 */
public class MyCustomExecutionContext extends CustomExecutionContext {
    @Inject
    public MyCustomExecutionContext(ActorSystem actorSystem) {
        super(actorSystem, "blocking-io-dispatcher");
    }
}
