"use strict";

var common = require('../../common');
var logger = common.logger;
var config = require('../../../config');
const BadBasketError = require('../../../representations/errors').BadBasketError;

var BaSeStoreService = function() {

	var getPrice = function(basket) {
		logger.debug('[BaSeStoreService.getPrice] IN');

		let price = 0;
		validateBasket(basket);

		price = calculatePrice(basket);
		let discounts = calculateDiscount(basket);
		console.log('discounts.amt', discounts.amt)
		console.log('price.subtotal', price.subtotal)

		price.total = common.roundToTwoDecimals(price.subtotal - discounts.amt);
		console.log('price.total', price.total)
		price.discounts = discounts.applied;
		price.discountAmt = discounts.amt;
		logger.debug('[BaSeStoreService.getPrice] OUT [%f]', price);
		return price;
	};

	var calculatePrice = function(basket) {
		logger.debug('[BaSeStoreService.calculatePrice] IN');

		let price = {
			subtotal : 0.00,
		};
		for (var i = 0; i < basket.items.length; i++) {
			let item = basket.items[i];

			var finder = function(param) {
				var f = function(e) {
					return e.name === param;
				};
				return {
					f : f
				};
			}(item);
			let storedItem = config.services.store.items.find(finder.f);
			price.subtotal += storedItem.price;
		}
		price.subtotal = common.roundToTwoDecimals(price.subtotal);
		
		logger.debug('[BaSeStoreService.calculatePrice] OUT', price);
		return price;
	};

	var calculateDiscount = function(basket) {
		logger.debug('[BaSeStoreService.calculateDiscount] IN');

		let discounts = {
			applied : [],
			amt : 0.0
		};

		let stats = {};
		for (var i = 0; i < basket.items.length; i++) {
			let item = basket.items[i];
			//get item info from store
			var finder = function(param) {
				var f = function(e) {
					return e.name === param;
				};
				return {
					f : f
				};
			}(item);
			let storedItem = config.services.store.items.find(finder.f);
			//keep stats if it has discount
			if (storedItem.discount) {
				if (!stats[storedItem.name])
					stats[storedItem.name] = {
						count : 1,
						discount : storedItem.discount,
						price : storedItem.price
					};
				else
					stats[storedItem.name].count++;
			}
		}

		let statItems = common.getProperties(stats);
		for (var i = 0; i < statItems.length; i++) {
			let item = statItems[i].value;
			//we know we only have one type of discount so rationale is simple
			//and can be implemented locally, otherwise we would have to
			//externalise the discount processing into its own component
			if ("quantity" == item.discount.condition.type) {
				let n = item.count / item.discount.condition.value;
				if (0 < n) {
					discounts.applied.push(item.discount.name);
					discounts.amt += common.roundToTwoDecimals((1.000 * n * item.discount.condition.value) * item.discount.discount * item.price);
				}
			}
		}

		logger.debug('[BaSeStoreService.calculateDiscount] OUT', discounts);
		return discounts;
	};

	// we could use some validation library for this later
	var validateBasket = function(basket) {
		logger.debug('[BaSeStoreService.validateBasket] IN');

		try {
			let exceptions = '';
			let properties = common.getPropertyNames(basket);
			if (-1 < properties.indexOf("items") && Array.isArray(basket.items)) {
				if (0 >= basket.items.length)
					exceptions += 'no items in basket;';
			} else {
				exceptions += 'no items array in basket;';
			}

			if (-1 == properties.indexOf("currency")) {
				exceptions += 'no currency property in basket;';
			}

			if (0 < exceptions.length)
				throw new BadBasketError(exceptions);
		} finally {
			logger.debug('[BaSeStoreService.validateBasket] OUT');
		}

	};

	return {
		getPrice : getPrice
	};

}();

module.exports = BaSeStoreService;