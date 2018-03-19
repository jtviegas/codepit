import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import '../node_modules/bootstrap/dist/css/bootstrap.css'
import Page from './components/Page';
import registerServiceWorker from './registerServiceWorker';

ReactDOM.render(<Page name="just a part" notes="..." price="21.43" category="AA" subcategory="BA" images="{}"  />, document.getElementById('root'));
registerServiceWorker();
