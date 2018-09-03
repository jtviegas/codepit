
import React from 'react';
import DataService from '../services/data/index';
import { Switch, Route, Redirect } from 'react-router-dom';

import Objs from './Objs';
import Obj from './Obj';
import Header from './Header';

class App extends React.Component {
	
	constructor(props){
		super(props)
/*		this.context = {
			services: {
				data: new DataService(props.configuration)
			}
			, configuration : props.configuration
		};*/
		this.contexto = "AAA";
		console.log('context', this.context)
		this.state = {
			objs: []
			, page: null
			, obj: null
			, search: null
		};
	}
	
	render(){
		console.log('[App|render|in]');
		console.log('context', this.contexto);
		console.log('state', this.state);
		console.log('[App|render|out]');

		const { context, state } = this;
		return (
            <section className="container-fluid">
				<Header state={state} context={context} />
				<section className="container">
					<Switch>
						<Route path='/objs/:id' render={(props) => <Obj {...props} state={state} />} />	
						<Route path='/objs' render={ (props) => <Objs {...props} state={state} /> } />
						<Redirect from='/' to='/objs' />
					</Switch>
				</section>
			</section>
		)
	}
};

export default App;


