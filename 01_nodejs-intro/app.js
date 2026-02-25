import http from "http";

const PORT = 3000;

const server = http.createServer((req, res) => {
  res.writeHead(200, { "Content-Type": "text/plain" });
  res.end('Programação Back-end: Introdução ao Node.js');
});

server.listen(PORT, () => {
  console.log("servidor escutando!");
});

