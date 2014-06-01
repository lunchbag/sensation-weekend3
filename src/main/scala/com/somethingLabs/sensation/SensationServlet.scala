package com.somethingLabs.sensation

import org.scalatra._
import scalate.ScalateSupport
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._

class SensationServlet extends SensationStack with JacksonJsonSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  }

  get("/event/:id") {
    DummyData.generate(params("id"))
  }

}


object DummyData {
  def generate(id: String) = List(
    Map("event" -> id),
    Map("derp1" -> "Andy"),
    Map("derp2" -> "Jen")
  )
}