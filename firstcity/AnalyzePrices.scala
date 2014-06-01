import com.twitter.scalding._

class AnalyzePrices(args : Args) extends Job(args) {
  // This is reading for an auto-generated text file of dummy data. See
  // generateDummyData.rb for the format of this
  TextLine( "../sensation/firstcity/dummy.txt" )
    // Map each line of this file to 3 columns: date, action, and price
    .map('line -> ('date, 'action, 'price)) {
      line : String =>
        val split = line.split("""\s+""")
        (split(0), split(1), split(2))
        // The fields (date, action, and price) are now all mapped to the
        // appropriate column data
    }
    // Grouping by date and action, we calculate the size, average price and
    // standard deviation in price for each of these groupings
    .groupBy('date, 'action) {
      _.sizeAveStdev('price -> ('count, 'averagePrice, 'stdevPrice))
    }
    // Write the output to a tsv file
    .write (Tsv( "../sensation/firstcity/output.tsv" ))
}



