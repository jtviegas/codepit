
import React from 'react';
import Carousel from './Carousel';

class Highlights extends React.Component {
	constructor(props) {
	    super(props);
	    this.state = {
	      value: null,
	    };
	  }

	render() {
		const {data} = this.props;
		
		return (
			<div className="page-element">
				<div className="jumbotron">
		        	<h1>Highlights</h1>
		        	<Carousel auto={true}/>
		        </div>
			</div>


			)
	}
}
;

export default Highlights;