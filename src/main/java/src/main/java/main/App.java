package src.main.java.main;
import static spark.Spark.port;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import routing.BoxRouting;
import routing.TimeSegmentRouting;
import routing.UserRouting;

public class App {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		port(80);
		UserRouting.initialize();
		TimeSegmentRouting.initialize();
		BoxRouting.initialize();
	}

}
