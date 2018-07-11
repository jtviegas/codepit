
import React from 'react';
import Header from './Header';
import Main from './Main';
import Footer from './Footer';
import DataStore from '../services/DataStore';

class App extends React.Component {
	
	constructor(props){
		super(props)
		this.store = new DataStore();
		this.state = {
			narrative: props.narrative || 'browse' // ['browse', 'detail']
			, mode: props.mode || 'ro'	// ['ro', 'rw']
			, objectId: (props.objectId) ? parseInt(props.objectId) : 0
			, object: null
			, categoryId: (props.categoryId) ? parseInt(props.categoryId) : 0
			, categories: []
			, subcategoryId: (props.subcategoryId) ? parseInt(props.subcategoryId) : 0
			, subcategories: []
			, categorisation : {}
		}
	}
	
	componentWillMount() {
		
		if(0 < this.state.objectId){
			this.store.getPart(this.state.objectId, (e,o) => {
				if(e)
					console.log('challenges getting part', e);
				else
					this.setState({object: o});
			});
		}
		
		this.store.getCategories((e,os) => {
			if(e)
				console.log('challenges getting categories', e);
			else{
				let cats = [];
				let categ = {}
				for( i=0; i < os.length; i++){
					let c = os[i];
					cats.push(c.name)
					categ[c.name]=c.subcategories
				}
				this.setState({categories: cats});
				this.setState({categorisation: categ});
			}
				
		});

	}
	
	render(){
		const { narrative, mode } = this.state;
		return (
				<div className="app">
					<Header {this.state} />
					<Main />	
					<Footer />
				</div>
				)
	}
};

App.propTypes = {
		narrative: PropTypes.string
		, mode: PropTypes.string
		, objectId: PropTypes.number
		, object: PropTypes.object
		, categoryId: PropTypes.number
		, categories: PropTypes.array
		, subcategoryId: PropTypes.number
		, subcategories: PropTypes.object
}

export default App;