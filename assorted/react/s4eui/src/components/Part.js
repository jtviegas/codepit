/**
 * 
 */
import React from 'react';

class Part extends React.Component {
	render(){
		const { name, notes, price, category, subcategory, images } = this.props;
		return (
				<div className="part">
					<h3>{name}</h3>
					<p>
						<span>{price}</span>
						<span>{category}</span>
						<span>{subcategory}</span>
						<span>{notes}</span>
						<span>{images}</span>
					</p>
				</div>
				)
	}
};

export default Part;