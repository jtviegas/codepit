package snippetlab.snippets.ee;

import javax.swing.JOptionPane;
import javax.xml.ws.WebServiceRef;

import snippetlab.snippets.AbstractSnippet;

import com.d2w.labs.j2ee.snippets.book.bjee6wg3.ch14.CardValidator;
import com.d2w.labs.j2ee.snippets.book.bjee6wg3.ch14.CardValidatorService;
import com.d2w.labs.j2ee.snippets.book.bjee6wg3.ch14.CreditCard;

public class CardValidatorWebServiceClient extends AbstractSnippet
{

	@Override
	public void method()
	{
		CreditCard _card = new CreditCard();
		_card.setNumber("2313213132136");
		_card.setControlNumber(2234);
		_card.setExpiryDate("10/14");
		_card.setType("visa");
	
		CardValidatorService service = new CardValidatorService();
	 
		CardValidator _validator = service.getCardValidatorPort();
		boolean _r = _validator.validate(_card);
		
		JOptionPane.showMessageDialog(this.frame, "card:" + _card.getNumber() + " is valid?" + _r);
		
		CreditCard _card2 = new CreditCard();
		_card2.setNumber("2313213132135");
		_card2.setControlNumber(2234);
		_card2.setExpiryDate("10/14");
		_card2.setType("visa");
		
		boolean _r2 = _validator.validate(_card2);
		JOptionPane.showMessageDialog(this.frame, "card:" + _card2.getNumber() + " is valid?" + _r2);

	}

}
