package org.challenges.adthena.shopbasket.services;

import java.io.IOException;
import java.io.OutputStream;

import org.challenges.adthena.shopbasket.exceptions.AppException;
import org.challenges.adthena.shopbasket.model.Basket;
import org.challenges.adthena.shopbasket.model.PriceUtils;
import org.springframework.stereotype.Service;

@Service
public class BasketPrinterImpl implements BasketPrinter {

  private static final String SUBTOTAL_PATTERN = "Subtotal: %s", NO_OFFERS="(No offers available)", TOTAL_PATERN="Total: %s";  

  @Override
  public void print(Basket basket, OutputStream output) throws AppException {
    try {
      output.write(print(basket).getBytes());
    } catch (IOException e) {
      throw new AppException("! problems writing on the output stream !");
    }
  }

  @Override
  public String print(Basket basket) {
    StringBuffer sb = new StringBuffer();
    sb.append( String.format(SUBTOTAL_PATTERN,PriceUtils.toString( basket.getSubtotal() ) ) );
    sb.append(System.getProperty("line.separator"));
    
    if( basket.getPromotionDetails().isEmpty() )
      sb.append(NO_OFFERS);
    else 
      sb.append(String.join(System.getProperty("line.separator"), basket.getPromotionDetails()));
    
    sb.append(System.getProperty("line.separator"));
    sb.append( String.format(TOTAL_PATERN,PriceUtils.toString( basket.getTotal() ) ) );

    return sb.toString();
  }

}
