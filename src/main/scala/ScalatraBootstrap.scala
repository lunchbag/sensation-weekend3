import com.somethingLabs.sensation._
import org.scalatra._
import javax.servlet.ServletContext
import com.mongodb.casbah.Imports._

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {

    // As you can see, there's not much to do in order to get MongoDb working with Scalatra.
    // We're connecting with default settings - localhost on port 27017 -
    // by calling MongoClient() with no arguments.
    val mongoClient =  MongoClient()
    val mongoColl = mongoClient("app25917593")("test_listings")

    // pass a reference to the Mongo collection into your servlet when you mount it at application start:
    context.mount(new MongoController(mongoColl), "/*")

    context.mount(new SensationServlet, "/*")
  }
}
