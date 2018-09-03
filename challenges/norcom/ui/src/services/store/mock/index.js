import uuid from 'uuid';
import store from './data.js';

class MockDataStore {


    constructor(props) {
    	if( !props.defaultPageSize )
    		throw new Error('!!! no defaultPageSize configuration !!!');
    	this.config = props;
        this.data = store;
        
        
    }

    getObjIndex(type, obj){
        let r = -1
        if( obj["Message-ID"] ){
            let s = this.data[type].filter(e => obj["Message-ID"] === e["Message-ID"])
            if(1 === s.length)
                r = this.data[type].indexOf(s[0]);
        }
        return r;
    }

    getIdIndex(type, id){
    	
        let r = -1
        if( id ){
            let s = this.data[type].filter(e => id === e["Message-ID"])
            console.log('found with id:', id, 'the obj array:', s);
            if(1 === s.length)
                r = this.data[type].indexOf(s[0]);
        }
        return r;
    }


    getObj(type, id, cb){
        try{
            let r = null;
            let idx = this.getIdIndex(type, id);
            if( idx > -1 )
                r = this.data[type][idx];
            cb(null, r);
         }
        catch(error){
            cb(error);
        }
    }

    getObjs(type, cb){
    	this.getPagedObjs(type, null, null, cb);
    }
    
    getPagedObjs(type, page, pagesize, cb){
    	console.log('[MockDataStore|getPagedObjs|in]');
		
        try {
        	let r = {
        		objs: []
        		, pages:
        			{
        			first: null
        			, previous: null
        			, next: null
        			, last: null
        		}
        	}
        	let _data = this.data[type];
        	
        	if(0 < _data.length){
        		
        		if( !pagesize )
        			pagesize = this.config.defaultPageSize

        		r.pages.first = 0;
        		r.pages.last = Math.floor(_data.length/pagesize);
        		
        		if( !page )
        			page = 0;
        		
        		let startIndex = page * pagesize
        		if( startIndex >= _data.length )
        			throw new Error("startIndex bigger than data length");
        		
        		let endIndex = startIndex + pagesize - 1
        		if( (startIndex + pagesize) < _data.length )
        			r.pages.next = page + 1;
        		else
        			endIndex = _data.length - 1

        		if( startIndex >= pagesize )
        			r.pages.previous = page - 1;

        		
        		r.objs = _data.slice(startIndex, endIndex+1);
	
        	}
        	
            cb(null, r);
         }
        catch(error){
            cb(error);
        }
        console.log('[MockDataStore|getPagedObjs|out]');
    }


};

export default MockDataStore;


