
import React from 'react';

class PartsView extends React.Component {
	render(){
		const { data } = this.props;
		return (
				<div className="page-element">

			        <div className="row justify-content-between align-items-center">
			          		<div className="col-6 align-self-end">
			          			<select className="form-control" placeholder="dasd">
			          			<option value="0">categoria</option>
						      	<option value="1">Ajdaskl</option>
						      	<option value="2">Bdasdsadasd</option>
						      	</select>
			          		</div>
			          		<div className="col-6 align-self-end">
			          			<select className="form-control">
			          				<option value="0">subcategoria</option>
							      	<option value="1">Ajdaskl</option>
							      	<option value="2">Bdasdsadasd</option>
						      	</select>
			          		</div>
			      
			          </div>
				</div>
				
		
				)
	}
};

export default PartsView;