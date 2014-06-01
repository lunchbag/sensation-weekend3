Secondary market analysis for tickets to local music events by [Andy Jiang](http://twitter.com/andyjiang) and [Jennifer Yip](http://twitter.com/lunchbag)

#### /firstcity
This is a scalding job responsible for the heavy lifting of the massive amount of data we'll be analyzing. It will calculate things such as supply/demand ratio, average prices by location, standard deviation.

It will return clear-cut, human-interprettable data.

*Requires [Twitter Scalding](https://github.com/twitter/scalding) to run.*

    scripts/scald.rb --local ../sensation/firstcity/AnalyzePrices.scala

#### /bottlerock
This is our web scraper to grab supply/demand information for certain events. It's responsible for sanitizing the data as well. /firstcity consumes data from /bottlerock.