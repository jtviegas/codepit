package org.aprestos.labs.bluemix.dataretriever;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import org.apache.wink.json4j.JSON;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.JSONObject;

import com.ibm.twa.applab.client.WorkloadService;
import com.ibm.twa.applab.client.helpers.WAProcess;
import com.ibm.twa.applab.client.helpers.steps.CommandStep;
import com.ibm.tws.simpleui.bus.Task;
import com.ibm.tws.simpleui.bus.TaskHistoryInstance;
import com.ibm.tws.simpleui.bus.TaskInstanceStatus;

/**
 * Hello world!
 *
 */
public class App 
{
	// Once connected and authenticated correctly, this boolean is true
		boolean connected = false;
		// Hold the instance of the WorkloadService
		WorkloadService ws;
		// Holds the last created process id.
		long myProcessId;
		// If true adds <br> instead of \n for each output row
		boolean htmlOut = true;

		//TODO: Change the workload service name
		String workloadServiceName = "WorkloadScheduler";
		//TODO: Agent name: to use workload automation service you need to have an agent installed
		//      Once installed you can schedule on it referencing his name here 
		String agentName = "_CLOUD";
		
		//Enable debug mode if needed
		boolean debugMode = false;
		
		// Default empty constructor.
		public App(){};
			
		
		/**
		 *  Connects and authenticates to the server, exploring the content of 
		 *  VCAP_SERVICES content.
		 *  
		 *  @param o: Output Stream to write useful info
		 */
		public void helloWorkloadConnect(Writer o) throws JSONException{
			PrintWriter out = new PrintWriter(o);
			 String vcapJSONString = System.getenv("VCAP_SERVICES");
	         Object jsonObject = JSON.parse(vcapJSONString);
	         JSONObject json = (JSONObject)jsonObject;
	         String key;
	         JSONArray twaServiceArray =null;
	         
	         println(out,"Looking for Workload Automation Service...");
	         out.flush();

	         for (Object k: json.keySet())
	         {
	             key = (String )k;            
	             if (key.startsWith(workloadServiceName))
	             {
	                 twaServiceArray = (JSONArray)json.get(key);
	                 println(out,"Workload Automation service found!");
	                 println(out,vcapJSONString);
	                 out.flush();
	                 break;
	             }                       
	         }
	          if (twaServiceArray== null){
	        	  println(out,"Could not connect: I was not able to find the Workload Automation service!");
	        	  println(out,"This is your VCAP services content");
	        	  println(out,vcapJSONString);
	        	  out.flush();
	        	  return;
	          }
	         
	          JSONObject twaService = (JSONObject)twaServiceArray.get(0); 
	          JSONObject credentials = (JSONObject)twaService.get("credentials");
	         
	                  
	          
	          println(out,"Starting Workload Automation connection..");
	          out.flush();
	          
	          
	          String url = (String) credentials.get("url");
	          int index  = url.indexOf("tenantId=") +9 ;                    
	          String prefix = url.substring(index, index+2);
	          println(out,"prefix="+ prefix);
	          agentName = prefix+agentName;
	          try {
	        	  WorkloadService.disableCertificateValidation();
	              ws = new WorkloadService(url);
	              ws.setDebugMode(debugMode);
	          }catch(Exception e){
	        	  println(out,"Could not connect to the service: " + e.getClass().getName() + " " + e.getMessage());
	        	  out.flush();
	        	  return;
	          }
	          connected = true;
	          println(out,"Connection obtained.");
	          out.flush();
		}
		
		/**
		 * Creates a very simple hello world process
		 * 
		 * @param o
		 */
		public void helloWorkloadCreate(Writer o){
				PrintWriter out = new PrintWriter(o);
				WAProcess p = new WAProcess("My process name","Hello world process!"); // Process name and description
			    p.addStep(new CommandStep("echo Hello World!", agentName));  
			    
			    try {
			    	println(out,"Creating and enabling the process");
			    	out.flush();
			    	Task t =  ws.createAndEnableTask(p);
			    	if (t!=null) {
			    		this.myProcessId = ws.createAndEnableTask(p).getId();   // The process is created and enabled for execution
			    		println(out,"Running the process");
				    	out.flush();
						ws.runTask(this.myProcessId); // The process is submitted to run
			    	}
					
				} catch (Exception e) {
					println(out,"Could not connect complete the operation: " + e.getClass().getName() + " " + e.getMessage());
		        	out.flush();
		        	  
				} 
			    
		}
		
		/**
		 * Tracks a process created with a previous call to helloWorkloadCreate
		 * 
		 * @param o
		 */	
		public void helloWorkloadTrack(Writer o){
			PrintWriter out = new PrintWriter(o);
		    try {
		    	List<TaskHistoryInstance> thiList = ws.getTaskHistory(this.myProcessId);
	            for (TaskHistoryInstance taskHistoryInstance : thiList) {
	                println(out,"Started: " + taskHistoryInstance.getStartDate() + 
	                        "\n Completed steps: " + taskHistoryInstance.getCompletedSteps() + 
	                        "\n Is completed: "  + (taskHistoryInstance.getStatus() == TaskInstanceStatus.COMPLETED));
	                if ((taskHistoryInstance.getStatus() == TaskInstanceStatus.COMPLETED)) {
	                    println(out,"The process has completed all the steps in: " + taskHistoryInstance.getElapsedTime()/60000 + " minutes");                    
	                }
	                out.flush();  
	            }

			} catch (Exception e) {
				out.println("Could not connect complete the operation: " + e.getClass().getName() + " " + e.getMessage());
	        	out.flush();  
			}    
		}
		
		public void println(PrintWriter out,String msg){
			if (this.htmlOut){
				out.print(msg + "<br>");
			}else{
				out.println(msg);
			}
		}
		
		
		public long getMyProcessId() {
			return myProcessId;
		}
		
		public boolean isConnected() {
			return connected;
		}
		
		

}
