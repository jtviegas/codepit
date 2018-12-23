package org.challenges.adthena.shopbasket;

import java.util.Arrays;

import org.challenges.adthena.shopbasket.exceptions.AppException;
import org.challenges.adthena.shopbasket.exceptions.InputException;
import org.challenges.adthena.shopbasket.model.Basket;
import org.challenges.adthena.shopbasket.services.BasketPrinter;
import org.challenges.adthena.shopbasket.services.PriceEngine;
import org.challenges.adthena.shopbasket.services.promotions.PromotionEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@SpringBootApplication
public class AppBoot {

	private static final String MSG_NO_ITEMS = "no items input provided";
	private static final String MSG_USAGE = "usage: java -jar shopbasket.jar itemOne itemTwo ...";
	private static final Logger LOGGER = LoggerFactory.getLogger(AppBoot.class);

	@Autowired
	private PriceEngine priceEngine;
	@Autowired
	private PromotionEngine promotionEngine;
	@Autowired
	private BasketPrinter printer;

	public static void main(String[] args) {
		SpringApplication.run(AppBoot.class, args);
	}

	@Bean
	public CommandLineRunner run() {
		LOGGER.trace("[run|in]");
		return (args) -> {
			try {

				if (0 == args.length)
					throw new InputException(MSG_NO_ITEMS);

				doit(args);
			} catch (InputException ie) {
				LOGGER.error("[run] {}", ie.getMessage());
				LOGGER.info(MSG_USAGE);
			} catch (AppException ae) {
				LOGGER.error("[run] {}", ae.getMessage());
			} catch (Exception e) {
				LOGGER.error("[run]", e);
			} finally {
				LOGGER.trace("[run|out]");
			}

		};

	}

	private void doit(String... args) throws AppException {
		LOGGER.trace("[doit|in] file: {}", Arrays.toString(args));
		BasketCalculation calculation = new BasketCalculation(priceEngine, promotionEngine);
		Basket basket = calculation.calculate(args);
		printer.print(basket, System.out);
		LOGGER.trace("[doit|out]");
	}

}
