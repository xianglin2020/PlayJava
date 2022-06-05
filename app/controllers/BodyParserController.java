package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

/**
 * @author xianglin
 */
public class BodyParserController extends Controller {
    @play.mvc.BodyParser.Of(BodyParser.Json.class)
    public Result json(Http.Request request) {
        JsonNode jsonNode = request.body().asJson();
        return ok("Got name: " + jsonNode.get("name").asText());
    }

    @BodyParser.Of(BodyParser.MultipartFormData.class)
    public Result file(Http.Request request) {
        var multipartFormData = request.body().asMultipartFormData();
        var file = multipartFormData.getFile("file");
        var fileSize = file.getFileSize();
        var json = Json.newObject().put("success", true).put("fileSize", fileSize);
        return ok(json);
    }
}
