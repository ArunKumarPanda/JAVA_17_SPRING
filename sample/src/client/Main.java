package client;

import com.adobe.service.SampleService;
import com.adobe.util.DateUtil;

public class Main {

	public static void main(String[] args) {
		SampleService service = new SampleService();
		service.doTask();
		
		System.out.println(DateUtil.getDate());
	}

}
