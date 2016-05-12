/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.documentsdigressions;

/**
 *
 * @author jtviegas
 */
public interface DocumentValidator 
{
	/**
	 * Obtém o atributo FormatValid.
	 * 
	 * @param text
	 *            Descrição do parâmetro.
	 * @return O valor do atributo FormatValid.
	 */
	public boolean isFormatValid(String text);

	/**
	 * Aqui deverá ser colocada a descrição do método.
	 * 
	 * @param text
	 *            Descrição do parâmetro.
	 * @param str
	 *            Descrição do parâmetro.
	 * @return Descrição do valor de retorno.
	 */
	public boolean canInsert(String text, String str);

	/**
	 * Aqui deverá ser colocada a descrição do método.
	 * 
	 * @param text
	 *            Descrição do parâmetro.
	 * @param len
	 *            Descrição do parâmetro.
	 * @return Descrição do valor de retorno.
	 */
	public boolean canRemove(String text, int len);
}
