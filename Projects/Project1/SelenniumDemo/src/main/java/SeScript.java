import org.openqa.selenium.chrome.ChromeDriver;

public class SeScript {

	public static void main(String[] args) {
		String url = "http://newtours.demoaut.com/";
		ChromeDriver driver = new ChromeDriver();
		
		driver.get(url);
//		checkTitle("Welcome: Mercury Tours");
	}
}
