/**
 * 
 */
class EndCurrencyNotSupportedError extends Error {
  constructor(...params) {
    super(...params);
    if (Error.captureStackTrace) {
      Error.captureStackTrace(this, EndCurrencyNotSupportedError);
    }
  }
}


class SourceCurrencyNotSupportedError extends Error {
	  constructor(...params) {
	    super(...params);
	    if (Error.captureStackTrace) {
	      Error.captureStackTrace(this, SourceCurrencyNotSupportedError);
	    }
	  }
	}

class BadBasketError extends Error {
	  constructor(...params) {
	    super(...params);
	    if (Error.captureStackTrace) {
	      Error.captureStackTrace(this, BadBasketError);
	    }
	  }
	}


module.exports = {EndCurrencyNotSupportedError: EndCurrencyNotSupportedError
		, SourceCurrencyNotSupportedError: SourceCurrencyNotSupportedError
		, BadBasketError: BadBasketError
};
