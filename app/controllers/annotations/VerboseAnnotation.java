package controllers.annotations;

import controllers.actions.VerboseAnnotationAction;
import play.mvc.With;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xianglin
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@With(VerboseAnnotationAction.class)
public @interface VerboseAnnotation {
    boolean value() default true;
}
