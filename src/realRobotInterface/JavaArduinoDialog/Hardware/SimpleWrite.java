package realRobotInterface.JavaArduinoDialog.Hardware;

import java.io.*;
//import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.*;
import gnu.io.*;
import realRobotInterface.MainRemoteControl;

public class SimpleWrite{
	static final int waitingDelay = (int) 5E8;
    static String transmitterPortName = "COM3";
    static String messageToSend;
    static Enumeration portList;
    static CommPortIdentifier portId;
    static SerialPort serialPort;
    static OutputStream outputStream;
    
    static SimpleRead readingThread;
    
    public static enum sendingMode{SIMPLE_SENDING, SENDING_WITH_RESPONSE};
    
    private String response;

    public static void main(String[] args) {
    	//Need to close the port opened for read before reopening it to write
    	System.out.println("Waiting to close...");
		while(!readingThread.isAvailable()) {};
    	readingThread.closePort();
    	
        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                 if (portId.getName().equals(transmitterPortName)) {
                    try {
                   	 	//System.out.println("Opening port for writing : " + portId.getName());
                        serialPort = (SerialPort)
                            portId.open("SimpleWriteApp", 2000);
                        //System.out.println("Port successfully opened");
                    } catch (PortInUseException e) {System.out.println("Failed port opening...");}
                    try {
                        outputStream = serialPort.getOutputStream();
                    } catch (IOException e) {}
                    try {
                        serialPort.setSerialPortParams(115200,
                            SerialPort.DATABITS_8,
                            SerialPort.STOPBITS_1,
                            SerialPort.PARITY_NONE);
                    } catch (UnsupportedCommOperationException e) {System.out.println(e);}
                    try {
                    	//System.out.println("Trying to send following message :");
                    	//System.out.println(messageToSend);
                        outputStream.write(messageToSend.getBytes());
                        //System.out.println("Message sent");
                    } catch (IOException e) {System.out.println(e);}
                }
            }
        }
        closePort(serialPort);
        
        //Reopening the port in reading mode
        System.out.println("Waiting to re-open...");
		while(!readingThread.isAvailable()){};
    	readingThread.openPort();
    }
    
    public SimpleWrite(SimpleRead readingThread, String dataToSend, sendingMode sendingMode) {
    	setReadingThread(readingThread);
    	switch(sendingMode) {
    	case SIMPLE_SENDING:
    		sendData(dataToSend);
    		break;
    	case SENDING_WITH_RESPONSE :
    		sendQuerry(dataToSend);
    		break;
    	}
    }
    
    private static void closePort(SerialPort serialPort) {
    	//System.out.println("closing port current serial port... (writing)");
		if (serialPort != null) {
			if (outputStream != null) {
				try {
					outputStream.close();
					outputStream = null;
				}
				catch (IOException e) {}
			}
			serialPort.close();
			serialPort = null;
			//System.out.println("Port closed.");
		}
	}
    
    public void sendQuerry(String querry) {
    	messageToSend = querry;
    	main(null);
		//wait for Arduino response
		LocalDateTime limitTime = LocalDateTime.now().plusNanos(waitingDelay);
		System.out.println("-----Waiting for Arduino Response-----");
		while(readingThread.getReceivedData() == "noData" && (LocalDateTime.now().compareTo(limitTime) < 0)) {
			//System.out.println(LocalDateTime.now().getSecond());
			/*if(LocalDateTime.now().getSecond() - beginTime >= 2) {
			System.out.println("Retry...");
			sendQuerry(querry);
			}*/
		}
		setResponse(readingThread.getReceivedData());
		readingThread.setReceivedData("noData");
		System.out.println("-----Response Received!----");
		System.out.println("response : " + response);
    }
    
    public static void setReadingThread(SimpleRead readingThread) {
    	SimpleWrite.readingThread = readingThread;
    }
    
    public static void sendData(String data) {
    	messageToSend = data;
    	main(null);
    }
    
    public void setResponse(String response) {
    	this.response = response;
    }
    
    public String getResponse() {
    	return response;
    }
}