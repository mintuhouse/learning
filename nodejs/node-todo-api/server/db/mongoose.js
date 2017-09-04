const mongoose = require('mongoose');

mongoose.Promise = global.Promise; // Use the default promise library
mongoose.connect('mongodb://localhost:27017/TodoApp');
// No need of callback - as mongoose maintains an internal queue so it processes the requests after it establishes the connection

module.exports = {mongoose};
