'use strict';

(function() {
  // Controllers. ==============================================================
  var Kimono = require('../app/controllers/kimono');

  // Public functions. =========================================================
  module.exports = function(app) {
    app.get('/api/craigslist', function(req, res) {
      var obj = '';
      Kimono.getListings(obj, function(err, data) {
        if (err)
          res.send(err, 400);

        res.send(data, 200);
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
