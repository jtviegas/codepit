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
	       					<tr className="row">
	       						<th className="col-3">
	       							<tr><th>from</th></tr>
	       							<tr><th>to</th></tr>
	       						</th>
	       						<th className="col-4">
	       							<tr><th>date</th></tr>
	       							<tr><th>subject</th></tr>
       							</th>
		       					<th className="col-5">body</th>
	       					</tr>
	       				</thead>
	       				<tbody>
			            { this.props.objs.map( (obj, i) => 
			            	<tr key={i} className="row">
			            		<td className="col-3">
			            			<tr><td>{obj.From}</td></tr>
			            			<tr><td>{obj.To}</td></tr>
			            		</td>
			            		<td className="col-4">
			            			<tr><td>{obj.Date}</td></tr>
			            			<tr><td><NavLink to={"/objs/" + obj["Message-ID"]} className="card-link">{obj.Subject}</NavLink></td></tr>
			            		</td>
			            		<td className="col-5">{obj.body}</td>
			            	</tr> 
			            	
			            ) }
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