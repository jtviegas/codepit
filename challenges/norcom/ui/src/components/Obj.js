
import React from 'react';
import PropTypes from 'prop-types';

class Obj extends React.Component {
	
	constructor(props){
		super(props)
		console.log('[Obj|constructor|in] props:', props);		
		console.log('[Obj|constructor|out]');
	}
	
	componentWillMount(){
		console.log('[Obj|componentWillMount|in]', this.props);
		const element = this.props.objs.filter(o => o["Message-ID"] === this.props.match.params.id)[0];
		this.setState({obj: element})
		console.log('[Obj|componentWillMount|out]'); 
	}
	
	render(){
		console.log('[Obj|render|in]', this.props, this.state);
        console.log('[Obj|render|out]');
		return (
			<section>
				<div className="form-row">
					<div className="form-group col-md-6">
				      <label htmlFor="inputEmailFrom">from</label>
				      <input type="email" className="form-control" id="inputEmailFrom" value={this.state.obj.From} readOnly />
				    </div>
				    <div className="form-group col-md-6">
				    	<label htmlFor="inputEmailTo">to</label>
				    	<input type="email" className="form-control" id="inputEmailTo" value={this.state.obj.To} readOnly/>
				    </div>
				</div>
				<div className="form-row">
			    	<div className="form-group col-md-6">
			    		<label htmlFor="inputDate">date</label>
			    		<input type="text" className="form-control" id="inputDate" value={this.state.obj.Date} readOnly/>
			    	</div>
			    </div>
			    <div className="form-row">
			    	<div className="form-group col-md-12">
			    		<label htmlFor="inputSubject">subject</label>
			    		<input type="text" className="form-control" id="inputSubject" value={this.state.obj.Subject} readOnly/>
			    	</div>
			    </div>
			    <div className="form-row">
			    	<div className="form-group col-md-12">
				    	<label htmlFor="textBody">body</label>
				    	<textarea className="form-control" id="textBody" rows="7" readOnly value={this.state.obj.body}/>
				    </div>
			    </div>

			</section>
			)
	}
};

Obj.propTypes = {
		objs: PropTypes.array.isRequired
	};

Obj.defaultProps = {
		objs: []
	};

export default Obj;