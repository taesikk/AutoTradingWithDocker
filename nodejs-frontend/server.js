const http = require('http');
const fs = require('fs');
const path = require('path');
const url = require('url');

const PORT = 3000;
const staticDir = path.join(__dirname, 'static');
const templatesDir = path.join(__dirname, 'templates');


const server = http.createServer((req, res) => {
    const parsedUrl = url.parse(req.url);
    let filePath = parsedUrl.pathname;
//   let filePath = './public' + (req.url === '/' ? '/index.html' : req.url);
//   const extname = String(path.extname(filePath)).toLowerCase();
    if (filePath === '/' || filePath === '/index.html') {
        filePath = '/templates/home.html';
    }
    if (filePath.startsWith('/css') || filePath.startsWith('/js') || filePath.startsWith('/images') || filePath.startsWith('/fonts') || filePath.startsWith('/vendor')) {
        filePath = '/static' + filePath;
    } else if (filePath.startsWith('/templates')) {
        // 그대로 사용
    } else if (filePath.endsWith('.html')) {
        filePath = '/templates' + filePath;
    }

  const mimeTypes = {
    '.html': 'text/html',
    '.js': 'text/javascript',
    '.css': 'text/css',
    '.json': 'application/json',
    '.png': 'image/png',
    '.jpg': 'image/jpg',
    '.svg': 'image/svg+xml',
  };
  //const contentType = mimeTypes[extname] || 'application/octet-stream';

  const absPath = path.join(__dirname, filePath);
  fs.readFile(absPath, (err, data) => {
    if (err) {
      res.writeHead(404, { 'Content-Type': 'text/plain' });
      res.end('404 Not Found');
      return;
    }
    const ext = path.extname(absPath);
    let mimeType = mimeTypes[ext] || 'application/octet-stream';
    if (mimeType.startsWith('text/') || mimeType === 'application/javascript') {
      mimeType += '; charset=utf-8';
    }
    res.writeHead(200, { 'Content-Type': mimeType });
    res.end(data);
  });
});

server.listen(PORT, () => {
  console.log(`Server running at http://localhost:${PORT}`);
});