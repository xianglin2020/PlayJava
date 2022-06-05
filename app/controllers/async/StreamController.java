package controllers.async;

import akka.NotUsed;
import akka.japi.function.Function;
import akka.stream.CompletionStrategy;
import akka.stream.OverflowStrategy;
import akka.stream.javadsl.FileIO;
import akka.stream.javadsl.Source;
import akka.util.ByteString;
import play.http.HttpEntity;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.ResponseHeader;
import play.mvc.Result;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Optional;

/**
 * @author xianglin
 */
public class StreamController extends Controller {
    public Result httpResponse() {
        return new Result(
                new ResponseHeader(Http.Status.OK, Collections.emptyMap()),
                new HttpEntity.Strict(ByteString.fromString("It's work!"), Optional.of(Http.MimeTypes.TEXT))
        );
    }


    public Result index() throws IOException {
        var path = Paths.get("/home/xianglin/Downloads/20220504100611.json");
        var source = FileIO.fromPath(path);

        // compute the response size
        var contentLength = Optional.of(Files.size(path));

        return new Result(
                new ResponseHeader(Http.Status.OK, Collections.emptyMap()),
                new HttpEntity.Streamed(source, contentLength, Optional.of(Http.MimeTypes.TEXT))
        );
    }

    public Result file() {
        return ok(new File("/home/xianglin/Downloads/20220504100611.json"), Optional.of("示例文件.json")).as(Http.MimeTypes.BINARY);
    }

    public Result fileToDisplay() {
        return ok(new File("/home/xianglin/Downloads/20220504100611.json"), false);
    }

    public Result fileStream() throws FileNotFoundException {
        return ok(new FileInputStream("/home/xianglin/Downloads/20220504100611.json"));
    }

    public Result trunked() {
        Source<ByteString, ?> trunked = Source.<ByteString>actorRef(256, OverflowStrategy.dropNew())
                .mapMaterializedValue(
                        sourceActor -> {
                            sourceActor.tell(ByteString.fromString("first"), null);
                            sourceActor.tell(ByteString.fromString("second"), null);
                            sourceActor.tell(ByteString.fromString("third"), null);
                            sourceActor.tell(ByteString.fromString("fourth"), null);
                            return NotUsed.getInstance();
                        }
                );
        return ok().chunked(trunked);
    }


}
