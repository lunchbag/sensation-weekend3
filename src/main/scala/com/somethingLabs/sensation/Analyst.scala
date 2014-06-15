package com.somethingLabs.sensation

import scala.collection.immutable.List

object Analyze {

  case class Ticket (
    vip:String,
    shuttle:String,
    fri:String,
    sat:String,
    sun:String
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

  case class buyerOrSellerMeanStdMean (
    buyer_or_seller: String,
    mean: Double,
    std_dev: Double,
    quantity: Int
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


 def analyze (data: List[Listing]): Map[GroupedListingByDateTicket, List[buyerOrSellerMeanStdMean]] = {
   val groupedByDateActionTicket = data map { x =>
      (GroupedListing.apply(x.date, x.buyer_or_seller, x.ticket), x.price)
    } groupBy ( listing => listing._1 ) map {
      case (groupedListing, list) =>
        val listOfPrices = list map ( listing => listing._2.toDouble )
        val avg = mean(listOfPrices)
        val stdDev = stddev(listOfPrices, avg)
        (groupedListing, (avg, stdDev, listOfPrices length))
    } map {
      // Rearranging without seller/buyer in the first value
      case (key, value) =>
        (GroupedListingByDateTicket.apply(key.date, key.ticket), key.buyer_or_seller, value)
    } groupBy ( listing => listing._1 ) map {
      // Regrouping
      case (key, value) =>
        val sanitizedValue = value map ( x =>
          buyerOrSellerMeanStdMean.apply(x._2, x._3._1, x._3._2, x._3._3)
        )
        (key, sanitizedValue.toList)
    }
    groupedByDateActionTicket
 }
}