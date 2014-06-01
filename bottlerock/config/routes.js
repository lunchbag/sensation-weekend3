'use strict';

(function() {
  // Controllers. ==============================================================
  var Kimono = require('../app/controllers/kimono');
  var fs = require('fs');

  // Public functions. =========================================================
  module.exports = function(app) {
    app.get('/api/craigslist', function(req, res) {
      var obj = '';
      Kimono.getListings(obj, function(err, data) {
        if (err)
          res.send(err, 400);

        res.send(data, 200);

        var outputFilename = 'data/listings.json';
        fs.writeFile(outputFilename, JSON.stringify(data, null, 4), function(err) {
          if (err) {
            console.log(err);
          } else {
            console.log("JSON saved to " + outputFilename);
          }
        });
      });
    });

    // Application routes. =====================================================
    app.get('/', function(req, res) {
      res.sendfile('index.html', {
        'root': './public/views/'
      });
    });
  };

}());
