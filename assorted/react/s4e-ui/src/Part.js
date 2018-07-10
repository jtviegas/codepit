/**
 * 
 */
import React from 'react';
import Carousel from './Carousel';
import PartData from './PartData';
import PartAction from './PartAction';

const Part = ({part}) => 
	<section className="part-section" id={part.id}>
		<h1>{part.name}</h1>
		<Carousel className="part-carousel" images={part.images} />
		<PartData  data={part} />
		<PartAction id={part.link} />
	</section>
		
export default Part;
