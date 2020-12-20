module.exports = function () {
var person = require("../nodejs/Person/Person");
var person2 = require("../nodejs/Person2/Person2");
        return {
            person: person().Person,
            person2: person2().Person2,
        }
}
