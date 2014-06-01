import com.twitter.scalding._

class AnalyzePrices(args : Args) extends Job(args) {
  TextLine( "../sensation/firstcity/dummy.txt" )
    .map('line -> ('action, 'price)) {
      line : String =>
        val split = line.split("""\s+""")
        (split(0), split(1))
    }
    .groupBy('action) {
      _.sizeAveStdev('price -> ('count, 'meanAge, 'stdevAge))
    }
    .write (Tsv( "../sensation/firstcity/output.tsv" ))
}

