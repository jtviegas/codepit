
import React from 'react';

class Header extends React.Component {
	render(){
		const { data } = this.props;
		return (
				<div className="container">
					<header className="header">
			        <div className="row justify-content-between align-items-center">
			          <div className="col-4 pt-1">
				          <address>
						    <strong>split4ever</strong><br></br>
						    Praceta do fusca 69<br></br>
						    Sao Francisco, Alcochete<br></br>
						    (123) 456-7890<br></br>
						    info@split4ever.com 
					    </address>
			          </div>
			          <div className="col-8 justify-content-end align-self-center text-right">
			          	<h3>
			          		peças clássicas VW: venda e compra<br></br>
			          		<small className="text-muted"> usadas | old stock | new old stock | recondicionadas</small>
				        </h3>

			          </div>
			          	
			        </div>
			      </header>
				</div>
				
		
				)
	}
};

export default Header;