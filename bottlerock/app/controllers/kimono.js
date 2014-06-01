'use strict';

(function() {

  var request = require('request');
  var KIMONO_KEY = '6849a5e4ffb39e325c8add25b1f5695c';
  var KIMONO_PATH = 'http://www.kimonolabs.com/api/8v26ll92';

  // Public functions. =========================================================
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

      request(opts, function(err, data) {
        if (!err) {
          var load = JSON.parse(data.body);
          // data.body.results.collection1 => array
          sanitizeListings(load.results.collection1);
          // console.log(load.results.collection1);
        }
        cb(err, load);
      });
    }
  };

  // Private functions. ========================================================

  function sanitizeListings(arr) {
    // Iterate through array; sanitize.
    for (var i = 0; i < arr.length; i++) {
      if (arr[i].buyer_or_seller.text) {
        var buyer_or_seller = arr[i].buyer_or_seller.text.toLowerCase();

        if (buyer_or_seller.indexOf('tickets - by owner') >= 0) {
          arr[i].buyer_or_seller.text = 'seller';
        } else if (buyer_or_seller.indexOf('wanted - by owner') >= 0) {
          arr[i].buyer_or_seller.text = 'buyer';
        } else {
          arr[i] = '';
        }
      }

      if (arr[i] != '' && arr[i].title.text) {
        var title = arr[i].title.text.toLowerCase();
        // Default ticket stats.
        arr[i].ticket = {
          vip: false,
          shuttle: false,
          days: []
        };
        var days = ['fri', 'sat', 'sun'];

        if (title.indexOf('vip') >= 0) {
          arr[i].ticket.vip = true;
        }

        if (title.indexOf('shuttle') >= 0) {
          arr[i].ticket.shuttle = true;
        }

        if (title.indexOf('fri') >= 0 || title.indexOf('sat') >= 0 ||
          title.indexOf('sun') >= 0) {
          for (var j = 0; j < days.length; j++) {
            if (title.indexOf(days[j]) >= 0) {
              arr[i].ticket.days.push(days[j]);
            }
          }
        } else {
          arr[i].ticket.days = ['fri', 'sat', 'sun'];
        }
      }
    }
  }

}());
