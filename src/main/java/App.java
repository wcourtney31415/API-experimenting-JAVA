import static spark.Spark.get;
import static spark.Spark.port;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

public class App {

	static class CalculatedTimeSegment {
		long startTime;
		long endTime;
		long duration;

		public CalculatedTimeSegment(TimeSegment timeSegment) {
			startTime = timeSegment.startTime;
			endTime = timeSegment.endTime;
			duration = deltaInSeconds(epochToDate(startTime), epochToDate(endTime));
		}

	}

	static List<TimeSegment> segments = new ArrayList<TimeSegment>();

	private static Date epochToDate(Long epoch) {
		Date expiry = new Date(epoch * 1000);
		return expiry;
	}

	public static long deltaInSeconds(Date startTime, Date endTime) {
		long diffInMillies = Math.abs(endTime.getTime() - startTime.getTime());
		long diff = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		return diff;
	}

	private static String secondsToTimeString(long seconds) {
		long hours = seconds / 60 / 60;
		long minutes = (seconds / 60) % 60;
		String ret = hours + " hours and " + minutes + " minutes";
		return ret;
	}

	private static String actualUsername = "bob";
	private static String actualPassword = "passwordy";

	static boolean isAuthentic(String username, String password) {
		boolean isValidUsername = username.equals(actualUsername);
		boolean isValidPassword = password.equals(actualPassword);
		boolean auth = isValidUsername && isValidPassword;
		return auth;
	}

