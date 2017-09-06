const {
  ObjectID
} = require('mongodb');

const {
  mongoose
} = require('./../server/db/mongoose');
const {
  Todo
} = require('./../server/models/todo');

var id = '59aff77e3116e709f1a1c387';
// var id = '69aff77e3116e709f1a1c387';

// Todo.find({
//   _id: id
// }).then((todos) => {
//   console.log('Todos', todos);
// })
//
// Todo.findOne({
//   _id: id
// }).then((todo) => {
//   console.log('Todo', todo);
// })

// ObjectID.isValid(id) #=> true/false
if (!ObjectID.isValid(id)) {
  console.log('Id not valid')
}

Todo.findById(id).then((todo) => {
  if (!todo) {
    return console.log('Id not found');
  }
  console.log('Todo', todo)
}).catch((e) => console.log(e));


// Validating ObjectID
