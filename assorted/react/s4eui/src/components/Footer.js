
import React from 'react';

class Footer extends React.Component {
	render(){
		const { data } = this.props;
		return (
				<div className="container footer">
					<div className="row align-items-end justify-content-between">
						<div className="col-5">

						<p class="text-left">
						call: 8734627868
						<br></br><small>
							email:  <a  href="mailto:info@split4ever.com"> info@split4ever.com</a>
						<br></br>split4ever 2016 &copy;</small>
						</p>
						</div>
						<div className="col-5">
							<p class="text-right">
								split4ever
								<br></br>
								<small>peças clássicas VW: venda e compra<br></br>usadas | old stock | new old stock | recondicionadas </small>
							</p>

						</div>
					</div>
				</div>
				)
	}	
};

export default Footer;

