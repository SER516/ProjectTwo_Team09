package app.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JFrame;

import app.client.controller.ClientController;
import app.client.gui.CoordinatesModel;

/**
 * @author Manish Tandon This class is responsible for providing data to various
 *         UI elements of the client application. Eg. Graphs etc. Follows
 *         observer pattern.
 */
public class ClientCommonData extends Observable {
	// data structure for various channels, Format eg.:
	// [[channel_num_1,channel_num_2,...],[channel2_num1,chnnale2_num2...],...]

	private List<ArrayList<CoordinatesModel>> dataFromServer;
	

	private List<String> logs;
	private int frequency;
	private int channels;
	private JFrame clientFrame;
	private boolean started;
	private int portNo;
	
	private ClientController clientControllerObj;
	static Thread clientThread = null;

	private static ClientCommonData instance;

	private ClientCommonData() {
		// so only one instance of this object exists.
		logs = new ArrayList<String>();
		dataFromServer = new ArrayList<ArrayList<CoordinatesModel>>();
	}

	public static ClientCommonData getInstance() {
		if (instance == null) {
			instance = new ClientCommonData();
		}
		return instance;
	}
	
	public void setDataFromServer(List<ArrayList<CoordinatesModel>> dataFromServer) {
		this.dataFromServer = dataFromServer;
	}
	public List<ArrayList<CoordinatesModel>> getDataFromServer() {
		return dataFromServer;
	}


	public List<String> getLogs() {
		return logs;
	}

	public int getFrequency() {
		return frequency;
	}

	public int getChannels() {
		return channels;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public void setChannels(int channels) {
		this.channels = channels;
	}

	public JFrame getClientFrame() {
		return clientFrame;
	}

	public void setClientFrame(JFrame clientFrame) {
		this.clientFrame = clientFrame;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}
	
	public void setPortNo(int portNo) {
		this.portNo = portNo;
		this.clientControllerObj = new ClientController(portNo);
	}
	
	public void toggleClientStatus() {
		
		if(this.isStarted()) {
			this.setStarted(false);
			clientThread.interrupt();
			this.clientControllerObj.stopClient();
			
		} else {
			ClientCommonData.getInstance().setStarted(true);
			//clientThread = new Thread(this.clientControllerObj);
			//clientThread.start();
			this.clientControllerObj.startClient(channels);
		}
	}
	
}