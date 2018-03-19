
import React from 'react';
import PartsView from './PartsView';
import Part from './Part';
import Highlights from './Highlights';


class Main extends React.Component {
	constructor(props) {
	    super(props);
	  }
	
	render(){
		const { data } = this.props;
		
		switch(this.props.narrative){
		case 'part':
			return ( 
				<div className="container">
					<Part data={this.props.data} />
				</div>
			)
		default:
			return ( 
				<div className="container">
					<Highlights data={this.props.data} />
					<PartsView data={this.props.data} />
				</div>
			)
		}
	}
};

export default Main;