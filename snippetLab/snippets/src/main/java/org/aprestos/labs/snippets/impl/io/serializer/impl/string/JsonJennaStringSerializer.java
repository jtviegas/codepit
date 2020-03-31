package org.aprestos.labs.snippets.impl.io.serializer.impl.string;

import com.google.gson.Gson;
import org.aprestos.labs.snippets.impl.io.serializer.exceptions.SerializerException;
import org.aprestos.labs.snippets.impl.io.serializer.interfaces.StringSerializer;
import org.aprestos.labs.snippets.impl.io.serializer.objects.StringListOperation;

public class JsonJennaStringSerializer implements StringSerializer<StringListOperation> {

	
	public String toString(StringListOperation o) throws SerializerException {
		String result;
		Gson gson = new Gson();
		result = gson.toJson(o);
		return result;
	}



	public StringListOperation toObject(String s) throws SerializerException {
		StringListOperation result = null;
		Gson gson = new Gson();
		result = gson.fromJson(s, StringListOperation.class);
		return result;
	}

}
