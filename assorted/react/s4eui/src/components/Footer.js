
import React from 'react';

class Footer extends React.Component {
	render(){
		const { data } = this.props;
		return (
				<div className="container footer">
					<div className="row align-items-end justify-content-between">
						<div className="col-5">
							.....................
						</div>
						<div className="col-5">
							.................
						</div>
					</div>
				</div>
				)
	}	
};

export default Footer;