/**
 * Created by luizsilva on 5/26/17.
 */
var express = require('express');
var app = express();
// var setupController = require('./controllers/setupController');
var apiController = require('./controllers/apiController');

var port = process.env.PORT || 3000;

// app.use('/assets', express.static(__dirname + '/public'));
//
// app.set('view engine', 'ejs');

// mongoose.connect(config.getDbConnectionString());
// setupController(app);
apiController(app);

console.log("application listening on port: "+port);
app.listen(port);
