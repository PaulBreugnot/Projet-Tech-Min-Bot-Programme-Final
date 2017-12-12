package realRobotInterface.JavaArduinoDialog.Hardware;
import java.time.LocalDateTime;

public class Main {

	public static void main(String[] args) {
		//Initiate a unique reading thread that will be executed as a background process
		SimpleRead readingThread = new SimpleRead();
		
		//SimpleWrite is instantiating at each sendQuerry/sendData
		
		//Test Querry + Response 
		/*SimpleWrite askForSomething = new SimpleWrite(readingThread, "Comment ça va?\n", SimpleWrite.sendingMode.SENDING_WITH_RESPONSE);
		System.out.println(askForSomething.getResponse());*/
		
		//Test Simple Sending
		//SimpleWrite sendSomething = new SimpleWrite(readingThread, "CPTR", SimpleWrite.sendingMode.SIMPLE_SENDING);
		
		//Read incoming data for 15s
		/*int beginRead = LocalDateTime.now().getSecond();
		while (LocalDateTime.now().getSecond() - beginRead < 15) {
			//System.out.println(LocalDateTime.now().getSecond() - beginRead);
		}*/
		
		//Asking for captors data
		/*SimpleWrite askForSomething = new SimpleWrite(readingThread, "CPTR", SimpleWrite.sendingMode.SENDING_WITH_RESPONSE);
		System.out.println(askForSomething.getResponse());*/
		
		//Piloting motors

		SimpleWrite sendSpeed = new SimpleWrite(readingThread, "STOP", SimpleWrite.sendingMode.SIMPLE_SENDING);
		
		//Don't forget to always close the reading port
		readingThread.closePort();
	}
}
