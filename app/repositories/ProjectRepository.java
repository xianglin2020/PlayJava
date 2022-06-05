package repositories;

import dev.morphia.query.experimental.filters.Filters;
import it.unifi.cerm.playmorphia.PlayMorphia;
import models.Project;

import javax.inject.Inject;

/**
 * @author xianglin
 */
public class ProjectRepository {
    private final PlayMorphia playMorphia;

    @Inject
    public ProjectRepository(PlayMorphia playMorphia) {
        this.playMorphia = playMorphia;
    }

    public Project findById(String id) {
        return playMorphia
                .datastore()
                .find(Project.class)
                .filter(Filters.eq(id, id))
                .first();
    }

    public String save(Project project) {
        return playMorphia
                .datastore()
                .save(project)
                .getId();
    }
}
