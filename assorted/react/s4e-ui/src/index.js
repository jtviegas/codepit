import React from 'react';
import { render } from 'react-dom';
import './index.css';
import Parts from './Parts';
import data from './data';
import registerServiceWorker from './registerServiceWorker';

window.React = React;
render(<Parts parts={data} />, document.getElementById('root'));
registerServiceWorker();
