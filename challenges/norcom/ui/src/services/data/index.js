import DataStoreFactory from '../store/DataStoreFactory';

class DataService {

	constructor(config) {
        this.store = new DataStoreFactory(config.datastore).get();
	}

	search(term, callback) {
		console.log('[DataService|getObjs|in] term:', term);
		console.log('[DataService|getObjs|out]');
        return this.store.find(term, callback);
    };

};

export default DataService;