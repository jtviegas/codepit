import React from 'react';
import PropTypes from 'prop-types';
import { NavLink } from 'react-router-dom';

class Objs extends React.Component {
	
	constructor(props){
		super(props)
		console.log('[Objs|constructor|in]');
		console.log('[Objs|constructor|out]');
	}

	render(){
		console.log('[Objs|render|in]');
        console.log('[Objs|render|out]');
		if( 0 === this.props.objs.length )
			return null;
		else {
			return (
				
				<div className="table-responsive">
		            <table className="table table-striped">
	       				<thead>
	       					<tr>
	       					<th className="col-1">from</th>
	       					<th className="col-1">to</th>
	       					<th className="col-1">date</th>
	       					<th className="col-2">subject</th>
	       					<th className="col-7">body</th>
	       					</tr>
	       				</thead>
	       				<tbody>
			            { this.props.objs.map( (obj, i) => 
			            	<tr key={i}>
			            	<td className="col-1">{obj.From}</td>
			            	<td className="col-1">{obj.To}</td>
			            	<td className="col-1">{obj.Date}</td>
			            	<td className="col-2"><NavLink to={"/objs/" + obj["Message-ID"]} className="card-link">{obj.Subject}</NavLink></td>
			            	<td className="col-7">{obj.body}</td>
			            	</tr> ) }
			            </tbody>
		            </table>
		        </div>
		
			)
		}
	}
};
	
Objs.propTypes = {
	objs: PropTypes.array.isRequired
}

Objs.defaultProps = {
	objs: []
}

export default Objs;