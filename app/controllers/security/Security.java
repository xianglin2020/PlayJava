package controllers.security;

import models.User;
import play.libs.typedmap.TypedKey;
import play.mvc.Action;
import play.mvc.Http.Request;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.With;
import repositories.UserRepository;

import javax.inject.Inject;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Defines several security helpers.
 *
 * @author xianglin
 */
public class Security {

    public static final TypedKey<User> CURRENT_USER = TypedKey.create("currentUser");

    public static final String SESSION_ID_KEY = "session_id_key";

    /**
     * Wraps the annotated action in an {@link AuthenticatedAction}.
     */
    @With(AuthenticatedAction.class)
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Authenticated {
        String[] permissions() default {};

        boolean pathPermission() default false;
    }

    /**
     * Wraps another action, allowing only authenticated HTTP requests.
     *
     * <p>The user name is retrieved from the session cookie, and added to the HTTP request's <code>
     * username</code> attribute.
     */
    public static class AuthenticatedAction extends Action<Authenticated> {
        private final Authenticator authenticator;

        @Inject
        public AuthenticatedAction(Authenticator authenticator) {
            this.authenticator = authenticator;
        }

        public CompletionStage<Result> call(final Request req) {
            Optional<User> userOptional = this.authenticator.getUser(req);
            if (userOptional.isEmpty()) {
                return CompletableFuture.completedFuture(authenticator.onUnauthorized(req));
            }

            List<String> permissionList = Arrays.asList(configuration.permissions());
            if (configuration.pathPermission()) {
                permissionList = Collections.singletonList(req.path());
            }
            User user = userOptional.get();
            if (!this.authenticator.hasPermission(req, user, permissionList)) {
                return CompletableFuture.completedFuture(authenticator.onNeedPermissions(req));
            }
            return delegate.call(req.addAttr(CURRENT_USER, user));
        }
    }

    /**
     * Handles authentication.
     */
    public static class Authenticator extends Results {
        private final UserRepository userRepository;

        @Inject
        public Authenticator(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        /**
         * Retrieves the username from the HTTP request; the default is to read from the session cookie.
         *
         * @param req the current request
         * @return the username if the user is authenticated.
         */
        public Optional<User> getUser(Request req) {
            return req.session()
                    .get(SESSION_ID_KEY)
                    .map(userRepository::findById);
        }

        /**
         * @param req  the current request
         * @param user the current user
         * @return hasPermission
         */
        public boolean hasPermission(Request req, User user, List<String> permissionList) {
            var permissions = Arrays.asList("user:add", "user:delete", "user:list", "/security/pathPermission");
            return permissions.containsAll(permissionList);
        }

        /**
         * Generates an alternative result if the user is not authenticated; the default a simple '401
         * Not Authorized' page.
         *
         * @param req the current request
         * @return a <code>401 Not Authorized</code> result
         */
        public Result onUnauthorized(Request req) {
            return unauthorized("onUnauthorized");
        }

        public Result onNeedPermissions(Request req) {
            return forbidden("onNeedPermissions");
        }
    }
}