	public static void main(String[] args) {
		segments.add(new TimeSegment(720865980, 720869980));
		segments.add(new TimeSegment(725834338, 725836338));
		segments.add(new TimeSegment(730800696, 730800696));
		segments.add(new TimeSegment(735765054, 735771054));
		segments.add(new TimeSegment(740735412, 740739412));
		segments.add(new TimeSegment(745703770, 745705770));
		segments.add(new TimeSegment(750670128, 750670128));
		segments.add(new TimeSegment(755634486, 755640486));
		segments.add(new TimeSegment(760604844, 760608844));
		segments.add(new TimeSegment(765573202, 765575202));
		segments.add(new TimeSegment(770539560, 770539560));
		segments.add(new TimeSegment(775503918, 775509918));
		segments.add(new TimeSegment(780474276, 780478276));
		segments.add(new TimeSegment(785442634, 785444634));
		segments.add(new TimeSegment(790408992, 790408992));
		segments.add(new TimeSegment(795373350, 795379350));
		segments.add(new TimeSegment(800343708, 800347708));
		segments.add(new TimeSegment(805312066, 805314066));
		segments.add(new TimeSegment(810278424, 810278424));
		segments.add(new TimeSegment(815242782, 815248782));
		segments.add(new TimeSegment(820213140, 820217140));
		segments.add(new TimeSegment(825181498, 825183498));
		segments.add(new TimeSegment(830147856, 830147856));
		segments.add(new TimeSegment(835112214, 835118214));
		segments.add(new TimeSegment(840082572, 840086572));
		segments.add(new TimeSegment(845050930, 845052930));
		segments.add(new TimeSegment(850017288, 850017288));
		segments.add(new TimeSegment(854981646, 854987646));
		segments.add(new TimeSegment(859952004, 859956004));
		segments.add(new TimeSegment(864920362, 864922362));
		segments.add(new TimeSegment(869886720, 869886720));
		segments.add(new TimeSegment(874851078, 874857078));
		segments.add(new TimeSegment(879821436, 879825436));
		segments.add(new TimeSegment(884789794, 884791794));
		segments.add(new TimeSegment(889756152, 889756152));
		segments.add(new TimeSegment(894720510, 894726510));
		segments.add(new TimeSegment(899690868, 899694868));
		segments.add(new TimeSegment(904659226, 904661226));
		segments.add(new TimeSegment(909625584, 909625584));
		segments.add(new TimeSegment(914589942, 914595942));
		segments.add(new TimeSegment(919560300, 919564300));
		segments.add(new TimeSegment(924528658, 924530658));
		segments.add(new TimeSegment(929495016, 929495016));
		segments.add(new TimeSegment(934459374, 934465374));
		segments.add(new TimeSegment(939429732, 939433732));
		segments.add(new TimeSegment(944398090, 944400090));
		segments.add(new TimeSegment(949364448, 949364448));
		segments.add(new TimeSegment(954328806, 954334806));
		segments.add(new TimeSegment(959299164, 959303164));
		segments.add(new TimeSegment(964267522, 964269522));
		segments.add(new TimeSegment(969233880, 969233880));
		segments.add(new TimeSegment(974198238, 974204238));
		segments.add(new TimeSegment(979168596, 979172596));
		segments.add(new TimeSegment(984136954, 984138954));
		segments.add(new TimeSegment(989103312, 989103312));
		segments.add(new TimeSegment(994067670, 994073670));
		segments.add(new TimeSegment(999038028, 999042028));
		segments.add(new TimeSegment(1004006386, 1004008386));
		segments.add(new TimeSegment(1008972744, 1008972744));
		segments.add(new TimeSegment(1013937102, 1013943102));
		segments.add(new TimeSegment(1018907460, 1018911460));
		segments.add(new TimeSegment(1023875818, 1023877818));
		segments.add(new TimeSegment(1028842176, 1028842176));
		segments.add(new TimeSegment(1033806534, 1033812534));
		segments.add(new TimeSegment(1038776892, 1038780892));
		segments.add(new TimeSegment(1043745250, 1043747250));
		segments.add(new TimeSegment(1048711608, 1048711608));
		segments.add(new TimeSegment(1053675966, 1053681966));
		segments.add(new TimeSegment(1058646324, 1058650324));
		segments.add(new TimeSegment(1063614682, 1063616682));
		segments.add(new TimeSegment(1068581040, 1068581040));
		segments.add(new TimeSegment(1073545398, 1073551398));
		segments.add(new TimeSegment(1078515756, 1078519756));
		segments.add(new TimeSegment(1083484114, 1083486114));
		segments.add(new TimeSegment(1088450472, 1088450472));
		segments.add(new TimeSegment(1093414830, 1093420830));
		segments.add(new TimeSegment(1098385188, 1098389188));
		segments.add(new TimeSegment(1103353546, 1103355546));
		segments.add(new TimeSegment(1108319904, 1108319904));
		segments.add(new TimeSegment(1113284262, 1113290262));
		segments.add(new TimeSegment(1118254620, 1118258620));
		segments.add(new TimeSegment(1123222978, 1123224978));
		segments.add(new TimeSegment(1128189336, 1128189336));
		segments.add(new TimeSegment(1133153694, 1133159694));
		segments.add(new TimeSegment(1138124052, 1138128052));
		segments.add(new TimeSegment(1143092410, 1143094410));
		segments.add(new TimeSegment(1148058768, 1148058768));
		segments.add(new TimeSegment(1153023126, 1153029126));
		segments.add(new TimeSegment(1157993484, 1157997484));
		segments.add(new TimeSegment(1162961842, 1162963842));
		segments.add(new TimeSegment(1167928200, 1167928200));
		segments.add(new TimeSegment(1172892558, 1172898558));
		segments.add(new TimeSegment(1177862916, 1177866916));
		
		List<CalculatedTimeSegment> calcs = new ArrayList<App.CalculatedTimeSegment>();
		
		for (TimeSegment ts : segments) {
			calcs.add(new CalculatedTimeSegment(ts));
		}
		
		port(80);

		get("/", (req, res) -> "Welcome home.");

		get("/time-segments", (req, res) -> {
			
			
			String username = req.headers("username");
			String password = req.headers("password");
			boolean authentic = isAuthentic(username, password);
			if (authentic) {
				Gson gson = new Gson();
				String json = gson.toJson(calcs);
				return json;
			} else {
				res.redirect("http://www.google.com");
				return res;
			}
		});

		get("/something", (req, res) -> {
			String username = req.headers("username");
			String password = req.headers("password");
			boolean authentic = isAuthentic(username, password);
			if (authentic) {
				String body = req.body();
				Gson gson = new Gson();
				TimeSegment timeSegment = gson.fromJson(body, TimeSegment.class);
				Date startTime = epochToDate(timeSegment.startTime);
				Date endTime = epochToDate(timeSegment.endTime);
				long diff = deltaInSeconds(startTime, endTime);
				String strDiff = secondsToTimeString(diff);
				System.out.println(strDiff);
				return strDiff;
			} else {
				res.redirect("http://www.google.com");
				return res;
			}

		});

	}

}
