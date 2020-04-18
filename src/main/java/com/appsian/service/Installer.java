package com.appsian.service;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.Zip;
import org.springframework.stereotype.Component;
import org.testng.Assert;

@Component
public class Installer {
	static {

        System.setProperty("java.awt.headless", "false");
}
	@SuppressWarnings("static-access")
	public void appInstaller() throws IOException, InterruptedException {
//	Get Test data from Properties file
		Properties prop = new Properties();
		FileInputStream input = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\resources\\application.properties");
		prop.load(input);

//	Load test data to variables

		String Instance_Name = prop.getProperty("Instance_Name");
		Instance_Name = Instance_Name.replaceAll("\\s", "");
		String DatabaseName = prop.getProperty("DatabaseName");
		DatabaseName = DatabaseName.replaceAll("\\s", "");
		String JenkinsURL = prop.getProperty("URL");
		String ServerPassword = prop.getProperty("ServerPassword");
		String WindowsAction = prop.getProperty("WindowsAction");
		String InstallProduct = prop.getProperty("InstallProduct");
		String NewInstall = prop.getProperty("NewInstall");
		String PUXLicenceKey = prop.getProperty("PUXLicenceKey");
		String FirewallLicenceKey = prop.getProperty("FirewallLicenceKey");

		System.out.println(DatabaseName);
		System.out.println(Instance_Name);
//	Declare variables and assign values

		String exe_Path = System.getProperty("user.dir") + "\\ProjectInstaller_SeleniumV1.exe";
		String AppServer_Path;
		String WebServer_Path;
		String ProjectInstaller_Path;
		String ProjectXpath = null;
		String WebBin = null;
		String AppVersion = null;
		String BatchScript = null;
		String AppURL = null;
		String Path = null;
		String AppBin = "./gh_fwappserv.bin";
		String UserName = "PS";
		String Password = "PS";
		String home = System.getProperty("user.home");
		String downloadPath = home + "\\Downloads";
//	File LocalFilePath = new File(home + "/Downloads/");

//=========================================================================
		char Value = DatabaseName.charAt(4);
		AppVersion = "8.5" + Value;
		BatchScript = "C:\\batchscripts\\Configure85" + Value + "AppDesigner.bat";

		if (Instance_Name.toLowerCase().contains("f2t")) {
			UserName = "VP1";
			Password = "VP1";
			AppURL = "EMPLOYEE/ERP";
		} else if (Instance_Name.toLowerCase().contains("c2t")) {
			AppURL = "EMPLOYEE/PSFT_CS";
		} else if (Instance_Name.toLowerCase().contains("h2t")) {
			AppURL = "EMPLOYEE/HRMS";
		}

		if (InstallProduct.equalsIgnoreCase("firewall")) {
			WebBin = "./gh_firewall_web.bin";
			ProjectXpath = "//a[text()='ERP_Firewall_Package.zip']";
			Path = "ERP_Firewall";
		} else if (InstallProduct.equalsIgnoreCase("PUX")) {
			WebBin = "./gh_mobile_web.bin";
			ProjectXpath = "//a[text()='PeopleMobile_Package.zip']";
			Path = "PeopleMobile";
		}
//=========================================================================		

		try {
			FileUtils.cleanDirectory(new File(downloadPath + "\\Upgrade"));
		} catch (Exception e) {
			System.out.println("Upgrade Folder Not Available In Downloads");
		}

		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\geckodriver.exe");
//	WebDriver driver = new ChromeDriver();
		WebDriver driver = new FirefoxDriver();
		driver.get(JenkinsURL);
		// Point p = new Point(0, 3000);
		Thread.sleep(1000);
		driver.manage().window().maximize();

		driver.findElement(By.xpath("//input[@id='j_username']")).click();
		driver.findElement(By.xpath("//input[@id='j_username']")).sendKeys("prakashg");
		driver.findElement(By.xpath("//input[@name='j_password']")).click();
		driver.findElement(By.xpath("//input[@name='j_password']")).sendKeys("pg@aug19");
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(ProjectXpath)).click();
		Thread.sleep(1000);

		Robot robot;
		try {
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_DOWN);
			Thread.sleep(500);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Thread.sleep(5000);

		File getLatestFile = getLatestFilefromDir(downloadPath);
		String fileName = getLatestFile.getName();
		System.out.println(fileName);
		Zip zip = new Zip();
		FileInputStream Test = new FileInputStream(downloadPath + "\\" + fileName);
		zip.unzip(Test, new File(downloadPath + "\\Upgrade"));

