package computerServer;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;

//contain all functions to simulate mouse
public class mouseControl {
	
	private Robot rob;						//object rob from class Robot to control mouse and keyboard
	private float xSpeedScaler = 20;		//scale x-direction the mouse speed, speed scaler get up -> mouse speed get down
	private float ySpeedScaler = 20;		//scale y-direction the mouse speed, speed scaler get up -> mouse speed get down
	
	//create object rob from class Robot
	public void createRobot(){
		try {
			rob = new Robot();
		}catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	//update the current mouse position with x-y-coordinates
	public void refreshXYPosition(int xPosition, int yPosition) {
		createRobot();
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		int x = (int) b.getX();	//get current mouse x-Position
		int y = (int) b.getY();	//get current mouse y-Position
		rob.mouseMove((int)(x+xPosition/xSpeedScaler), (int)(y+yPosition/ySpeedScaler));
	}
	
	//handle left click
	public void leftClick() {
		createRobot();
		rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		rob.delay(10);
		rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	//handle right click
	public void rightClick() {
		createRobot();
		rob.mousePress(InputEvent.BUTTON2_DOWN_MASK);
		rob.delay(10);
		rob.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
	}
	
	//handle long press tap
	public void longPress() {
		createRobot();
		rob.mousePress(InputEvent.BUTTON2_DOWN_MASK);
		rob.delay(10);
		rob.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
	}
	
	//handle mouse wheel
	public void mouseWheel(int yWheel) {
		rob.mouseWheel(yWheel);
	}
}
