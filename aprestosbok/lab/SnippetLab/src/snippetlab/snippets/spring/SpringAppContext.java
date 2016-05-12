package snippetlab.snippets.spring;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringAppContext
{
	private static SpringAppContext instance;
	private static final String PROPS_SUFFIX = ".properties",
			PROPERTY_SPRING_CONTEXT = "spring.context";

	private ApplicationContext context;

	private SpringAppContext() throws IOException
	{
		init();

	}

	public static SpringAppContext getInstance() throws IOException
	{
		if (null == instance)
			instance = new SpringAppContext();

		return instance;
	}

	public ApplicationContext getContext()
	{
		return this.context;
	}

	private void init() throws IOException
	{

		InputStream is = SpringAppContext.class.getResourceAsStream(SpringAppContext.class.getSimpleName() + PROPS_SUFFIX);
		Properties props = new Properties();
		props.load(is);
		is.close();
		is = null;
		context = new ClassPathXmlApplicationContext("/" + SpringAppContext.class.getPackage().getName()
						.replace('.', '/') + "/" + props.getProperty(PROPERTY_SPRING_CONTEXT) + ".xml");
	}

}
