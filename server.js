const mysql 		= require('mysql');
const express		= require('express');
const https 		= require('https');
const http 			= require('http');
const fs			= require('fs');
const log4js		= require('log4js');

const config		= require('./config');
const utils			= require('./utils');

const filename = utils.now('yyyy-MM-dd');

fs.openSync(__dirname + '/logs/' + filename +'.log', 'a');

log4js.configure({
	appenders: [
		{ type: 'console' },
		{ type: 'file', filename: './logs/' + filename +'.log' }
	]
});

const app 			= express();
const logger		= log4js.getLogger();

logger.debug('Script start');
logger.info('Initialisation complited');


app.get('/', (req, res) => {

	logger.debug('Server GET: "/"');

	res.status(200);
	res.set('Content-Type', 'text/html');
	res.send('Hello world!');

	logger.info('Server GET: "/" - response sent');

});


logger.debug('Server start');

if(config.server.protocol == 'http://') {
	
	const server = http.createServer(app).listen(config.server.port, () => {
		logger.info(
			'Server listening on ' +
			config.server.protocol +
			config.server.address  +
			':'					   +
			config.server.port
		);
	});

} else if(config.server.protocol == 'https://') {

	const server = https.createServer(config.server.credentials, app).listen(config.server.port, () => {
		logger.info(
			'Server listening on ' +
			config.server.protocol +
			config.server.address  +
			':'					   +
			config.server.port
		);
	});

} else {

	logger.fatal('Wrong protocol');

	const server = null;

	throw new Error('Unknown protocol or no protocol found. Please check config file.');
}
