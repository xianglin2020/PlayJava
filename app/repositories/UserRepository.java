package repositories;

import dev.morphia.query.experimental.filters.Filters;
import it.unifi.cerm.playmorphia.PlayMorphia;
import models.User;
import org.bson.types.ObjectId;

import javax.inject.Inject;

/**
 * @author xianglin
 */
public class UserRepository {
    private final PlayMorphia playMorphia;

    @Inject
    public UserRepository(PlayMorphia playMorphia) {
        this.playMorphia = playMorphia;
    }

    public User findById(String id) {
        return playMorphia
                .datastore()
                .find(User.class)
                .filter(Filters.eq("id", new ObjectId(id)))
                .first();
    }

    public User save(User user) {
        return playMorphia
                .datastore()
                .save(user);
    }
}
