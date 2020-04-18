package com.appsian.model;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Installer {
	@NotBlank(message = "Name is mandatory")
	private String instanceName;
	@NotBlank(message = "Name is mandatory")
	private String dbName;
	@NotBlank(message = "Name is mandatory")
	private String installProduct;
	@NotBlank(message = "Name is mandatory")
	private String instalType;
	@NotBlank(message = "Name is mandatory")
	private String jenkinsUserName;
	@NotBlank(message = "Name is mandatory")
	private String jenkinsPwd;
	@NotBlank(message = "Name is mandatory")
	private String jenkinsURL;
	@NotBlank(message = "Name is mandatory")
	private String windowsAction;

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getInstallProduct() {
		return installProduct;
	}

	public void setInstallProduct(String installProduct) {
		this.installProduct = installProduct;
	}

	public String getInstalType() {
		return instalType;
	}

	public void setInstalType(String instalType) {
		this.instalType = instalType;
	}

	public String getJenkinsUserName() {
		return jenkinsUserName;
	}

	public void setJenkinsUserName(String jenkinsUserName) {
		this.jenkinsUserName = jenkinsUserName;
	}

	public String getJenkinsPwd() {
		return jenkinsPwd;
	}

	public void setJenkinsPwd(String jenkinsPwd) {
		this.jenkinsPwd = jenkinsPwd;
	}

	public String getJenkinsURL() {
		return jenkinsURL;
	}

	public void setJenkinsURL(String jenkinsURL) {
		this.jenkinsURL = jenkinsURL;
	}

	public String getWindowsAction() {
		return windowsAction;
	}

	public void setWindowsAction(String windowsAction) {
		this.windowsAction = windowsAction;
	}

	@Override
	public String toString() {
		return "Installer [instanceName=" + instanceName + ", dbName=" + dbName + ", installProduct=" + installProduct
				+ ", instalType=" + instalType + ", jenkinsUserName=" + jenkinsUserName + ", jenkinsPwd=" + jenkinsPwd
				+ ", jenkinsURL=" + jenkinsURL + ", windowsAction=" + windowsAction + "]";
	}

}
