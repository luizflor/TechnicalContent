/**
 * Created by luizsilva on 5/26/17.
 */
var express = require('express');
var app = express();
// var setupController = require('./controllers/setupController');
var apiController = require('./controllers/apiController');

var port = process.env.PORT || 3000;

app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Log-ID");
    res.header("Access-Control-Allow-Methods", "GET,POST,DELETE");
    next();
});

// app.use('/assets', express.static(__dirname + '/public'));
//
// app.set('view engine', 'ejs');

// mongoose.connect(config.getDbConnectionString());
// setupController(app);
apiController(app);

console.log("application listening on port: "+port);
app.listen(port);
