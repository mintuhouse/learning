const {
  MongoClient,
  ObjectID
} = require('mongodb');

MongoClient.connect('mongodb://localhost:27017/TodoApp', (err, db) => {
  if (err) {
    return console.log('Unable to connect to MongoDB Server');
  }
  console.log('Connected to MongoDB Server');

  // findOneAndUpdate(filter, update, options) #=> promise
  db.collection('Todos').findOneAndUpdate({
    _id: new ObjectID('59ad36b16d6aed13e681e0a9')
  }, {
    $set: {
      completed: true
    }
  }, {
    returnOriginal: false // Return the updated document
  }).then((result) => {
    console.log(result);
  }, (error) => {

  })

  // db.close();
});
