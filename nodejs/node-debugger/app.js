const express = require('express');

var app = express();

app.get('/', (req, res) => {
   res.send('Worked!')
})

app.listen(3000, () => {
  console.log("Started  a server");
})