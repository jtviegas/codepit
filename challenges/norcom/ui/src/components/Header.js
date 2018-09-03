
import React from 'react';
import PropTypes from 'prop-types';

class Header extends React.Component {
	
	constructor(props){
		super(props)
		this.state = props.state;
		this.context = props.context;
		this.submit = this.submit.bind(this);
		console.log('props', props);
		console.log('state', this.state);
	}
	
/*	callback(e,o){
		if(e){
			this.setState({cache: { objs: []}});
			
		}
		else{
			let cache = {};
			cache.objs = o.data;
			const newState = Object.assign({}, this.state, {cache: cache});

			this.setState(newState);
			console.log(this.state)
		}
	}*/
	
	submit(e) {
		const { _term } =  this.refs;
        e.preventDefault();
        console.log("context", this.context);
        this.context.services.data.search(_term.value);
    }
	
	render(){

		return (
			    <nav className="navbar navbar-expand-md navbar-dark bg-dark mb-12">
			      <a className="navbar-brand" href="#">indexer</a>
			      <div className="collapse navbar-collapse" id="navbarCollapse">
			        <form className="form-inline mt-2 mt-md-0" onSubmit={this.submit}>
			          <input ref="_term" className="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search" required/>
			          <button className="btn btn-outline-success my-2 my-sm-0">Search</button>
			        </form>
			      </div>
			    </nav>
			)
	}
};


Header.propTypes = {
		state: PropTypes.object.isRequired
		, context: PropTypes.object.isRequired
	}

Header.defaultProps = {
		state: {}
		, context: {}
	}

export default Header;