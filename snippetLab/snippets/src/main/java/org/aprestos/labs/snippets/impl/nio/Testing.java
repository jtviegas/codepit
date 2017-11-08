package org.aprestos.labs.snippets.impl.nio;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Testing {
    
    private String text;
    private int num;
    
    public Testing() {
	// TODO Auto-generated constructor stub
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNumber() {
        return num;
    }

    public void setNumber(int number) {
        this.num = number;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + num;
	result = prime * result + ((text == null) ? 0 : text.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Testing other = (Testing) obj;
	if (num != other.num)
	    return false;
	if (text == null) {
	    if (other.text != null)
		return false;
	} else if (!text.equals(other.text))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	
	return new ToStringBuilder(this).append("text", text).append("number", num).toString();
    }
    
    

}
