package org.challenges.camunda.bpmn;

import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Deque;
import java.util.Optional;

import static java.lang.String.format;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
public class BpmnApplicationTests {

	@Autowired
	private XmlFetcher fetcher;

	@Test
	public void checkIfFetcherIsFetching() {
		Assert.assertNotNull(fetcher.fetch());
	}

	@Test
	public void checkNodesAreRight() {
		Exception exception = assertThrows(RuntimeException.class, () ->
				new Solver().getPath(fetcher.fetch(),"StartEvent_1","assignApproverX"));
		assertTrue(exception.getMessage().contains("couldn't find end node with"));
	}

	@Test
	public void checkNodesAreRightAgain() {
		Exception exception = assertThrows(RuntimeException.class, () ->
				new Solver().getPath(fetcher.fetch(),"assignApproverX","invoiceProcessed"));
		assertTrue(exception.getMessage().contains("couldn't find start node with"));
	}

	@Test
	public void checkNodesAreRightYetAgain() {
		Exception exception = assertThrows(RuntimeException.class, () ->
				new Solver().getPath(fetcher.fetch(),"assignApproverX","invoiceProcessedX"));
		assertTrue(exception.getMessage().contains("couldn't find start node with"));
	}

	@Test
	public void checkProcessedPathToItself() {
		String start = "approveInvoice", end = "approveInvoice";
		Solver solver = new Solver();
		Optional<Deque<FlowNode>> path = solver.getPath(fetcher.fetch(),start,end);
		assertTrue(path.isPresent());
		assertEquals("The path from approveInvoice to approveInvoice is: [approveInvoice]", Solver.toString(path, start, end) );
	}

	@Test
	public void checkProcessedPathToItselfTheLastOne() {
		String start = "invoiceNotProcessed", end = "invoiceNotProcessed";
		Solver solver = new Solver();
		Optional<Deque<FlowNode>> path = solver.getPath(fetcher.fetch(),start,end);
		assertTrue(path.isPresent());
		assertEquals("The path from invoiceNotProcessed to invoiceNotProcessed is: [invoiceNotProcessed]", Solver.toString(path, start, end) );
	}

	@Test
	public void checkProcessedPathFromChallengeDefinition() {
		String start = "approveInvoice", end = "invoiceProcessed";
		Solver solver = new Solver();
		Optional<Deque<FlowNode>> path = solver.getPath(fetcher.fetch(),start,end);
		assertTrue(path.isPresent());
		assertEquals("The path from approveInvoice to invoiceProcessed is: " +
				"[approveInvoice, invoice_approved, prepareBankTransfer, ServiceTask_1, invoiceProcessed]", Solver.toString(path, start, end) );

	}

	@Test
	public void checkProcessedPathReversed() {
		String start = "invoiceProcessed", end = "approveInvoice";
		Solver solver = new Solver();
		Optional<Deque<FlowNode>> path = solver.getPath(fetcher.fetch(),start,end);
		assertFalse(path.isPresent());
		assertEquals(format("couldn't find path from %s to %s", start, end), Solver.toString(path, start, end) );
	}

	@Test
	public void checkProcessedPathUntilBankTranfer() {
		String start = "StartEvent_1", end = "prepareBankTransfer";
		Solver solver = new Solver();
		Optional<Deque<FlowNode>> path = solver.getPath(fetcher.fetch(),start,end);
		assertTrue(path.isPresent());
		assertEquals("The path from StartEvent_1 to prepareBankTransfer is: " +
				"[StartEvent_1, assignApprover, approveInvoice, invoice_approved, prepareBankTransfer]", Solver.toString(path, start, end) );

	}

	@Test
	public void checkProcessedPathFromAlmostTheEndToInvoiceNotProcessed() {
		String start = "reviewSuccessful_gw", end = "invoiceNotProcessed";
		Solver solver = new Solver();
		Optional<Deque<FlowNode>> path = solver.getPath(fetcher.fetch(),start,end);
		assertTrue(path.isPresent());
		assertEquals("The path from reviewSuccessful_gw to invoiceNotProcessed is: " +
				"[reviewSuccessful_gw, invoiceNotProcessed]", Solver.toString(path, start, end) );

	}

	@Test
	public void checkProcessedPathInvoiceApprovedToReviewInvoice() {
		String start = "invoice_approved", end = "reviewInvoice";
		Solver solver = new Solver();
		Optional<Deque<FlowNode>> path = solver.getPath(fetcher.fetch(),start,end);
		assertTrue(path.isPresent());
		assertEquals("The path from invoice_approved to reviewInvoice is: " +
				"[invoice_approved, reviewInvoice]", Solver.toString(path, start, end) );

	}

	@Test
	public void checkProcessedPathLoopBackToOtherBranch() {
		String start = "reviewInvoice", end = "prepareBankTransfer";
		Solver solver = new Solver();
		Optional<Deque<FlowNode>> path = solver.getPath(fetcher.fetch(),start,end);
		assertTrue(path.isPresent());
		assertEquals("The path from reviewInvoice to prepareBankTransfer is: " +
				"[reviewInvoice, reviewSuccessful_gw, approveInvoice, invoice_approved, prepareBankTransfer]", Solver.toString(path, start, end) );

	}

	@Test
	public void checkProcessedPathLoopBackToOtherBranchEnd() {
		String start = "reviewInvoice", end = "invoiceProcessed";
		Solver solver = new Solver();
		Optional<Deque<FlowNode>> path = solver.getPath(fetcher.fetch(),start,end);
		assertTrue(path.isPresent());
		assertEquals("The path from reviewInvoice to invoiceProcessed is: " +
				"[reviewInvoice, reviewSuccessful_gw, approveInvoice, invoice_approved, prepareBankTransfer, ServiceTask_1, invoiceProcessed]", Solver.toString(path, start, end) );

	}

}
