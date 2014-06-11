package com.somethingLabs.sensation

import com.somethingLabs.sensation.Analyze._
import org.scalatra._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._

case class Ticket (
  vip:Boolean,
  shuttle:Boolean,
  fri:Boolean,
  sat:Boolean,
  sun:Boolean
)

case class Listing (
  date: String,
  price: String,
  buyer_or_seller: String,
  ticket: Ticket
)

class SensationServlet extends SensationStack with JacksonJsonSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  post("/create") {
    val b = parsedBody.children.map {
      value =>
        value.extract[Analyze.Listing]
    }
    analyze(b)
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