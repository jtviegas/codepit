
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
		const { _term } =  this.refs;
        e.preventDefault();
        this.functions.search(_term.value);
    }
	
	render(){
		console.log('[Header|render|in]');
        console.log('[Header|render|out]');

		return (
			    <nav className="navbar navbar-expand-md navbar-dark mb-12">
			      <a className="navbar-brand">indexer</a>
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
		, app: PropTypes.object.isRequired
	}

Header.defaultProps = {
		state: {}
		, app: {}
	}

export default Header;