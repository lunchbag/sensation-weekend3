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

    val host = "kahana.mongohq.com"
    val port: Int = 10065
    val db = "app25917593"

    //app25917593

    val r = new ServerAddress("kahana.mongohq.com", 10065)
    val uri = MongoClientURI("mongodb://kahana.mongohq.com:10065")
    val credentials = MongoCredential.createMongoCRCredential("lunchbag", "app25917593", "baglunch" toCharArray)
    val mongoClient = MongoClient(r, List(credentials))

    val mongoColl = mongoClient(db)("Listings")

    // pass a reference to the Mongo collection into your servlet when you mount it at application start:
    context.mount(new MongoController(mongoColl), "/mongo/*")

    context.mount(new SensationServlet(mongoColl), "/*")
  }
}
