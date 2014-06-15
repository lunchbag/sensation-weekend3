Secondary market analysis for tickets to local music events by [Andy Jiang](http://twitter.com/andyjiang) and [Jennifer Yip](http://twitter.com/lunchbag)

#### /weekend3
This is our Scalatra web server. It receives daily data from [Bottlerock](https://github.com/lambtron/sensation-bottlerock), interprets it, summarizes it, and stores it in a MongoDB instance.

#### [/bottlerock](https://github.com/lambtron/sensation-bottlerock)
This is our web scraper to grab supply/demand information for certain events. It's responsible for sanitizing the data as well. /weekend3 consumes data from /bottlerock.

#### [/northcoast](https://github.com/lambtron/sensation-northcoast)
This is our web front-end that displays the data in a visual form.
