package com.somethingLabs.sensation

import scala.collection.immutable.List

object Analyze {

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

  case class GroupedListing (
    date: String,
    buyer_or_seller: String,
    ticket: Ticket
  )

  case class GroupedListingByDateTicket (
    date: String,
    ticket: Ticket
  )

  def mean(xs: List[Double]): Double = xs match {
    case Nil => 0.0
    case ys => ys.reduceLeft(_ + _) / ys.size.toDouble
  }

  def stddev(xs: List[Double], avg: Double): Double = xs match {
    case Nil => 0.0
    case ys => math.sqrt((0.0 /: ys) {
      (a,e) => a + math.pow(e - avg, 2.0)
    } / xs.size)
  }

  def derp(args: Array[String]) {
    val data = List(
      Listing(
        "2014/05/21",
        "123",
        "buyer",
        Ticket(
          true,
          true,
          true,
          false,
          true)
      ),
      Listing(
        "2014/05/22",
        "125",
        "buyer",
        Ticket(
          true,
          true,
          true,
          false,
          true)
      ),
      Listing(
        "2014/05/21",
        "111",
        "buyer",
        Ticket(
          true,
          true,
          true,
          false,
          true)
      ),
      Listing(
        "2014/05/21",
        "111",
        "seller",
        Ticket(
          true,
          true,
          true,
          false,
          true)
      ),
      Listing(
        "2014/05/21",
        "136",
        "seller",
        Ticket(
          true,
          true,
          true,
          false,
          true)
      )
    )
  }

 def analyze (data: List[Listing]) {
    val groupedByDateActionTicket = data map { x =>
      (GroupedListing.apply(x.date, x.buyer_or_seller, x.ticket), x.price)
    } groupBy ( listing => listing._1 ) map {
      case (groupedListing, list) =>
        val listOfPrices = list map ( listing => listing._2.toDouble )
        val avg = mean(listOfPrices)
        val stdDev = stddev(listOfPrices, avg)
        (groupedListing, (avg, stdDev))
    } map {
      // Rearranging without seller/buyer in the first value
      case (key, value) =>
        (GroupedListingByDateTicket.apply(key.date, key.ticket), key.buyer_or_seller, value)
    } groupBy ( listing => listing._1 ) map {
      // Regrouping
      case (key, value) =>
        val sanitizedValue = value map ( x => (x._2, x._3))
        (key, sanitizedValue)
    }
    println(groupedByDateActionTicket)
    groupedByDateActionTicket
 }
}
