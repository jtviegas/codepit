const configuration =  {
    pagination: {
        n: 12
    }
    , api: {
        url: "http://localhost:3000"
    }
    , datastore: {
        mode: 'mock'
        , defaultPageSize: 12
        , api: {
        	url: "http://localhost:8080/api"
        } 
    }
};
export default configuration;