package org.usfirst.frc.team4537.robot.utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Logger {

	private String logFileName;

	private SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss:SSSS"); //Log entry time
	private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'_'HH-mm-ss"); //File name

	private ArrayList<String> sensors = new ArrayList<String>();
	private ArrayList<String> data = new ArrayList<String>();
	private long logWriteDelay = 5000;
	private long logDelay;
	private long logTimer = 0;
	private String writeData = "";
	private boolean doWrite = false;
	private String logDirectory = "/home/lvuser/logs/";

	private FileWriter file;
	private BufferedWriter buffer;
	private PrintWriter writer;
	
	private String logBuffer = "";
	private int logBufferLen = 0;
	private long lastTime = 0;

	/**
	 * @param sensorsin Array of sensors to log
	 * @param loggerDelay 
	 */
	public Logger(String[] sensorsin, int loggerDelay, boolean enable) {
		logDelay = loggerDelay;
		//Create Sensors
		for(int i=0;i < sensorsin.length; i++) {
			addSensor(sensorsin[i]);
		}

		//Create log file
		logFileName = logDirectory+"log-"+sdf2.format(System.currentTimeMillis())+".csv";
		
		//Create header
		String header = "Time,Delay";
		for(int i=0; sensors.size()>i; i++) {
			header += ","+sensors.get(i);
		}

		//Write to file
		try {
			file = new FileWriter(logFileName, true);
			buffer = new BufferedWriter(file);
			writer = new PrintWriter(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(logFileName);//XXX
		System.out.println(header);//XXX
		appendFile(logFileName, header);
		
		if(enable) {
			writerThread.start();
		}
	}

	/**
	 * Thread for writing log to file
	 */
	private Thread writerThread = new Thread(() -> {
		while(!Thread.interrupted()) {
			if(doWrite) {
				writer.println(writeData);
				System.out.println("Written");//XXX
				doWrite = false;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	});

	/**
	 * Opens file from path
	 * @param path of file
	 * @return <b>FileWriter</b> object from path
	 */
	private void appendFile(String path, String data) {		
		logBufferLen++;
		if(logBufferLen%(logWriteDelay/logDelay) == 0) {
			writeData = logBuffer+data;
			doWrite = true;
			logBuffer = "";
		} else {
			logBuffer += data+"\n";
		}
	}

	/**
	 * Adds a sensor to logger
	 * @param sensor to add
	 * @return <b>true</b> if added or already exists, <b>false</b> if failed
	 */
	private boolean addSensor(String sensor) {
		if(!sensors.contains(sensor)) {
			sensors.add(sensor);
			data.add("");
			if(sensors.contains(sensor) && data.contains("")) return true;
			else return false;
		}
		else return true;
	}

	/**
	 * Updates sensor data
	 * @param sensor to update
	 * @param value to update sensor with
	 * @return <b>true</b> if sensor exists or was created, <b>false</b> if failed 
	 */
	public boolean updateSensor(String sensor, double value) {
		return updateData(sensor, Double.toString(value));
	}

	/**
	 * Updates sensor data
	 * @param sensor to update
	 * @param value to update sensor with
	 * @return <b>true</b> if sensor exists or was created, <b>false</b> if failed 
	 */
	public boolean updateSensor(String sensor, String value) {
		return updateData(sensor, value);
	}

	/**
	 * Updates sensor data
	 * @param sensor to update
	 * @param value to update sensor with
	 * @return <b>true</b> if sensor exists or was created, <b>false</b> if failed
	 */
	public boolean updateSensor(String sensor, boolean value) {
		return updateData(sensor, Boolean.toString(value));
	}

	/**
	 * Updates sensor data
	 * @param sensor to update
	 * @param value to update sensor with
	 * @return <b>true</b> if sensor exists or was created, <b>false</b> if failed 
	 */
	private boolean updateData(String sensor, String value) {
		if(sensors.contains(sensor)) {
			data.set(sensors.indexOf(sensor), value);
			return true;
		}
		else {
			addSensor(sensor);
			return updateData(sensor, value);
		}
	}

	/**
	 * 
	 * @return <b>ArrayList</b> of sensors
	 */
	public ArrayList<String> getSensors() {
		return sensors;
	}

	/**
	 * 
	 * @return <b>ArrayList</b> of all sensor data
	 */
	public ArrayList<String> getData() {
		return data;
	}

	/**
	 * Gets sensor data from storage
	 * @param sensor to get data from
	 * @return <b>String</b> data from sensor
	 */
	public String getData(String sensor) {
		if(sensors.contains(sensor)) {
			return data.get(sensors.indexOf(sensor));
		}
		else return null;
	}

	/**
	 * Logs to file
	 */
	public void log() {
		if(System.currentTimeMillis() > logTimer+logDelay) {
			long currTime = System.currentTimeMillis();
			String log = sdf1.format(currTime); //Current Time
			log += "," + Long.toString(currTime - lastTime); //Delay
			lastTime = currTime;

			for(int i=0; sensors.size()>i; i++) {
				log += ","+data.get(i);
			}
			if(log.length() > 0) {
				appendFile(logFileName, log);
			}
			logTimer = System.currentTimeMillis();
		}
	}
}
