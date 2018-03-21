
import React from 'react';
import PartWidget from './PartWidget';
import DataStore from '../services/DataStore';

class PartsView extends React.Component {
	
	constructor(props) {
		super(props);
		this.service = new DataStore();
		this.vars = {
				batchSize: 20
				, partsIndex : 0	
		};
		this.loadCategories = this.loadCategories.bind(this);
		this.categoryChange = this.categoryChange.bind(this);
		this.subcategoryChange = this.subcategoryChange.bind(this);
		
		this.state = {
				categoryId: 1
				, subcategoryId: 4
				, categories: []
				, subcategories: []
		};
		
	  }
	
	loadCategories(){
		console.log("going to load categories")
		this.service.getCategories((err,os) => {
			if(err)
				console.log("there is a challenge loading categories", err);
			else 
				this.setState({categories: os})
		});
	}
	
	categoryChange(event){
		event.preventDefault();
		console.log("categoryId: %d", this.state.categoryId)
		console.log("subcategoryId: %d", this.state.subcategoryId)
		let id = parseInt(event.target.value);
		console.log("new category id: %d", id)
		this.setState({categoryId: id});
		this.setState({subcategoryId: 0})
		console.log("going to load subcategories from category %d", id)
		this.service.getSubcategories(id, (err,os) => {
			if(err)
				console.log("there is a challenge loading subcategories", err);
			else 
				this.setState({subcategories: os})
		});
	}
	
	subcategoryChange(event){
		event.preventDefault();
		console.log("categoryId: %d", this.state.categoryId)
		console.log("subcategoryId: %d", this.state.subcategoryId)
		let id = parseInt(event.target.value);
		this.setState({subcategoryId: id})
		console.log("new subcategory: %d", id)


	}
	
	componentWillMount() {
		  this.loadCategories();
		  if(0 < this.state.categoryId){
			  this.service.getSubcategories(this.state.categoryId, (err,os) => {
					if(err)
						console.log("there is a challenge loading subcategories", err);
					else 
						this.setState({subcategories: os})
				});
		  }
	}


	
	render(){
		
		return (
				<div className="page-element">
			        <div className="row justify-content-between align-items-center">
			          		<div className="col-6 align-self-end">
			          			<select className="form-control" value={this.state.categoryId} onChange={this.categoryChange}>
			          			<option value={0}>seleccione a categoria</option>
			          			{ this.state.categories.map( (o, i) => <option key={i} value={o.id}>{o.name}</option> ) } 
						      	</select>
			          		</div>
			          		<div className="col-6 align-self-end">
			          			<select className="form-control" value={this.state.subcategoryId} onChange={this.subcategoryChange}>
			        			<option value={0}>seleccione a subcategoria</option>
			        			{ this.state.subcategories.map( (o, i) => <option key={i} value={o.id}>{o.name}</option> ) } 
						      	</select>
			          		</div>
			          </div>
				</div>

			)
	}
	

	
};

PartsView.defaultProps = { category:null, subcategory:null, selection:null, partsIndex: 0, batchSize: 20 };

export default PartsView;