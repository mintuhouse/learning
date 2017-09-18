const express = require('express');
const socketIO = require('socket.io');
const http = require('http');
var app = express();

const path = require('path');
const publicPath =  path.join(__dirname + '/../public');
app.use(express.static(publicPath));

var server = http.createServer(app);
var io = socketIO(server);
// Socket frontend library available at http://localhost:3000/socket.io/socket.io.js

io.on('connection', (socket) => {
  console.log("New user connected");

  socket.on('disconnect', () => {
    console.log("Disconnected")
  })
});

const port = process.env.PORT || 3000;
server.listen(port, () => {
  console.log(`Starting server on port ${port}`);
})
