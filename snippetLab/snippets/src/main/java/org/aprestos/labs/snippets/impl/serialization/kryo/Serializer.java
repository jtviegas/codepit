package org.aprestos.labs.snippets.impl.serialization.kryo;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.aprestos.labs.snippets.impl.io.serializer.exceptions.SerializerException;
import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Kryo.DefaultInstantiatorStrategy;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class Serializer {

	private static final ThreadLocal<Kryo> kryoInstance = new ThreadLocal<Kryo>() {
		@Override
		protected Kryo initialValue() {
			Kryo kryo = new Kryo();
			kryo.setInstantiatorStrategy(new DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
			return kryo;
		}
	};

	public void serialize(final Object obj, final String filePath)
			throws FileNotFoundException {
		kryoInstance.get().register(obj.getClass());
		Output output = new Output(new FileOutputStream(filePath));
		kryoInstance.get().writeObject(output, obj);
		output.close();

	}

	public Object deSerialize(final String filePath, final Class<?> clazz)
			throws FileNotFoundException, ClassNotFoundException {
		Object result = null;
		
		Input input = new Input(new FileInputStream(filePath));
		result = (Object) kryoInstance.get().readObject(input, Class.forName(clazz.getName()));
		input.close();

		return result;
	}

	public void serializeCompressedGzip(final Object obj, final String filePath)
			throws IOException {

		kryoInstance.get().register(obj.getClass());
		Output output = new Output(new GZIPOutputStream(new FileOutputStream(
				filePath)));
		kryoInstance.get().writeObject(output, obj);
		output.close();

	}

	public Object deSerializeCompressedGzip(final String filePath,
			final Class<?> clazz) throws IOException {
		Object result = null;

		Input input = new Input(new GZIPInputStream(new FileInputStream(
				filePath)));
		result = (Object) kryoInstance.get().readObject(input, clazz);
		input.close();

		return result;
	}

	@SuppressWarnings("resource")
	public void serializeJ(final Object obj, final String filePath)
			throws SerializerException {
		FileChannel channel = null;
		try {
			channel = new RandomAccessFile(filePath, "rws").getChannel();
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream so = new ObjectOutputStream(bo);
			so.writeObject(obj);
			so.flush();
			channel.write(ByteBuffer.wrap(bo.toByteArray()));

		} catch (Exception e) {
			throw new SerializerException(e);
		} finally {
			if (null != channel) {
				try {
					channel.close();
				} catch (IOException e) {
				}
			}
			channel = null;
		}
	}

	public Object deserializeJ(final String filePath) throws SerializerException {

		Object result = null;
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(
					filePath));

			result = ois.readObject();
		} catch (Exception e) {
			throw new SerializerException(e);
		} finally {
			if (null != ois) {
				try {
					ois.close();
				} catch (IOException e) {
				}
			}
			ois = null;
		}

		return result;
	}
	


}
