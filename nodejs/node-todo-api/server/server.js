const mongoose = require('mongoose');

mongoose.Promise = global.Promise; // Use the default promise library
mongoose.connect('mongodb://localhost:27017/TodoApp');
// No need of callback - as mongoose maintains an internal queue so it processes the requests after it establishes the connection

var Todo = mongoose.model('Todo', {
  text: {
    type: String,
    required: true,
    minlength: 1, // Disallow empty strings
    trim: true // Remove leading or trailing space
  },
  completed: {
    type: Boolean,
    default: false
  },
  completedAt: {
    type: Number, // Unix timestamp
    default: null
  }
});

// Mongoose does type casting by default to help validations pass. Be careful as it may lead to bugs.

var newTodo = new Todo({
  text: 'Cook Dinner'
})

newTodo.save().then((doc) => {
  console.log(doc);
}, (e) => {
  console.log(e);
});


var User = mongoose.model('User', {
  email: {
    type: String,
    required: true,
    minlength: 1,
    trim: true
  }
});

var newUser = new User({
  email: 'test@example.com'
});

newUser.save().then((doc) => {
  console.log(JSON.stringify(doc, undefined, 2));
}, (error) => {
  console.log(error);
})
