const {MongoClient, ObjectID} = require('mongodb'); // node-mongodb-native


// Equivalent to
// const MongoClient = require('mongodb').MongoClient;

// var user = {name: 'andrew', age: 25};
// var {name} = user;
// console.log(name); // ES6 Object Destructuring

// var obj = new ObjectID();

MongoClient.connect('mongodb://localhost:27017/TodoApp', (err, db) => {
  if (err) {
    return console.log('Unable to connect to MongoDB Server');
  }
  console.log('Connected to MongoDB Server');

  db.collection('Todos')
    .insertOne({
      text: "Do something",
      completed: false
    }, (err, result) => {
      if (err) {
        return console.log('Unable to insert TODO')
      }

      //JSON.stringify(json, filterFunction, indentation)
      console.log(JSON.stringify(result.ops, undefined, 2));
      console.log(result.ops[0]._id.getTimestamp());
    });

  db.close();
});