		AppServer_Path = downloadPath + "\\Upgrade\\" + Path + "\\AppServer\\Unix";
		WebServer_Path = downloadPath + "\\Upgrade\\" + Path + "\\WebServer\\Unix";
		ProjectInstaller_Path = downloadPath + "\\Upgrade\\" + Path + "\\Projects";
		System.out.println(AppServer_Path);
		System.out.println(WebServer_Path);
		System.out.println(ProjectInstaller_Path);

//	driver.quit();
//	 driver.manage().window().setPosition(p);
		ProcessBuilder Build = new ProcessBuilder(exe_Path, Instance_Name, DatabaseName, AppServer_Path, WebServer_Path,
				ProjectInstaller_Path, AppBin, WebBin, AppVersion, BatchScript, UserName, Password, ServerPassword,
				WindowsAction, NewInstall, PUXLicenceKey, InstallProduct);

		Process process = Build.start();
		process.waitFor();

		Thread.sleep(3000);
		driver.manage().window().maximize();

		driver.get("http://" + Instance_Name + ".gh.pri:8000/psp/ps/?cmd=login");

		driver.findElement(By.xpath("//input[@id='userid']")).click();
		driver.findElement(By.xpath("//input[@id='userid']")).sendKeys(UserName);
		driver.findElement(By.xpath("//input[@id='pwd']")).click();
		driver.findElement(By.xpath("//input[@id='pwd']")).sendKeys(Password);
		driver.findElement(By.xpath("//input[@name='Submit']")).click();
		Thread.sleep(1000);

		driver.get("http://" + Instance_Name + ".gh.pri:8000/psc/ps/" + AppURL + "/c/GS_FW.GS_FW_SETUP.GBL?");
		driver.findElement(By.xpath("//textarea[@id='GS_FW_SYSTEM_GS_LICENSE_CODE']")).click();
		String LicenceInApp = driver.findElement(By.xpath("//textarea[@id='GS_FW_SYSTEM_GS_LICENSE_CODE']")).getText();
		if (LicenceInApp != null) {
			System.out.println("Licence Key Is available");
		}
		if (NewInstall.equalsIgnoreCase("yes") && (InstallProduct.equalsIgnoreCase("Firewall"))) {
			driver.findElement(By.xpath("//textarea[@id='GS_FW_SYSTEM_GS_LICENSE_CODE']")).click();
			driver.findElement(By.xpath("//textarea[@id='GS_FW_SYSTEM_GS_LICENSE_CODE']")).clear();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//textarea[@id='GS_FW_SYSTEM_GS_LICENSE_CODE']")).sendKeys(FirewallLicenceKey);
			driver.findElement(By.xpath("//input[@id='#ICSave']")).click();
		}

		driver.get("http://" + Instance_Name + ".gh.pri:8000/psc/ps_newwin/" + AppURL
				+ "/c/GS_FW.GS_SYS_CONSOLE.GBL?fwcmd=help");
		Thread.sleep(1500);
		String BuildNumber = driver.findElement(By.xpath("//span[@class='ghc-title-prod-rev']")).getText();
		System.out.println(BuildNumber);
		String Revision = JenkinsURL.substring(JenkinsURL.length() - 6);
		Revision = Revision.replaceAll("[^a-zA-Z0-9]", "");
		boolean checkBuild = BuildNumber.contains(Revision);

		if (NewInstall.equalsIgnoreCase("yes") && (InstallProduct.equalsIgnoreCase("Firewall"))) {
			driver.findElement(By.cssSelector(
					"div.ghc-section.ghc-show-table:nth-child(5) table.ghc-servers tbody:nth-child(2) tr.ghc-selected td:nth-child(6) > a:nth-child(1)"))
					.click();
			Thread.sleep(3000);

		}

		Assert.assertEquals(checkBuild, true);

		if (InstallProduct.equalsIgnoreCase("PUX")) {
			driver.get("http://" + Instance_Name + ".gh.pri:8000/psp/ps_newwin/" + AppURL
					+ "/c/GS_MOBL.GS_SYS_CONSOLE.GBL?mobcmd=help");
			BuildNumber = driver.findElement(By.xpath("//span[@class='ghc-title-prod-rev']")).getText();
			System.out.println(BuildNumber);
			checkBuild = BuildNumber.contains(Revision);

			Assert.assertEquals(checkBuild, true);
		}
		driver.quit();

	}

	private static File getLatestFilefromDir(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}
		return lastModifiedFile;
	}
}
