var mongoose = require('mongoose');

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

module.exports = {Todo}
