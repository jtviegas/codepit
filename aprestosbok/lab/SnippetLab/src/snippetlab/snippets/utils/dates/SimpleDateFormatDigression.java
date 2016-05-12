/**
 * SimpleDateFormatDigression.java Copyright (C)Wincor-Nixdorf Portugal
 * 2010/01/15 18:53:44
 * @author	joao.viegas
 *
 * TODO
 * 
 * CHANGES:
 */
package snippetlab.snippets.utils.dates;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import snippetlab.snippets.AbstractSnippet;

/**
 * SimpleDateFormatDigression
 * ...description... 
 *
 * @see
 * @since
 */
public class SimpleDateFormatDigression extends AbstractSnippet
{
	
	JTextField _tf_pattern = new JTextField(30);
	JTextField _tf_date = new JTextField(30);
	JLabel _result = new JLabel("             ");
	/**
	 * @see snippetlab.snippets.AbstractSnippet#method()
	 */
	@Override
	public void method()
	{
		SimpleDateFormat format = new SimpleDateFormat();
		format.applyLocalizedPattern("yyyyMMdd");

		try
		{
			Date date = format.parse(_tf_date.getText());
			format.applyPattern(_tf_pattern.getText());
			
			_result.setText(format.format(new Date()));
			
			Calendar xmas = new GregorianCalendar(1998, Calendar.DECEMBER, 25); 
			int dayOfWeek = xmas.get(Calendar.DAY_OF_WEEK); 
			// 6=Friday 
			Calendar cal = new GregorianCalendar(2003, Calendar.JANUARY, 1); 
			dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); 
			 
			// 4=Wednesday 
			
	
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}

	/**
	 * @see snippetlab.snippets.AbstractSnippet#init()
	 */
	@Override
	public void init()
	{
		JPanel _p_s = new JPanel(new FlowLayout());
		JPanel _a = new JPanel(new FlowLayout());
		JPanel _b = new JPanel(new FlowLayout());
		
		_a.add(new JLabel("date"));
		_a.add(_tf_date);
		_b.add(new JLabel("pattern"));
		_b.add(_tf_pattern);
		
		_p_s.add(_a);
		_p_s.add(_b);
		_p_s.add(new JLabel("result"));
		_p_s.add(_result);
		
		panel.add(_p_s, BorderLayout.CENTER);
		super.init();
	}

}
