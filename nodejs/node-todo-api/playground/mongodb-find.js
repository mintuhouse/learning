const {
  MongoClient,
  ObjectID
} = require('mongodb');

MongoClient.connect('mongodb://localhost:27017/TodoApp', (err, db) => {
  if (err) {
    return console.log('Unable to connect to MongoDB Server');
  }
  console.log('Connected to MongoDB Server');

  // Every document
  var cursor = db.collection('Todos').find(); // Returns a mongodb cursor

  var documents = cursor.toArray().then((docs) => {
    console.log('Todos:', JSON.stringify(docs, undefined, 2));
  }, (error)=> {
    console.log('Unable to connect');
  });

  db.collection('Todos').find({
    completed: false
  }).toArray().then((docs) => {
    console.log('Todos');
    console.log(docs);
  }, (error) => {
    console.log('Unable to get documents')
  });

  db.collection('Todos').find({
    _id: new ObjectID('59ad36b16d6aed13e681e0a9') // For quering via _id, you need to create an ObjectID from the string in database
  }).toArray().then((docs) => {
    console.log('Todos');
    console.log(JSON.stringify(docs, undefined, 2));
  }, (error) => {
    console.log('Unable to get documents')
  });

  // db.close();
});
