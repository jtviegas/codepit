
import React from 'react';

class Dummy extends React.Component {
	render(){
		const { data } = this.props;
		return (
				<div className="container">
				
			        <div className="row justify-content-between align-items-center">
			          <div className="col-6 pt-1">

				          
			          </div>
	
			          <div className="col-6 justify-content-end align-self-center text-right">
			          	<h4>
			          		peças clássicas VW: venda e compra<br></br>
			          		<small className="text-muted"> usadas | old stock | new old stock | recondicionadas</small>
				        </h4>

			          </div>
			          	
			        </div>
	
				</div>
				
		
				)
	}
};

export default Dummy;