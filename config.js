'use strict';

const fs = require('fs');

var Config = {
	dev: {
		server: {
			protocol: 'http://',
			address: 'localhost',
			port: '1337',
			credentials: {
				key: fs.readFileSync('./ssl/privatekey.pem'),
				cert: fs.readFileSync('./ssl/certificate.pem')
			}
		},
		db: {
			adress: 'localhost',
			port: '1337',
			user: 'admiin',
			password: 'admiin',
			name: 'test'
		}
	},
	production: {}
};

module.exports = Config.dev;