
import React from 'react';
import Header from './Header';
import Main from './Main';
import Footer from './Footer';

class Page extends React.Component {
	render(){
		const { data } = this.props;
		return (
				<div className="page">
					
					<Header data={data} />
					<Main data={data} />
					<Footer data={data} />

				</div>
				)
	}
};

export default Page;