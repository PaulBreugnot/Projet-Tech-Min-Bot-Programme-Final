package realRobotInterface.JavaArduinoDialog.Hardware;

import java.io.*;
import java.util.*;
import gnu.io.*;

public class SimpleRead implements Runnable, SerialPortEventListener {
    static CommPortIdentifier portId;
    static Enumeration portList;
    static String receiverPortName = "COM9";
    private String receivedData = "noData";
    private SerialPort serialPort;
    static InputStream inputStream;
    
    Thread readThread;

    public SimpleRead() {
    	openPort();
    	readThread = new Thread(this);
    	readThread.start();
    }
    	

    public void run() {
        /*try {
        	//The communication ends after 20s
            Thread.sleep(30000);
            run();
        } catch (InterruptedException e) {System.out.println(e);}*/
    }

    public void serialEvent(SerialPortEvent event) {
        switch(event.getEventType()) {
        case SerialPortEvent.BI:
        case SerialPortEvent.OE:
        case SerialPortEvent.FE:
        case SerialPortEvent.PE:
        case SerialPortEvent.CD:
        case SerialPortEvent.CTS:
        case SerialPortEvent.DSR:
        case SerialPortEvent.RI:
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
            break;
        case SerialPortEvent.DATA_AVAILABLE:
            byte[] readBuffer = new byte[25];

            try {
                while (inputStream.available() > 0) {
                    int numBytes = inputStream.read(readBuffer);
                }
                setReceivedData(new String(readBuffer));
                //System.out.println(receivedData);
            } catch (IOException e) {System.out.println(e);}
            break;
        }
    }
    
    public void closePort() {
    	System.out.println("Closing current serial port...(reading)");
		if (serialPort != null) {
			serialPort.notifyOnDataAvailable(false);
			serialPort.removeEventListener();
			if (inputStream != null) {
				try {
					inputStream.close();
					inputStream = null;
				}
				catch (IOException e) {}
			}
			serialPort.close();
			serialPort = null;
			System.out.println("Port closed.");
		}
	}
    
    public void openPort() {
    	portList = CommPortIdentifier.getPortIdentifiers();
    	while (portList.hasMoreElements()) {
    		portId = (CommPortIdentifier) portList.nextElement();
    		//System.out.println(portId.getName());
    		if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
    			if (portId.getName().equals(receiverPortName)) {
    				try {
    					System.out.println("Opening port for reading : " + portId.getName());
    					serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
    					System.out.println("Port successfully opened");
    					} catch (PortInUseException e) {
    						System.out.println("Failed port opening...");
    						System.out.println(e);
    					}
    				try {
    					inputStream = serialPort.getInputStream();
    				} catch (IOException e) {System.out.println(e);}
    				try {
    					serialPort.addEventListener(this);
    				} catch (TooManyListenersException e) {System.out.println(e);}
    				serialPort.notifyOnDataAvailable(true);
    				try {
    					serialPort.setSerialPortParams(115200,
    							SerialPort.DATABITS_8,
    							SerialPort.STOPBITS_1,
    							SerialPort.PARITY_NONE);
    				} catch (UnsupportedCommOperationException e) {System.out.println(e);}
    			}
    		}
    	}
    }
    
    public void setReceivedData(String receivedData) {
    	this.receivedData = receivedData;
    }
    
    public String getReceivedData() {
    	return receivedData;
    }
}