import React from 'react';
import PropTypes from 'prop-types';

class Objs extends React.Component {
	
	constructor(props){
		super(props)
		console.log('[Objs|constructor|in]');
		this.objs = props.objs;
		console.log('[Objs|constructor|out]');
	}

	render(){
		console.log('[Objs|render|in]');
		console.log("objs", this.props.objs);
        console.log('[Objs|render|out]');
		if( 0 === this.props.objs.length )
			return null;
		else {
			return (
				<section>
		            <table className="table table-striped table-sm">
	       				<thead><tr><th scope="col">to</th><th scope="col">from</th><th scope="col">date</th><th scope="col">subject</th><th scope="col">body</th></tr></thead>
	       				<tbody>
			            { this.props.objs.map( (obj, i) => <tr><td>{obj.To}</td><td>{obj.From}</td><td>{obj.Date}</td><td>{obj.Subject}</td><td>{obj.body}</td></tr> ) }
			            </tbody>
		            </table>
				</section>
			)
		}
	}
};

/*Objs.propTypes = {
	objs: PropTypes.array.isRequired
}*/

Objs.defaultProps = {
	objs: []
}

export default Objs;