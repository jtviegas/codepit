
import React from 'react';
import DataService from '../services/data/index';
import { Switch, Route, Redirect } from 'react-router-dom';

import Objs from './Objs';
import Obj from './Obj';
import Header from './Header';

class App extends React.Component {
	
	constructor(props){
		super(props)
		this.app = {
			services: {
				data: new DataService(props.configuration)
			}
			, configuration : props.configuration
		};
		this.state = {
			objs: []
			, page: null
			, obj: null
			, search: null
		};
		this.search = this.search.bind(this);
	}
	
	change(objs){
		this.setState({objs: objs});
	}
	
	search(term){
		console.log('[App|search|in] term:', term);
		this.app.services.data.search(term, (e,os) => { this.change(os.data) });	
		console.log('[App|search|out]');
	}
	
	render(){
		console.log('[App|render|in]', this.props);
		console.log('[App|render|out]');

		const functions = { search: this.search };
		return (
            <section>
            	<Header functions={functions} state={this.state} />
            	<main role="main" className="container">	
					<Switch>
						<Route path='/objs/:id' render={(props) => <Obj {...props} objs={this.state.objs} />} />	
						<Route path='/objs' render={ (props) => <Objs {...props} objs={this.state.objs} /> } />
						<Redirect from='/' to='/objs' />
					</Switch>
				</main>
			</section>
		)
	}
};

export default App;


