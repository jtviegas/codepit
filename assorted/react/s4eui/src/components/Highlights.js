
import React from 'react';
import Carousel from './Carousel';

class Highlights extends React.Component {
	render() {
		const {data} = this.props;
		return (
			<div className="page-element">
				<div class="jumbotron">
		        	<h1>Highlights</h1>
		        	<Carousel auto={true}/>
		        </div>
			</div>


			)
	}
}
;

export default Highlights;