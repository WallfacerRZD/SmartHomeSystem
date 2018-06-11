package smarthome.command;

import smarthome.Command;
import smarthome.appliance.Camera;

public class CameraOn implements Command {
	private Camera camera;

	public CameraOn(Camera camera) {
		this.camera = camera;
	}

	@Override
	public void execute() {
		camera.on();
	}

}
