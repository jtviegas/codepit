package org.challenges.rab.statproc;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stax.StAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.challenges.rab.statproc.exceptions.StatementFormatException;
import org.challenges.rab.statproc.exceptions.StatementProcessorException;
import org.challenges.rab.statproc.statement.Statement;
import org.challenges.rab.statproc.validator.StatementValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class XmlStatementProcessor implements StatementProcessor {

	private static final String XSD_FILE = "records.xsd";
	private static final Logger logger = LoggerFactory.getLogger(XmlStatementProcessor.class);
	private final StatementValidator validator;
	private Map<String, BiConsumer<Statement, String>> functionMap;
	private Validator xmlValidator;

	public XmlStatementProcessor(StatementValidator validator) {
		logger.trace("[()|in]");
		this.validator = validator;
		init();
		logger.trace("[()|out]");
	}

	private void init() {
		logger.trace("[init|in]");
		functionMap = new HashMap<String, BiConsumer<Statement, String>>();
		functionMap.put("accountNumber", (r, o) -> r.setAccountNumber(o));
		functionMap.put("description", (r, o) -> r.setDescription(o));
		functionMap.put("startBalance", (r, o) -> r.setStartBalance(new BigDecimal(o)));
		functionMap.put("mutation", (r, o) -> r.setMutation(new BigDecimal(o)));
		functionMap.put("endBalance", (r, o) -> r.setEndBalance(new BigDecimal(o)));
		logger.trace("[init|out]");
	}

	private void validateXml(XMLInputFactory factory, Path file) throws StatementFormatException {
		logger.trace("[validate|in] file: {}", file.toString());
		XMLStreamReader streamReader = null;
		try {
			if (null == xmlValidator) {
				// lazy load the xml validator
				Path xsd = Paths.get(XmlStatementProcessor.class.getClassLoader().getResource(XSD_FILE).toURI());
				SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
				Schema schema = schemaFactory.newSchema(xsd.toFile());
				xmlValidator = schema.newValidator();
			}
			streamReader = factory.createXMLStreamReader(Files.newBufferedReader(file));
			xmlValidator.validate(new StAXSource(streamReader));
			logger.debug("[validate] correctly validated file: {}", file.toString());
		} catch (Exception e) {
			logger.error("[validate] could not validate file: {}", file.toString());
			throw new StatementFormatException("couldn't validate the statements xml doc", e);
		} finally {
			if (null != streamReader)
				try {
					streamReader.close();
				} catch (XMLStreamException e) {
					logger.error("[validate]...when trying to close the reader...", e);
				}
			logger.trace("[validate|out]");
		}

	}

	@Override
	public String[] process(Path file) throws StatementFormatException, StatementProcessorException {
		logger.trace("[process|in] file: {}", (null != file ? file.toString() : "null"));
		if (null == file)
			throw new IllegalArgumentException("must provide file parameter");

		if (!(file.toFile().exists() && file.toFile().isFile() && file.toFile().canRead()))
			throw new StatementProcessorException(String.format("can't read file: %s", file.toString()));

		List<String> notvalid = null;
		XMLStreamReader streamReader = null;
		try {

			final XMLInputFactory factory = XMLInputFactory.newInstance();
			
			// validate the xml, we just have to, even if it means opening another reader
			validateXml(factory, file);

			streamReader = factory.createXMLStreamReader(Files.newBufferedReader(file));
			while (streamReader.hasNext()) {

				streamReader.next();
				switch (streamReader.getEventType()) {
				case XMLStreamReader.START_ELEMENT:
					String qName = streamReader.getLocalName();
					if (qName.equals("records")) {
						// we are starting the processing
						notvalid = new ArrayList<String>();
						continue;
					}
					if (qName.equals("record")) {
						Statement statement = processRecord(streamReader);
						if (!validator.validate(statement))
							notvalid.add(String.format("%d,%s", statement.getReference(), statement.getDescription()));

					}
					break;

				case XMLStreamReader.END_ELEMENT:
					if (!streamReader.getLocalName().equals("records")) // sanity check
						throw new StatementProcessorException("end element different than 'records'!");
					break;
				}
			}

			String[] r = notvalid.toArray(new String[] {});
			logger.info("[process]...finished processing statements. Error report: {}", Arrays.toString(r));
			return r;

		} catch (StatementFormatException sfe) {
			throw sfe;
		} catch (Exception e) {
			throw new StatementProcessorException(e);
		} finally {
			if (null != streamReader)
				try {
					streamReader.close();
				} catch (XMLStreamException e) {
					logger.error("[process]...when trying to close the reader...", e);
				}
			logger.trace("[process|out]");
		}

	}

	private Statement processRecord(XMLStreamReader streamReader) throws StatementFormatException {
		logger.trace("[processRecord|in]");
		final Statement r = new Statement();
		try {
			logger.debug("[processRecord] processing ");
			r.setReference(Integer.parseInt(streamReader.getAttributeValue(0)));

			BiConsumer<Statement, String> consumer = null;

			while (streamReader.hasNext()) {
				streamReader.next();
				if (streamReader.getEventType() == XMLStreamReader.END_ELEMENT
						&& streamReader.getLocalName().equals("record"))
					break;

				switch (streamReader.getEventType()) {
				case XMLStreamReader.START_ELEMENT:
					// define the proper setter
					consumer = functionMap.get(streamReader.getLocalName());
					break;
				case XMLStreamReader.CHARACTERS:
					String content = streamReader.getText().trim();
					if (!content.isEmpty())
						consumer.accept(r, content);

				}

			}
			return r;

		} catch (Exception e) {
			throw new StatementFormatException(e);
		} finally {
			logger.trace("[processRecord|out] statement: {}", r);
		}
	}

}
