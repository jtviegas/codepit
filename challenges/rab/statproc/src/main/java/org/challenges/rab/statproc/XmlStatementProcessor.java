package org.challenges.rab.statproc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.challenges.rab.statproc.exceptions.StatementFormatException;
import org.challenges.rab.statproc.exceptions.StatementProcessorException;
import org.challenges.rab.statproc.statement.Statement;
import org.challenges.rab.statproc.validator.StatementValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class XmlStatementProcessor implements StatementProcessor {

	private static final Logger logger = LoggerFactory.getLogger(CsvStatementProcessor.class);
	private final StatementValidator validator;

	public XmlStatementProcessor(StatementValidator validator) {
		this.validator = validator;
	}

	@Override
	public String[] process(Path file) throws StatementProcessorException {

		if (null == file)
			throw new IllegalArgumentException("must provide file parameter");

		if (!(file.toFile().exists() && file.toFile().isFile() && file.toFile().canRead()))
			throw new StatementProcessorException(String.format("can't read file: %s", file.toString()));

		List<String> notvalid = null;
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLEventReader eventReader = null;
		try {
			eventReader = factory.createXMLEventReader(Files.newBufferedReader(file));
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				switch (event.getEventType()) {
				case XMLStreamConstants.START_ELEMENT:
					StartElement startElement = event.asStartElement();
					String qName = startElement.getName().getLocalPart();
					if (qName.equals("records")) {
						if (null != notvalid)
							throw new StatementFormatException("two 'records' element! xml file is not valid!");

						notvalid = new ArrayList<String>();
						continue;
					}
					if (qName.equals("record")) {
						Statement statement = processRecord(startElement, eventReader);
						if (!validator.validate(statement)) {
							notvalid.add(String.format("%d,%s", statement.getReference(), statement.getDescription()));
						}

					}
					break;

				case XMLStreamConstants.END_ELEMENT:
					EndElement endElement = event.asEndElement();
					if (!endElement.getName().getLocalPart().equals("records"))
						throw new StatementProcessorException("end element different than 'records'!");
					break;
				}
			}

			String[] r = notvalid.toArray(new String[] {});
			return r;

		} catch (Exception e) {
			throw new StatementProcessorException(e);
		} finally {
			if (null != eventReader)
				try {
					eventReader.close();
				} catch (XMLStreamException e) {
					logger.error("[process]...when trying to close the reader...", e);
				}
		}

	}

	private Statement processRecord(StartElement element, XMLEventReader eventReader)
			throws StatementFormatException, XMLStreamException {
		final Statement r = new Statement();
		Attribute refAttribute = null;
		if (null != (refAttribute = element.getAttributeByName(QName.valueOf("reference")))) {
			r.setReference(Integer.parseInt(refAttribute.getValue()));
		} else
			throw new StatementFormatException("'record' element without 'reference' attribute!");

		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();

			if (event.getEventType() == XMLStreamConstants.END_ELEMENT
					&& event.asEndElement().getName().getLocalPart().equals("record"))
				break;

			Consumer<String> consumer = null;
			switch (event.getEventType()) {
			case XMLStreamConstants.START_ELEMENT:
				StartElement startElement = event.asStartElement();
				switch (startElement.getName().getLocalPart()) {
				case "accountNumber":
					consumer = (o) -> r.setAccountNumber(o);
					break;
				case "description":
					consumer = (o) -> r.setDescription(o);
					break;
				case "startBalance":
					consumer = (o) -> r.setStartBalance(Double.parseDouble(o));
					break;
				case "mutation":
					consumer = (o) -> r.setMutation(Double.parseDouble(o));
					break;
				case "endBalance":
					consumer = (o) -> r.setEndBalance(Double.parseDouble(o));
					break;
				}
			case XMLStreamConstants.CHARACTERS:
				String content = event.asCharacters().getData().trim();
				if (!content.isEmpty())
					consumer.accept(content);

			}

		}

		return r;
	}

}
