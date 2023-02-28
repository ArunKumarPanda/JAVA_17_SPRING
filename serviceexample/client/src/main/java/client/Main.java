package client;

import java.util.ServiceLoader;

import com.adobe.api.LogService;

public class Main {

	public static void main(String[] args) {
		ServiceLoader<LogService> service = ServiceLoader.load(LogService.class);
		service.findFirst().get().log("Hello World!!");
	}

}

