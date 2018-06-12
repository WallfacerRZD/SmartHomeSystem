package smarthome.servlet;


import net.sf.json.JSONObject;
import smarthome.appliance.Camera;
import smarthome.appliance.Light;
import smarthome.command.CameraOff;
import smarthome.command.CameraOn;
import smarthome.command.LightOff;
import smarthome.command.LightOn;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Connect extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		String appliance = request.getParameter("appliance");
		String command = request.getParameter("command");
		String state = "";

		switch (appliance) {

		case "light":
			Light testlight = new Light();
			if (command.equals("on")) {
				LightOn lightOn = new LightOn(testlight);
				lightOn.execute();
				state = "success";
			} else if (command.equals("off")) {
				LightOff lightOff = new LightOff(testlight);
				lightOff.execute();
				state = "success";
			}
			break;

		case "camera":
			Camera testcamera = new Camera();
			if (command.equals("on")) {
				CameraOn cameraOn = new CameraOn(testcamera);
				cameraOn.execute();
				state = "success";
			} else if (command.equals("off")) {
				CameraOff cameraOff = new CameraOff(testcamera);
				cameraOff.execute();
				state = "success";
			}
			break;

		default:
			state = "failed";
			break;
		}

		// ·µ»Ø
		JSONObject json = new JSONObject();
		json.put("state", state);
        response.getWriter().print(json);
	}
}