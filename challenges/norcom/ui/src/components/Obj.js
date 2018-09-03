
import React from 'react';
import PropTypes from 'prop-types';

class Obj extends React.Component {
	
	constructor(props){
		super(props)
		console.log('[Obj|constructor|in] props:', props);
		this.state = props.state;
		this.context = props.context;
		console.log('[Obj|constructor|out]');
	}
	
	render(){
		return (
			<section>
				<div className="form-row">
					<div className="form-group col-md-6">
				      <label for="inputEmailFrom">from</label>
				      <input type="email" className="form-control" id="inputEmailFrom" value={this.state.obj.From}/>
				    </div>
				    <div className="form-group col-md-6">
				    	<label for="inputEmailTo">to</label>
				    	<input type="email" className="form-control" id="inputEmailTo" value={this.state.obj.To}/>
				    </div>
				</div>
				<div className="form-row">
			    	<div className="form-group col-md-6">
			    		<label for="inputDate">date</label>
			    		<input type="text" className="form-control" id="inputDate" value={this.state.obj.Date}/>
			    	</div>
			    </div>
			    <div className="form-row">
			    	<div className="form-group col-md-12">
			    		<label for="inputSubject">subject</label>
			    		<input type="text" className="form-control" id="inputSubject" value={this.state.obj.Subject}/>
			    	</div>
			    </div>
			    <div className="form-row">
			    	<div className="form-group col-md-12">
				    	<label for="textBody">body</label>
				    	<textarea className="form-control" id="textBody" rows="7">{this.state.obj.body}</textarea>
				    </div>
			    </div>

			</section>
			)
	}
};

Obj.propTypes = {
		state: PropTypes.object.isRequired
		, context: PropTypes.object.isRequired
	}

Obj.defaultProps = {
		state: {}
		, context: {}
	}

export default Obj;