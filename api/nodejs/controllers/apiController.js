
let bodyParser = require('body-parser');
let todos = [];

function Todo(id, todo, isDone) {
    this.id = id;
    this.todo = todo;
    this.isDone = isDone;
}

function getRandomIntInclusive(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

module.exports = function (app) {

    app.use(bodyParser.json());
    app.use(bodyParser.urlencoded({extended: true}));

    app.get('/api/setup', function (req, res) {
        todos = [];
        for (var i = 1; i <= 20; i++) {
            var onOff = getRandomIntInclusive(0, 1);
            todos.push(new Todo(i, "task." + i, onOff === 1 ? true : false));
        }
        res.status(200).send(todos);
    });

    app.get('/api/todos', function (req, res) {
        res.status(200).send(todos);
    });

    app.get('/api/todos/:id', function (req, res) {

        var id = Number(req.params.id);
        if (!id) {
            res.status(400).json({
                title: "Not found",
                error: "404"
            });
        } else {
            var todo = todos.find(x => x.id === id);

            if (!todo) {
                res.status(404).json({
                    title: "Not found",
                    error: "404"
                });
            } else {
                res.status(200).send(todo);
            }
        }
    });

    app.post('/api/todos', function (req, res) {
        var id = Number(req.body.id);
        let idx = id ? todos.findIndex(x => x.id === id) : -1;
        if (idx < 0) {
            let newId = todos.length == 0 ? 0 : todos[todos.length - 1].id;
            let newTodo = new Todo(newId + 1, req.body.todo, req.body.isDone);
            todos.push(newTodo);
            res.status(201).send(newTodo);
        } else {
            var todo = todos[idx];

            if (!todo) {
                res.status(400).json({
                    title: "Not found",
                    error: "404"
                });
            } else {
                todo.todo = req.body.todo;
                todo.isDone = req.body.isDone;
                todos[idx] = todo;
                res.status(200).send(todo);
            }
        }
    });

    app.delete('/api/todos', function (req, res) {

        var id = Number(req.body.id);
        let idx = id ? todos.findIndex(x => x.id === id) : -1;
        if (!id || idx < 0) {
            res.status(404).json({
                title: "Not found",
                error: "404"
            });
        } else {
            let idx = todos.findIndex(x => x.id === id);
            let todo = todos[idx];
            todos.splice(idx, 1);
            res.status(200).send(todo);
        }
    });
}