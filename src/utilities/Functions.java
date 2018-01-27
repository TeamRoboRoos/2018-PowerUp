package utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Functions {

	/**
	 * Truncates  the decimal from a number
	 * @param number Number to truncate
	 * @return Truncated value
	 */
	public static double floor(double number) {
		return Math.floor(number);
	}

	/**
	 * Truncates to places decimal places
	 * @param number Number to truncate
	 * @param places Number of places to truncate to
	 * @return Truncated value
	 */
	public static double floor(double number, double places) {
		places = Math.pow(10, places);
		return Math.floor(number * places)/places;
	}

	/**
	 * Applies calibration to pressure reading
	 * @param analogReading Reading from pressure sensor
	 * @param gradient Gradient of trend line
	 * @param intercept Intercept of trend line
	 * @return Calibrated pressure reading
	 */
	public static double pressure(double analogReading, double gradient, double intercept) {
		return (analogReading-intercept)/gradient;
	}

	/**
	 * Applied calibration to encoder reading
	 * @param encoderReading Reading from encoder
	 * @return Calibrated encoder reading
	 */
	public static double encoder(double encoderReading) {
		return (encoderReading / 12288); //was 12268 prior to reading the manual...
	}

	/**
	 * Calculate the gradient and intercept of the trend line
	 * @param coords [[x,y],[x,y],...]
	 * @return [Gradient, Intercept]
	 */
	public static double[] statreg(double[][] coords) {
		double a = 0;
		double b = 0;
		double b1 = 0;
		double b2 = 0;
		double c = 0;
		double d = 0;
		double d1 = 0;
		double e = 0;

		int n = coords.length;

		for (int i = 0; i < n; i++) {
			a += coords[i][0]*coords[i][1];
			b1 += coords[i][0];
			b2 += coords[i][1];
			c += Math.pow(coords[i][0], 2);
			d1 += coords[i][0];
			e += coords[i][1];
		}
		a *= n;
		b = b1 * b2;
		c *= n;
		d = Math.pow(d1, 2);

		double grad = (a-b)/(c-d);
		double icpt = (e-(grad * d1))/n;

		double[] returner = {grad, icpt};
		return returner;
	}

	/**
	 * Outputs to the console when debug config is <i>true</i>
	 * @param value Message to print
	 */
	public static void debug(String value) {
//		if (Config.ENABLE_DEBUG) {
//			System.out.println(value);
//		}
	}
	
	public static double[][] readProfileFile(String filePath) {
		filePath = "/home/lvuser/profiles/" + filePath;
		System.out.println("RAN!");
		ArrayList<double[]> profileList = new ArrayList<double[]>();
		Path path = Paths.get(filePath);
		System.out.println("RAN! 2");
		try(BufferedReader br = Files.newBufferedReader(path, StandardCharsets.US_ASCII)) {
			String line = br.readLine();
			while(line != null) {
				String[] values = line.split(",");
				System.out.println("RAN! 3");
				profileList.add(new double[] {Double.parseDouble(values[0]), Double.parseDouble(values[1]), Double.parseDouble(values[2])});
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("RAN! 4");
		int listSize = profileList.size();
		double[][] profile = new double[listSize][3];
		for(int i=0; i < listSize; i++) {
			profile[i][0] = profileList.get(i)[0];
			profile[i][1] = profileList.get(i)[1];
			profile[i][2] = profileList.get(i)[2];
		}
		System.out.println("RAN! 5");
		return profile;
	}
}
