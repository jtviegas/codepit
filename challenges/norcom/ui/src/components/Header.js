import {
  withRouter
	} from 'react-router-dom'
import React from 'react';
import PropTypes from 'prop-types';

class Header extends React.Component {
	
	constructor(props){
		super(props)
		console.log(props)
		this.functions = props.functions;
		this.submit = this.submit.bind(this);
	}
	
	submit(e) {
		console.log(this.props)
		const { _term } =  this.refs;
        e.preventDefault();
        this.functions.search(_term.value);
        if( this.props.location.pathname !== '/objs' )
        	this.props.history.push('/objs');
    }
	
	render(){
		console.log('[Header|render|in]');
        console.log('[Header|render|out]');
        
        console.log(this.props);
        
        return(
        <div className="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom shadow-sm">
	        <h5 className="my-0 mr-md-auto font-weight-normal">indexer</h5>
	        <nav className="my-2 my-md-0 mr-md-3">
	        <form className="form-inline my-2 my-lg-0" onSubmit={this.submit}>
			<input ref="_term" className="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search" required />
			<button className="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
			</form>
	        </nav>
	      </div>
	     )
	}
};


Header.propTypes = {
		state: PropTypes.object.isRequired
		, functions: PropTypes.object.isRequired
	}

Header.defaultProps = {
		state: {}
		, functions: {}
	}

export default withRouter(Header);






