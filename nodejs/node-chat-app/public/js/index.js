var socket = io();
socket.on('connect', function() {
  console.log('Connected to server');
  //
  // socket.emit('createMessage', {
  //   from: 'jen@example.com',
  //   text: 'Hey, this is Andrew'
  // })
});
socket.on('disconnect', function() {
  console.log('Disconnected from server');
});

socket.on('newMessage', function(data) {
  console.log('New Message', data);
});
