
import React from 'react';
import PartsView from './PartsView';
import Part from './Part';
import Highlights from './Highlights';


class Main extends React.Component {
	render(){
		const { data } = this.props;
		return (
				<div className="container">
				
				
				<Highlights data={data} />
					<PartsView data={data} />
					<Part data={data} />
				</div>

				)
	}
};

export default Main;