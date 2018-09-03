import React from 'react';
import ReactDOM from 'react-dom';
import {HashRouter} from 'react-router-dom';

import '../node_modules/bootstrap/dist/css/bootstrap.css';
import '../node_modules/bootstrap/dist/js/bootstrap.min.js';
import './index.css';

import App from './components/App';

import configuration from './configuration';

ReactDOM.render(<HashRouter><App configuration={configuration}/></HashRouter>, document.getElementById('root'));


