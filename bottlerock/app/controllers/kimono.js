'use strict';

(function() {

  var request = require('request');
  var KIMONO_KEY = '6849a5e4ffb39e325c8add25b1f5695c';
  var KIMONO_PATH = 'http://www.kimonolabs.com/api/csv/8v26ll92';

  module.exports = {
    // Get JSON.
    getListings: function getListings(obj, cb) {
      if (typeof obj === 'function') {
        cb = obj;
        obj = {
          marketplace: 'craigslist',
          event_name: 'edc'
        };
      }

      // Obj.
      // .marketplace: craigslist, fb, stubhub, ..
      // .event_name: edc, ..

      var qs = {
        apikey: KIMONO_KEY,
        s: 0, // 0 is the first listing
        query: 'edc'
      };

      var opts = {
        uri: KIMONO_PATH,
        method: 'GET',
        timeout: 50000,
        followRedirect: true,
        maxRedirects: 10,
        qs: qs
      };

      request(opts, cb);
    }
  };

}());
