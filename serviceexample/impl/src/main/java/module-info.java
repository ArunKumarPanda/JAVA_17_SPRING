import com.adobe.api.LogService;
import com.adobe.impl.LogStdOutImpl;

module impl {
	requires api;
	exports com.adobe.impl;
	provides LogService with LogStdOutImpl; // ServiceLocator pattern
}

