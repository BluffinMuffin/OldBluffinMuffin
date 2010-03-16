import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class Utils {

	public static String msToStringTime(long totalms) {
		long ms = (totalms % 1000);
		long sec = (totalms / 1000) % 60;
		long min = (totalms / 1000 / 60) % 60;
		long h = (totalms / 1000 / 60 / 60) % 60;
		long d = totalms / 1000 / 60 / 60 / 60 / 24;

		String time = "";
		if (d != 0) {
			time += d + "d ";
		}
		if (h != 0) {
			time += h + "h ";
		}
		if (min != 0) {
			time += min + "m ";
		}
		if (sec != 0) {
			time += sec + "s ";
		}
		if (ms != 0) {
			time += ms + "ms";
		}

		return time.trim();
	}

	public static String readFileAsString(String filePath) throws java.io.IOException {
		byte[] buffer = new byte[(int) new File(filePath).length()];
		BufferedInputStream f = new BufferedInputStream(new FileInputStream(filePath));
		f.read(buffer);
		return new String(buffer);
	}
}
