package controllers;

import models.Project;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.ProjectRepository;
import repositories.UserRepository;

import javax.inject.Inject;

/**
 * 测试 MongoDB 操作
 *
 * @author xianglin
 */
public class MongoController extends Controller {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Inject
    public MongoController(UserRepository userRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public Result getUser(String id) {
        return ok(Json.toJson(userRepository.findById(id)));
    }

    public Result saveUser() {
        User user = new User();
        user.setFirstname("Xiang");
        user.setLastname("Lin");
        user.setEmail("mail@mail.com");
        return ok(Json.toJson(userRepository.save(user)));
    }

    public Result getProject(String id) {
        return ok(Json.toJson(projectRepository.findById(id)));
    }

    public Result saveProject() {
        Project project = new Project();
        project.setId("projectId");
        project.setName("projectName");
        return ok(Json.toJson(projectRepository.save(project)));
    }
}
