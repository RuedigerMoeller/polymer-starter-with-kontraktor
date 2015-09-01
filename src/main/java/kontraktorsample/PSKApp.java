package kontraktorsample;

import org.nustaq.kontraktor.Actor;
import org.nustaq.kontraktor.remoting.encoding.Coding;
import org.nustaq.kontraktor.remoting.encoding.SerializerType;
import org.nustaq.kontraktor.remoting.http.Http4K;
import org.nustaq.kontraktor.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by ruedi on 31.08.2015.
 */
public class PSKApp extends Actor<PSKApp> {

    public void init() {
        Log.Info(this, "Init finished");
    }

    public static void main(String[] args) throws IOException {
        File root = new File("src/main/web/client"); // expect to run with "run" as working dir

        if ( ! new File(root,"index.html").exists() ) {
            System.out.println("Please run with working dir: 'projectroot");
            System.exit(-1);
        }

        PSKApp app = AsActor(PSKApp.class);
        app.init();

        Class clazzNames[] = {
//            classes to encode without full qualified names in json

//            AddMessage.class, RemoveMessage.class, UpdateMessage.class, QueryDoneMessage.class, Record.class, MapRecord.class,
//            WebResource.class
        };
        Http4K.Build("localhost", 8888)
            .resourcePath("/")
                .elements("src/main/web/client", "src/main/web/bower_components", "src/main/web/lib")
                .allDev(true)
                .build()
            .httpAPI("/api", app)
                .coding(new Coding(SerializerType.JsonNoRef, clazzNames))
                .setSessionTimeout(30_000)
                .build()
                .build();
    }

}