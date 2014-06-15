package com.somethingLabs.sensation

import com.somethingLabs.sensation.Analyze._
import org.scalatra._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._
import com.mongodb.casbah.Imports._
import com.somethingLabs.sensation.Ticket

case class Ticket (
  vip:String,
  shuttle:String,
  fri:String,
  sat:String,
  sun:String,
  derp:String
)

case class Listing (
  date: String,
  price: String,
  buyer_or_seller: String,
  ticket: Ticket
)

class SensationServlet(mongoColl: MongoCollection) extends SensationStack with JacksonJsonSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  post("/create") {
    val b = parsedBody.children.map {
      value =>
        value.extract[Analyze.Listing]
    }
    val a = analyze(b)
    println("ok")

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

        println("k")
        println(k)

        /*val k = v map {
          case(key: String, value: String) =>
            (key, value)
        } toList

        println("k")
        println(k)*/

        val obj = MongoDBObject(List(
          ("date", key.date),
          ("ticket", caseClassToMap(key.ticket))
        ) ::: k)

        println(obj)
        mongoColl.insert(obj)
    }

    //val newObj = MongoDBObject(c)
    //println(newObj)
    //mongoColl.insert(newObj)
  }

  def caseClassToMap(ticket: Product): Map[String, String] = {
    //val values = ticket.productIterator
   val a = ticket.getClass.getDeclaredFields map {
      _.getName } zip {
      ticket.productIterator.to } toMap
   val b = a map {
     case (key, value) => (key toString, value toString)
   }
   b
  }

  get("/") {
    <html>
    <body>
      <h1>Hello, world!</h1>
      Say <a href="hello-scalate">hello to Scalate</a>.
    </body>
    </html>
  }
}