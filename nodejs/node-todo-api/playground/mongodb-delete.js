const {
  MongoClient,
  ObjectID
} = require('mongodb');

MongoClient.connect('mongodb://localhost:27017/TodoApp', (err, db) => {
  if (err) {
    return console.log('Unable to connect to MongoDB Server');
  }
  console.log('Connected to MongoDB Server');

  // deleteMany
  db.collection('Todos').deleteMany({
    text: 'Eat lunch'
  }).then((res) => {
    console.log(res.result);
  }, (error) => {
    console.log(error)
  })

  // deleteOne


  // findOneAndDelete - delete and return the object


  // db.close();
});
