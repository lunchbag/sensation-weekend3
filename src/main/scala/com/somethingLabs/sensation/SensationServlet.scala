package com.somethingLabs.sensation

import com.somethingLabs.sensation.Analyze._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._
import com.mongodb.casbah.Imports._
import org.scalatra.Ok

class SensationServlet extends SensationStack with JacksonJsonSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats

  val db = "app25917593"
  val addr = new ServerAddress("kahana.mongohq.com", 10065)
  val credentials = MongoCredential.createMongoCRCredential("lunchbag", db, "baglunch" toCharArray)
  val mongoClient = MongoClient(addr, List(credentials))
  val mongoColl = mongoClient(db)("Listings")

  before() {
    contentType = formats("json")
  }

  post("/create") {
    val b = parsedBody.children.map {
      value =>
        value.extract[Listing]
    }
    val a = analyze(b)

    a.map {
      case (key, value) =>
        // deconstruct buyer/seller status, std dev, and mean
        val v = value map { x: buyerOrSellerMeanStdMean =>
          caseClassToMap(x)
        }

        /*
        * Current:
        * List(
        *   Map(
        *     buyer_or_seller -> buyer,
        *     std_dev -> 432.0,
        *     mean -> 0.0),
        *   Map(
        *     buyer_or_seller -> seller,
        *     std_dev -> 475.0,
        *     mean -> 0.0
        *   )
        * )
        * */

        /*
        * Need it in this format:
        *   "buyer": Map("std_dev" -> XXX, "mean -> YYY)
        *   "seller": Map("std_dev" -> XXX, "mean -> YYY)
        * */

         val k = v map {
           case (m: Map[String, String]) =>
            m("buyer_or_seller") -> Map(
              "std_dev" -> m("std_dev"),
              "mean" -> m("mean"),
              "quantity" -> m("quantity")
            )
         } toList

        val obj = MongoDBObject(List(
          ("date", key.date),
          ("ticket", caseClassToMap(key.ticket))
        ) ::: k)

        mongoColl.insert(obj)
    }
    Ok()
  }

  def caseClassToMap(ticket: Product): Map[String, String] = {
   val a = ticket.getClass.getDeclaredFields map {
      _.getName } zip {
      ticket.productIterator.to } toMap
   val b = a map {
     case (key, value) => (key toString, value toString)
   }
   b
  }

  get("/") { }
}