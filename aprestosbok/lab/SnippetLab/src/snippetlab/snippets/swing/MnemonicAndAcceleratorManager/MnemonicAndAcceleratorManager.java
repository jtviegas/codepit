/*
 * MnemonicAndAcceleratorManager.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.swing.MnemonicAndAcceleratorManager;

import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Properties;

import javax.swing.KeyStroke;

public class MnemonicAndAcceleratorManager
{

	private Properties props;

	private static MnemonicAndAcceleratorManager instance;

	private MnemonicAndAcceleratorManager(Properties props){this.props = props;}

	/**
	 * Set configuration to be used by this singleton.
	 * 
	 * @param conf Configuration to be used.
	 */
	public static void initialize(Properties props)
	{
		if(null == instance)
			instance = new MnemonicAndAcceleratorManager(props);
		
	}

	/**
	 * Get singleton instance.
	 * 
	 * @return This singleton instance.
	 */
	public static MnemonicAndAcceleratorManager getInstance()
	{
		return instance;
	}

	/**
	 * Get mnemonic.
	 * 
	 * @param fieldKey Field key.
	 * @return Code for mnemonic.
	 * @throws UndefinedValueException If field haven't mnemonic configuration.
	 */
	public int getMnemonic(String fieldKey) throws UndefinedValueException,IllegalArgumentException
	{
		String value = props.getProperty(fieldKey);

		if (value == null)
			throw new UndefinedValueException();
		else
			if (value.length() == 1) 
			{
				int code = char2code(value.charAt(0));
				return code;
			}
			else
				throw new IllegalArgumentException(
					"invalid value on configuration for mnemonic of field '" + fieldKey	+ "'");
	}

	/**
	 * Get accelerator.
	 * 
	 * @param fieldKey Field key. 
	 * @return Accelerator KeyStroke
	 * @throws UndefinedValueException If field haven't accelerator configuration.
	 */
	public KeyStroke getAccelerator(String fieldKey) throws UndefinedValueException,IllegalArgumentException
	{
		String value = props.getProperty(fieldKey);

		if (value == null)
			throw new UndefinedValueException();
		else {
			int len = value.length();
			if (len == 2 || len == 3) 
			{
				int modifiers = 0;
				for(int i = 0; i<(len-1); ++i) {
					switch(value.charAt(i))
					{
						case 'S' :
						case 's' :
							modifiers |= InputEvent.SHIFT_DOWN_MASK;
							break;
						case 'A' :
						case 'a' :
							modifiers |= InputEvent.ALT_DOWN_MASK;
							break;
						case 'C' :
						case 'c' :
							modifiers |= InputEvent.CTRL_DOWN_MASK;
							break;
						case 'X' :
						case 'x' :
							modifiers = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
							break;
						default:
							throw new IllegalArgumentException(
								"invalid value on configuration for accelerator of field '" + fieldKey	+ "'");
					}
				}				
				KeyStroke ks = KeyStroke.getKeyStroke(char2code(value.charAt(len-1)), modifiers); 
				return ks;
			}		
			throw new IllegalArgumentException(
				"invalid value on configuration for accelerator of field '" + fieldKey	+ "'");
		}
	}
	
	private int char2code(char ch) 
	{
		int code = -1;
		switch(ch) {
			case '0':
				code = KeyEvent.VK_0;
				break;
			case '1':
				code = KeyEvent.VK_1;
				break;
			case '2':
				code = KeyEvent.VK_2;
				break;
			case '3':
				code = KeyEvent.VK_3;
				break;
			case '4':
				code = KeyEvent.VK_4;
				break;
			case '5':
				code = KeyEvent.VK_5;
				break;
			case '6':
				code = KeyEvent.VK_6;
				break;
			case '7':
				code = KeyEvent.VK_7;
				break;
			case '8':
				code = KeyEvent.VK_8;
				break;
			case '9':
				code = KeyEvent.VK_9;
				break;
			case 'a':
			case 'A':
				code = KeyEvent.VK_A;
				break;
			case 'b':
			case 'B':
				code = KeyEvent.VK_B;
				break;
			case 'c':
			case 'C':
				code = KeyEvent.VK_C;
				break;
			case 'd':
			case 'D':
				code = KeyEvent.VK_D;
				break;
			case 'e':
			case 'E':
				code = KeyEvent.VK_E;
				break;
			case 'f':
			case 'F':
				code = KeyEvent.VK_F;
				break;
			case 'g':
			case 'G':
				code = KeyEvent.VK_G;
				break;
			case 'h':
			case 'H':
				code = KeyEvent.VK_H;
				break;
			case 'i':
			case 'I':
				code = KeyEvent.VK_I;
				break;
			case 'j':
			case 'J':
				code = KeyEvent.VK_J;
				break;
			case 'k':
			case 'K':
				code = KeyEvent.VK_K;
				break;
			case 'l':
			case 'L':
				code = KeyEvent.VK_L;
				break;
			case 'm':
			case 'M':
				code = KeyEvent.VK_M;
				break;
			case 'n':
			case 'N':
				code = KeyEvent.VK_N;
				break;
			case 'o':
			case 'O':
				code = KeyEvent.VK_O;
				break;
			case 'p':
			case 'P':
				code = KeyEvent.VK_P;
				break;
			case 'q':
			case 'Q':
				code = KeyEvent.VK_Q;
				break;
			case 'r':
			case 'R':
				code = KeyEvent.VK_R;
				break;
			case 's':
			case 'S':
				code = KeyEvent.VK_S;
				break;
			case 't':
			case 'T':
				code = KeyEvent.VK_T;
				break;
			case 'u':
			case 'U':
				code = KeyEvent.VK_U;
				break;
			case 'v':
			case 'V':
				code = KeyEvent.VK_V;
				break;
			case 'w':
			case 'W':
				code = KeyEvent.VK_W;
				break;
			case 'x':
			case 'X':
				code = KeyEvent.VK_X;
				break;
			case 'y':
			case 'Y':
				code = KeyEvent.VK_Y;
				break;
			case 'z':
			case 'Z':
				code = KeyEvent.VK_Z;
				break;
		}
		return code;		
	}
	
}
