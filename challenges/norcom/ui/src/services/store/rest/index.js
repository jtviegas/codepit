import axios from 'axios';

class datastore {


	constructor(props) {
		console.log('props', props)
        this.url = props.api.url;
	}

	find(term, callback) {
        axios.get(this.url + `/query?query=${term}`)
            .then(response => callback(null,response)).catch(error => callback(error));
	};

}
;

export default datastore;