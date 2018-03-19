
import React from 'react';
import PartsView from './PartsView';

class Main extends React.Component {
	render(){
		const { data } = this.props;
		return (
				<PartsView data={data} />
				)
	}
};

export default Main;