/**
 * 
 */
import React from 'react';

class PartsService {

	constructor(props) {
		this.props = props;
	}

	getParts(startIndex, num, callback) {
		
	};
	
	getCategories(callback) {
		return [{name: "e", id:1}, {name: "r", id:2}, {name: "re", id:3}, {name: "io", id:4}];
	};
	
	getSubcategories(category, callback) {
		return [{name: "a", id:1}, {name: "b", id:2}, {name: "c", id:3}, {name: "d", id:4} ];
	};



}
;

export default PartsService;