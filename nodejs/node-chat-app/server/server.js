const express = require('express');
const socketIO = require('socket.io');
const http = require('http');

const {generateMessage} = require('./utils/message');

var app = express();

const path = require('path');
const publicPath =  path.join(__dirname + '/../public');
app.use(express.static(publicPath));

var server = http.createServer(app);
var io = socketIO(server);
// Socket frontend library available at http://localhost:3000/socket.io/socket.io.js

io.on('connection', (socket) => {
  console.log("New user connected");

  // socket.emit('newMessage', {
  //   from: "mike@example.com",
  //   text: 'Hey! Whatsup?',
  //   createdAt: 123
  // });

  socket.on('createMessage', (newMessage, callback) => {
    console.log('createMessage', newMessage);
    callback({a: 1});
    // Emit to everyone but socket
    socket.broadcast.emit('newMessage', generateMessage(newMessage.from, newMessage.text))
  })

  socket.on('disconnect', () => {
    console.log("Disconnected")
  })
});

const port = process.env.PORT || 3000;
server.listen(port, () => {
  console.log(`Starting server on port ${port}`);
})
