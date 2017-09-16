const {ObjectID} = require('mongodb');
const {mongoose} = require('./../server/db/mongoose');
const {Todo} = require('./../server/models/todo');
const {User} = require('./../server/models/user');

// Todo.remove({}) //Removes all & DOES NOT return the removed ones

// Todo.findOneAndRemove({})
// Todo.findByIdAndRemove(id)
