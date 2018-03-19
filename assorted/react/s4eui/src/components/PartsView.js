
import React from 'react';
import PartWidget from './PartWidget';
import PartsService from '../services/PartsService';

class PartsView extends React.Component {
	
	constructor(props) {
		super(props);
		this.service = new PartsService();
		this.vars = {
				batchSize: 20
				, partsIndex : 0	
		}
		this.state = {
				category: null
				, subcategory: null
		};
	  }
	
	
	render(){
		
		const getPartsCallback = function(err,parts){
			if(err){
				return ( "there is a challenge loading parts" )
			}
			else {
				parts.map( (p, i) => { 
					this.vars.partsIndex = p.id;
					return (<PartWidget key={i} {...p} /> ) 
						} )
			}
				
		};
		
		const getSelectItemCallback = function(err,os){
			if(err){
				return ( "there is a challenge loading categories" )
			}
			else {
				os.map( (o, i) => { 
					return (<option value="{o.id}">{o.name}</option> ) 
						} )
			}
				
		}

		
		return (
				<div className="page-element">

			        <div className="row justify-content-between align-items-center">
			          		<div className="col-6 align-self-end">
			          			<select className="form-control">
			          			<option value="0">seleccione a categoria</option>
			          			{ null === this.state.category ? this.service.getCategories(getSelectItemCallback) : ""}
						      	</select>
			          		</div>
			          		<div className="col-6 align-self-end">
			          			<select className="form-control">
			        			<option value="0">seleccione a subcategoria</option>
			        			{ null !== this.state.subcategory ? this.service.getSubcategories(this.state.subcategory, getSelectItemCallback) : ""}
						      	</select>
			          		</div>
			      
			          </div>
			          
			          <div className="row">
			          	{
			          		null !== this.state.category ? this.service.getParts(this.vars.partsIndex, this.vars.batchSize, getPartsCallback) : ""
			          	}
			          </div>
				</div>
				
		
				)
	}
};

PartsView.defaultProps = { category:null, subcategory:null, selection:null, partsIndex: 0, batchSize: 20 };

export default PartsView;