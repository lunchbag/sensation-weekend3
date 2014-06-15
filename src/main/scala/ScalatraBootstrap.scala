import com.mongodb.casbah.MongoClientURI
import com.somethingLabs.sensation._
import org.scalatra._
import javax.servlet.ServletContext
import com.mongodb.casbah.Imports._
import scala.util.Properties

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {

    // As you can see, there's not much to do in order to get MongoDb working with Scalatra.
    // We're connecting with default settings - localhost on port 27017 -
    // by calling MongoClient() with no arguments.



    // pass a reference to the Mongo collection into your servlet when you mount it at application start:
    //context.mount(new MongoController(mongoColl), "/mongo/*")

    context.mount(new SensationServlet, "/*")
  }
}
