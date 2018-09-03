
import React from 'react';
import PropTypes from 'prop-types';

class Objs extends React.Component {
	
	constructor(props){
		super(props)
		console.log('[Objs|constructor|in] props:', props);
		this.state = props.state;
		this.context = props.context;
	}

	render(){
		console.log('[Objs|render|in]', this.state);
		console.log('[Objs|render|out]');
		if( 0 == this.state.objs.length )
			return null;
		else {
			return (
				<section>
		            <table className="table table-striped table-sm">
	       				<thead><tr><th scope="col">to</th><th scope="col">from</th><th scope="col">date</th><th scope="col">subject</th><th scope="col">body</th></tr></thead>
	       				<tbody>
			            { this.state.objs.map( (obj, i) => <tr><td>{obj.To}</td><td>{obj.From}</td><td>{obj.Date}</td><td>{obj.Subject}</td><td>{obj.body}</td></tr> ) }
			            </tbody>
		            </table>
				</section>
			)
		}
	}
};

Objs.propTypes = {
	state: PropTypes.object.isRequired
	, context: PropTypes.object.isRequired
}

Objs.defaultProps = {
	state: {}
	, context: {}
}

export default Objs;