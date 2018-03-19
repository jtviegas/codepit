
import React from 'react';
import Header from './Header';
import Main from './Main';
import Footer from './Footer';
import Dummy from './Dummy';

class Page extends React.Component {
	render(){
		const { data } = this.props;
		return (
				<div className="page">
				
					<Header data={data} />
					<Main data={data} narrative='init' />	
					<Footer data={data} />

				</div>
				)
	}
};

export default Page;