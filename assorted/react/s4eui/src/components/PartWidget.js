/**
 * 
 */
import React from 'react';
import Carousel from './Carousel';


class PartWidget extends React.Component {
	
	constructor(props) {
	    super(props);
	  }
	
	render(){
		const { name, notes, price, category, subcategory, images } = this.props;
		var settings = {
			      dots: true
			    };
		return (
				<div className="part">
				<form>
				  <div className="form-group">
				    <label htmlFor="inputName">name</label>
				    <input type="text" className="form-control" id="inputName" aria-describedby="nameHelp" placeholder="Enter item name"/>
				    <small id="nameHelp" className="form-text text-muted">a short name of the item</small>
				  </div>
				  <div className="form-group">
				    <label htmlFor="inputPrice">price</label>
				    <input type="number" className="form-control" id="inputPrice" aria-describedby="priceHelp" placeholder="Enter item price"/>
				    <small id="priceHelp" className="form-text text-muted">the price of the item</small>
				  </div>
				  <div className="form-group">
				    <label htmlFor="selectCategory">category</label>
				    <select className="form-control" id="selectCategory">
				      <option>1</option>
				      <option>2</option>
				    </select>
				  </div>
				  <div className="form-group">
				    <label htmlFor="selectSubcategory">subcategory</label>
				    <select className="form-control" id="selectSubcategory">
				      <option>1</option>
				      <option>2</option>
				    </select>
				  </div>
				  <div className="form-group">
				    <label htmlFor="textNotes">notes</label>
				    <textarea className="form-control" id="textNotes" rows="3"></textarea>
				  </div>
				  
				  <div className="form-group">
				  	<input type="file" accept="image/*" multiple="true" className="form-control"></input>
				  	<Carousel auto={false}/>
				  </div>
				  <div className="form-row align-items-center">
				  	<div className="col-auto">
				      <button type="submit" className="btn btn-primary mb-2">Delete</button>
				    </div>
				  	<div className="col-auto">
				      <button type="submit" className="btn btn-primary mb-2">Submit</button>
				    </div>			    
				  </div>
				</form>

				</div>
				)
	}
};

export default PartWidget;