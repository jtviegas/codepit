import store from './data.js';

class MockDataStore {

    constructor(props) {
        this.data = store;
    }

 
    find(term, callback){
    	console.log('[MockDataStore|find|in]');

    	let r = [];
    	
    	let _data = this.data['obj'];
    	for( let i=0; i < _data.length; i++ ){
    		if( -1 < _data[i].body.indexOf(term) )
    			r.push(_data[i]);
    	}
    	callback(null, r);
        console.log('[MockDataStore|find|out]');
    }


};

export default MockDataStore;


