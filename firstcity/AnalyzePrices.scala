import com.twitter.scalding._

class AnalyzePrices(args : Args) extends Job(args) {
  TextLine( "../sensation/firstcity/dummy.txt" )
    .map('line -> ('date, 'action, 'price)) {
      line : String =>
        val split = line.split("""\s+""")
        (split(0), split(1), split(2))
    }
    .groupBy('date) {
      _.sizeAveStdev('price -> ('count, 'averagePrice, 'stdevPrice))
    }
    .write (Tsv( "../sensation/firstcity/output.tsv" ))
}



