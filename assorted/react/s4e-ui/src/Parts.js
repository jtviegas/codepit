/**
 * 
 */
import React from 'react';
import Part from './Part';


const Parts = ({parts}) => {
			return parts.map( (part,i) => 
				<Part  key={i} part={part} />
		)};


export default Parts;
