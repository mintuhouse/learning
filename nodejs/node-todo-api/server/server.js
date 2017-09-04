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

var newTodo = new Todo({
  text: 'Cook Dinner'
})

newTodo.save().then((doc) => {
  console.log(doc);
}, (e) => {
  console.log(e);
});
