package com.bluetooth.server;

public class RemoteBluetoothServer{
	
	public static void main(String[] args) {
		Thread waitThread = new Thread(new WaitThread());
		waitThread.start();
	}
}
