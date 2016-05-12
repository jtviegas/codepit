package org.aprestos.labs.snippets.impl.serialization.kryo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Kryo.DefaultInstantiatorStrategy;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class GenericSerializer<T> {

	private static final ThreadLocal<Kryo> kryoInstance = new ThreadLocal<Kryo>() {
		@Override
		protected Kryo initialValue() {
			Kryo kryo = new Kryo();
			kryo.setInstantiatorStrategy(new DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
			return kryo;
		}
	};

	private T obj;
	
	public GenericSerializer(T obj){
		this.obj = obj;
	}
	
	public void serialize(final String filePath)
			throws IOException {
		kryoInstance.get().register(obj.getClass());
		Output output = new Output(new GZIPOutputStream(new FileOutputStream(
				filePath)));
		kryoInstance.get().writeObject(output, obj);
		output.close();

	}

	public T deSerialize(final String filePath)
			throws IOException {
		T result = null;

		Input input = new Input(new GZIPInputStream(new FileInputStream(
				filePath)));
		result = (T) kryoInstance.get().readObject(input, obj.getClass());
		input.close();

		return result;
	}
	
	public byte[] obj2bytes() throws Exception {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		kryoInstance.get().register(obj.getClass());
		Output output = new Output(new GZIPOutputStream(bo));
		kryoInstance.get().writeObject(output, obj);
		output.close();
		return bo.toByteArray();
	}


	@SuppressWarnings("unchecked")
	public T bytes2obj(byte[] bytes) throws Exception {
		T result = null;
		Input input = new Input(new GZIPInputStream(new ByteArrayInputStream(bytes)));
		result = (T)kryoInstance.get().readObject(input, obj.getClass());
		input.close();

		return result;
	}

	
}
