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

import resources.TimeSegment;
import routing.BoxRouting;
import routing.TimeSegmentRouting;
import routing.UserRouting;
import javax.swing.JSpinner;

public class MainWindow {

	private JFrame frame;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(MainWindow.class);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		initializeRouting(80);
		initializeUI();
		
	}

	private static void initializeUI() {
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

	private static void initializeRouting(int port) {
		port(port);
		UserRouting.initialize();
		TimeSegmentRouting.initialize();
		BoxRouting.initialize();
		get("/", (req, res) -> {
			return "Welcome to my API.";
		});		
	}

	public MainWindow() {
		initialize();
	}


	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 705, 460);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblWelcome = new JLabel("Welcome John Smith");
		lblWelcome.setBounds(10, 11, 170, 14);
		frame.getContentPane().add(lblWelcome);

		JComboBox<String> cmbBox = new JComboBox<String>();
		cmbBox.setBounds(374, 36, 149, 22);
		frame.getContentPane().add(cmbBox);

		JList<String> lstTimeSegments = new JList<String>();
		lstTimeSegments.setBounds(10, 36, 354, 374);
		frame.getContentPane().add(lstTimeSegments);

		JButton btnFetchTimeSegments = new JButton("Fetch Time Segments");
		btnFetchTimeSegments.setBounds(374, 69, 149, 23);
		btnFetchTimeSegments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String uri = "http://localhost/timeSegment";
				List<TimeSegment> timeSegments = getTimeSegments(uri);
				List<String> timeSegmentStrings = new ArrayList<String>();
				for (TimeSegment timeSegment : timeSegments) {
					timeSegmentStrings.add(timeSegment.toPrettyString());
				}
				updateList(lstTimeSegments, timeSegmentStrings);

			}
		});
		frame.getContentPane().add(btnFetchTimeSegments);
		
		JSpinner spnStartHour = new JSpinner();
		spnStartHour.setBounds(374, 173, 43, 20);
		frame.getContentPane().add(spnStartHour);
		
		JSpinner spnStartMinute = new JSpinner();
		spnStartMinute.setBounds(427, 173, 43, 20);
		frame.getContentPane().add(spnStartMinute);
		
		JComboBox spnStartAmPm = new JComboBox();
		spnStartAmPm.setBounds(480, 172, 43, 22);
		frame.getContentPane().add(spnStartAmPm);
		
		JLabel lblNewLabel = new JLabel("Start Time: ");
		lblNewLabel.setBounds(374, 148, 83, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JComboBox spnEndAmPm = new JComboBox();
		spnEndAmPm.setBounds(480, 228, 43, 22);
		frame.getContentPane().add(spnEndAmPm);
		
		JSpinner spnEndMinute = new JSpinner();
		spnEndMinute.setBounds(427, 229, 43, 20);
		frame.getContentPane().add(spnEndMinute);
		
		JSpinner spnEndHour = new JSpinner();
		spnEndHour.setBounds(374, 229, 43, 20);
		frame.getContentPane().add(spnEndHour);
		
		JLabel lblEndTime = new JLabel("End Time: ");
		lblEndTime.setBounds(374, 204, 83, 14);
		frame.getContentPane().add(lblEndTime);
		
		JButton btnAddTimeSeg = new JButton("Add Time Segment");
		btnAddTimeSeg.setBounds(374, 261, 149, 23);
		frame.getContentPane().add(btnAddTimeSeg);
		
		JButton btnRemoveSelected = new JButton("Remove Selected");
		btnRemoveSelected.setBounds(374, 99, 149, 23);
		frame.getContentPane().add(btnRemoveSelected);
	}
	
	private static HttpResponse<String> makeGetRequest(String uri) {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = 
				HttpRequest.newBuilder(URI.create(uri))
				.header("accept", "application/json")
				.build();
		HttpResponse<String> response = null;
		try {
			response = client.send(request, BodyHandlers.ofString());
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		return response;
	}
	
	private static List<TimeSegment> getTimeSegments(String uri) {
		List<TimeSegment> timeSegments = new ArrayList<TimeSegment>();
		String body = makeGetRequest(uri).body();
		Type listOfMyClassObject = new TypeToken<ArrayList<TimeSegment>>() {}.getType();
		timeSegments = new Gson().fromJson(body, listOfMyClassObject);		
		return timeSegments;
	}

	private static void updateList(JList<String> jlist, List<String> arrList) {
		DefaultListModel<String> model = new DefaultListModel<String>();
		model.addAll(arrList);
		jlist.setModel(model);
	}
}
