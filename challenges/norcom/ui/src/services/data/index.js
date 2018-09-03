import DataStoreFactory from '../store/DataStoreFactory';

class DataService {

	constructor(config) {
        this.store = new DataStoreFactory(config.datastore).get();
	}

	search(term, cb) {
		console.log('[DataService|getObjs|in] term:', term);
		console.log('[DataService|getObjs|out]');
        return this.store.searchObjs(term, cb);
    };
    
    getObj(id, cb) {
		console.log('[DataService|getObj|in] id:', id);
		console.log('[DataService|getObj|out]');
        return this.store.getObj(id, cb);
    };

};

export default DataService;