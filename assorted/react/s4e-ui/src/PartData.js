/**
 * 
 */
import React from 'react';
const PartData = (part) => 
	<div>
		<p>
			description: {part.description}
		</p>
		<p>
			category: {part.category}<br/>
			subcategory: {part.subcategory}
		</p>
		<p>
			price: {part.price} 
		</p>
	</div>
		
export default PartData;
