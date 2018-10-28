package com.ericsson.bluetooth.server;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;



public class SimpleReadClass1 {



    static SerialPort serialPort;



    public static void main(String[] args) {
        String[] portNames = SerialPortList.getPortNames();

        for (String port : portNames) {
            System.out.println("aaaa "+port);
        }
        serialPort = new SerialPort("COM5");

        try {

            serialPort.openPort();

            serialPort.setParams(9600, 8, 1, 0);

            //Preparing a mask. In a mask, we need to specify the types of events that we want to track.

            //Well, for example, we need to know what came some data, thus in the mask must have the

            //following value: MASK_RXCHAR. If we, for example, still need to know about changes in states

            //of lines CTS and DSR, the mask has to look like this: SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR

            int mask = SerialPort.MASK_RXCHAR;

            //Set the prepared mask

            serialPort.setEventsMask(mask);

            //Add an interface through which we will receive information about events

            serialPort.addEventListener(new SerialPortReader());

        }

        catch (SerialPortException ex) {

            ex.printStackTrace();

        }
        /*finally{
            try {
                serialPort.closePort();
            } catch (SerialPortException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }*/

    }



    static class SerialPortReader implements SerialPortEventListener {



        public void serialEvent(SerialPortEvent event) {

            //Object type SerialPortEvent carries information about which event occurred and a value.

            //For example, if the data came a method event.getEventValue() returns us the number of bytes in the input buffer.
        	System.out.println("Hi"+event);

            if(event.isRXCHAR()){

                if(event.getEventValue() == 10){

                    try {

                        byte buffer[] = serialPort.readBytes(10);

                    }

                    catch (SerialPortException ex) {

                        System.out.println(ex);

                    }

                }

            }

            //If the CTS line status has changed, then the method event.getEventValue() returns 1 if the line is ON and 0 if it is OFF.

            else if(event.isCTS()){

                if(event.getEventValue() == 1){

                    System.out.println("CTS - ON");

                }

                else {

                    System.out.println("CTS - OFF");

                }

            }

            else if(event.isDSR()){

                if(event.getEventValue() == 1){

                    System.out.println("DSR - ON");

                }

                else {

                    System.out.println("DSR - OFF");

                }

            }

        }

    }

}
