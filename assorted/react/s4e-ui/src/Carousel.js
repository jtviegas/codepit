/**
 * 
 */
import React from 'react';
const Carousel = ({images}) => 
	<div>
		{images.map( (image,i) =>
				<img alt={i} className="carousel-img" key={i} src="{image.data}" />

		)}
	</div>
		
export default Carousel;
