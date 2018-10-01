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
	private static final Logger LOGGER = LoggerFactory.getLogger(XmlStatementProcessor.class);
	private final StatementValidator validator;
	private Map<String, BiConsumer<Statement, String>> functionMap;
	private Validator xmlValidator;

	public XmlStatementProcessor(final StatementValidator validator) {
		LOGGER.trace("[()|in]");
		this.validator = validator;
		init();
		LOGGER.trace("[()|out]");
	}

	private void init() {
		LOGGER.trace("[init|in]");
		functionMap = new HashMap<String, BiConsumer<Statement, String>>();
		functionMap.put("accountNumber", (r, o) -> r.setAccountNumber(o));
		functionMap.put("description", (r, o) -> r.setDescription(o));
		functionMap.put("startBalance", (r, o) -> r.setStartBalance(new BigDecimal(o)));
		functionMap.put("mutation", (r, o) -> r.setMutation(new BigDecimal(o)));
		functionMap.put("endBalance", (r, o) -> r.setEndBalance(new BigDecimal(o)));
		LOGGER.trace("[init|out]");
	}

	private void validateXml(final XMLInputFactory factory, final Path file) throws StatementFormatException {
		LOGGER.trace("[validate|in] file: {}", file.toString());
		XMLStreamReader streamReader = null;
		try {
			if (null == xmlValidator) {
				// lazy load the xml validator
				Path xsd = Paths.get( Thread.currentThread().getContextClassLoader().getResource(XSD_FILE).toURI());
				SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
				Schema schema = schemaFactory.newSchema(xsd.toFile());
				xmlValidator = schema.newValidator();
			}
			streamReader = factory.createXMLStreamReader(Files.newBufferedReader(file));
			xmlValidator.validate(new StAXSource(streamReader));
			LOGGER.debug("[validate] correctly validated file: {}", file.toString());
		} catch (Exception e) {
			LOGGER.error("[validate] could not validate file: {}", file.toString());
			throw new StatementFormatException("couldn't validate the statements xml doc", e);
		} finally {
			if (null != streamReader)
				try {
					streamReader.close();
				} catch (XMLStreamException e) {
					LOGGER.error("[validate]...when trying to close the reader...", e);
				}
			LOGGER.trace("[validate|out]");
		}

	}

	@Override
	public String[] process(final Path file) throws StatementFormatException, StatementProcessorException {
		LOGGER.trace("[process|in] file: {}", null != file ? file.toString() : "null");
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
			notvalid = new ArrayList<String>();
			
			while (streamReader.hasNext()) {

				streamReader.next();
				
				if( streamReader.getEventType() == XMLStreamReader.START_ELEMENT ) {
				  String qName = streamReader.getLocalName();
          if ("records".equals(qName)) {
            continue;
          }
          if ("record".equals(qName)) {
            Statement statement = processRecord(streamReader);
            if (!validator.validate(statement))
              notvalid.add(String.format("%d,%s", statement.getReference(), statement.getDescription()));

          }
				}
				else if(streamReader.getEventType() == XMLStreamReader.END_ELEMENT  && !streamReader.getLocalName().equals("records")) {
            throw new StatementProcessorException("end element different than 'records'!");
				}

			}

			String[] result = notvalid.toArray(new String[] {});
			LOGGER.info("[process]...finished processing statements. Error report: {}", Arrays.toString(result));
			return result;

		} catch (StatementFormatException sfe) {
			throw sfe;
		} catch (Exception e) {
			throw new StatementProcessorException(e);
		} finally {
			if (null != streamReader)
				try {
					streamReader.close();
				} catch (XMLStreamException e) {
					LOGGER.error("[process]...when trying to close the reader...", e);
				}
			LOGGER.trace("[process|out]");
		}

	}

	private Statement processRecord(final XMLStreamReader streamReader) throws StatementFormatException {
		LOGGER.trace("[processRecord|in]");
		final Statement statement = new Statement();
		try {
			LOGGER.debug("[processRecord] processing ");
			statement.setReference(Integer.parseInt(streamReader.getAttributeValue(0)));

			BiConsumer<Statement, String> consumer = null;

			while (streamReader.hasNext()) {
				streamReader.next();
				if (streamReader.getEventType() == XMLStreamReader.END_ELEMENT
						&& streamReader.getLocalName().equals("record"))
					break;
				
				if( XMLStreamReader.START_ELEMENT == streamReader.getEventType() ) {
				  consumer = functionMap.get(streamReader.getLocalName());
				}
				else if( XMLStreamReader.CHARACTERS == streamReader.getEventType() ) {
				  String content = streamReader.getText().trim();
          if (!content.isEmpty())
            consumer.accept(statement, content);
				}

			}
			return statement;

		} catch (Exception e) {
			throw new StatementFormatException(e);
		} finally {
			LOGGER.trace("[processRecord|out] statement: {}", statement);
		}
	}

}
