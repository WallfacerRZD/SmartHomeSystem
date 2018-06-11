package smarthome.command;

import smarthome.Command;
import smarthome.appliance.Camera;

public class CameraOff implements Command {
	private Camera camera;

	public CameraOff(Camera camera) {
		this.camera = camera;
	}

	@Override
	public void execute() {
		camera.off();
	}

}
