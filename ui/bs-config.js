var proxy = require('http-proxy-middleware');

module.exports = {
    server:  {
        middleware: {
            10: proxy('/rating-service', {
                target: 'http://localhost:8080',
                changeOrigin: true
            })
        }
    }
};