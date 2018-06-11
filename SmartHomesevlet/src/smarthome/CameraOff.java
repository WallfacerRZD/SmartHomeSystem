package smarthome;

public class CameraOff implements Command {
	private Camera cameraOff;

    CameraOff(Camera camera) {
		this.cameraOff=camera;
	}
	@Override
	public void execute() {
		cameraOff.off();

	}

}
