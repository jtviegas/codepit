
import React from "react";
import ReactDOM from "react-dom";
import Slider from "react-slick";
import "./Carousel.css";

class Carousel extends React.Component {
	  render() {
		  var settings = {
			      dots: true,
			      infinite: true,
			      speed: 1000,
			      slidesToShow: 1,
			      slidesToScroll: 1,
			      autoplay: this.props.auto
			    };
	    return (
	      <div className="carousel">
	        <Slider {...settings}>
	          <div>
	            <img className="d-block w-100" src="http://placekitten.com/g/400/200" />
	          </div>
	          <div>
	            <img className="d-block w-100" src="http://placekitten.com/g/400/200" />
	          </div>

	        </Slider>
	      </div>
	    );
	  }
	}

export default Carousel;