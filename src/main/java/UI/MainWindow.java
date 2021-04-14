package UI;

import static spark.Spark.get;
import static spark.Spark.port;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import routing.BoxRouting;
import routing.TimeSegmentRouting;
import routing.UserRouting;

public class MainWindow {

	private JFrame frame;

	private static final Logger logger = LoggerFactory.getLogger(MainWindow.class);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		port(80);
		UserRouting.initialize();
		TimeSegmentRouting.initialize();
		BoxRouting.initialize();
		get("/", (req, res) -> {
			return "Welcome to my API.";
		});
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 705, 460);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblWelcome = new JLabel("Welcome John Smith");
		lblWelcome.setBounds(10, 11, 170, 14);
		frame.getContentPane().add(lblWelcome);

		JComboBox cmbBox = new JComboBox();
		cmbBox.setBounds(10, 36, 135, 22);
		frame.getContentPane().add(cmbBox);

		JList lstTimeSegments = new JList();
		lstTimeSegments.setBounds(10, 69, 354, 341);
		frame.getContentPane().add(lstTimeSegments);

		JButton btnFetchTimeSegments = new JButton("Fetch Time Segments");
		btnFetchTimeSegments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String uri = "http://localhost/timeSegment";
				List<resources.TimeSegment> timeSegments = getTimeSegments(uri);
				List<String> timeSegmentStrings = new ArrayList<String>();
				for (resources.TimeSegment timeSegment : timeSegments) {
					timeSegmentStrings.add(timeSegment.toPrettyString());
				}
				updateList(lstTimeSegments, timeSegmentStrings);

			}
		});
		btnFetchTimeSegments.setBounds(229, 36, 135, 23);
		frame.getContentPane().add(btnFetchTimeSegments);
	}

	private static List<resources.TimeSegment> getTimeSegments(String url) {
		HttpClient client = HttpClient.newHttpClient();

		HttpRequest request = HttpRequest.newBuilder(URI.create(url)).header("accept", "application/json").build();

		List<resources.TimeSegment> outputList = new ArrayList<resources.TimeSegment>();

		HttpResponse<?> response = null;
		try {
			response = client.send(request, BodyHandlers.ofString());
			Gson gson = new Gson();

			List<resources.TimeSegment> inputList = new ArrayList<resources.TimeSegment>();

			Type listOfMyClassObject = new TypeToken<ArrayList<resources.TimeSegment>>() {
			}.getType();

			outputList = gson.fromJson((String) response.body(), listOfMyClassObject);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		return outputList;
	}

	private static void updateList(JList<String> jlist, List<String> arrList) {
		DefaultListModel<String> model = new DefaultListModel<String>();
		model.addAll(arrList);
		jlist.setModel(model);
	}

}
