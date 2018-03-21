/**
 * 
 */
import React from 'react';

class DataStore {

	constructor(props) {
		this.props = props;
	}

	getParts(startIndex, num, callback) {
		
	};
	
	getCategories(callback) {
		callback(null, [{name: "e", id:1}, {name: "r", id:2}, {name: "re", id:3}, {name: "io", id:4}])
	};
	
	getSubcategories(category, callback) {
		if(category == 1)
			callback(null, [{name: "a", id:1}, {name: "b", id:2}, {name: "c", id:3}, {name: "d", id:4} ])
		else
			callback(null, [{name: "de", id:1}, {name: "be", id:2}, {name: "ce", id:3}, {name: "de", id:4} ])
	};



}
;

export default DataStore;