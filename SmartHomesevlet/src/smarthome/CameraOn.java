package smarthome;

public class CameraOn implements Command {
	private Camera cameraOn ;
		
	CameraOn(Camera camera) {
		this.cameraOn= camera;
	}

	@Override
	public void execute() {
		cameraOn.on();
	}

}
