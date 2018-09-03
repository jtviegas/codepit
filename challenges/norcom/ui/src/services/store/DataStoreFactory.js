import MockDataStore from './mock/index.js';
import RestDataStore from './rest/index.js';

class DataStoreFactory {

	constructor(props) {
        this.config = props;
        if(this.config.mode === 'mock'){
                this.store = new MockDataStore(this.config)
        }
        else
        	this.store = new RestDataStore(this.config)
	}

	get() {
        return this.store;
	};

};

export default DataStoreFactory;