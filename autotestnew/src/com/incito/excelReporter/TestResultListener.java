package com.incito.excelReporter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.demo.test.utils.FileHelper;

public class TestResultListener extends TestListenerAdapter {

	//private static Logger logger = Logger.getLogger(TestResultListener.class);
	
	protected ITestContext testContext = null; // 这里也是新加的
	String  browser = null;
	

	@Override
	public void onStart(ITestContext testContext) { // 这里也是新加的，用于对context进行统一
		this.testContext = testContext;
		browser = String.valueOf(testContext.getCurrentXmlTest().getParameter("browserName"));
		super.onStart(testContext);
	}

	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		//logger.warn(tr.getName() + " 测试用例执行失败！");
		FileHelper.info(tr.getName() + " 测试用例执行失败！");
		WebDriver webDriver = (WebDriver) testContext.getAttribute("SELENIUM_DRIVER"); // 这里就是取driver
		saveScreenShot(tr, webDriver,browser);
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		super.onTestSkipped(tr);
		WebDriver webDriver = (WebDriver) testContext.getAttribute("SELENIUM_DRIVER");
		//logger.warn(tr.getName() + " 测试用例由于某些原因被跳过！");
		FileHelper.info(tr.getName() + " 测试用例由于某些原因被跳过！");
		saveScreenShot(tr, webDriver,browser);

	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		super.onTestSuccess(tr);
		//logger.info(tr.getName() + " 测试用例执行成功！");
		FileHelper.info(tr.getName() + " 测试用例执行成功！");
	}

	@Override
	public void onTestStart(ITestResult tr) {
		super.onTestStart(tr);
		//logger.info(tr.getName() + " 测试用例开始执行！");
		FileHelper.info(tr.getName() + " 测试用例开始执行！");
	}

	@Override
	public void onFinish(ITestContext testContext) {
		super.onFinish(testContext);

		// List of test results which we will delete later
		ArrayList<ITestResult> testsToBeRemoved = new ArrayList<ITestResult>();
		// collect all id's from passed test
		Set<Integer> passedTestIds = new HashSet<Integer>();
		for (ITestResult passedTest : testContext.getPassedTests().getAllResults()) {
			//logger.info("执行成功的用例 = " + passedTest.getName());
			FileHelper.info("执行成功的用例 = " + passedTest.getName());
			passedTestIds.add(getId(passedTest));
		}

		// Eliminate the repeat methods
		Set<Integer> skipTestIds = new HashSet<Integer>();
		for (ITestResult skipTest : testContext.getSkippedTests().getAllResults()) {
			//logger.info("被跳过的用例 = " + skipTest.getName());
			FileHelper.info("被跳过的用例 = " + skipTest.getName());
			int skipTestId = getId(skipTest);

			if (skipTestIds.contains(skipTestId) || passedTestIds.contains(skipTestId)) {
				testsToBeRemoved.add(skipTest);
			} else {
				skipTestIds.add(skipTestId);
			}
		}

		// Eliminate the repeat failed methods
		Set<Integer> failedTestIds = new HashSet<Integer>();
		for (ITestResult failedTest : testContext.getFailedTests().getAllResults()) {
			//logger.info("执行失败的用例 = " + failedTest.getName());
			FileHelper.info("执行失败的用例 = " + failedTest.getName());
			int failedTestId = getId(failedTest);
			if (failedTestIds.contains(failedTestId) || passedTestIds.contains(failedTestId) || skipTestIds.contains(failedTestId)) {
				testsToBeRemoved.add(failedTest);
			} else {
				failedTestIds.add(failedTestId);
			}
		}

	}

	private int getId(ITestResult result) {
		int id = result.getTestClass().getName().hashCode();
		id = id + result.getMethod().getMethodName().hashCode();
		id = id + (result.getParameters() != null ? Arrays.hashCode(result.getParameters()) : 0);
		return id;
	}

	private void saveScreenShot(ITestResult tr, WebDriver driver, String browser) {
		String fileName =tr.getTestClass().getRealClass().getSimpleName();
		String filePath = "";
		try {
			// 这里可以调用不同框架的截图功能
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			filePath = "result/screenshot/"+ fileName + ".png";
			File destFile = new File(filePath);
			if(destFile.exists()){
				destFile.delete();
			}
			FileUtils.copyFile(screenshot, destFile);
			//logger.info("["+fileName + "] 截图成功，保存在：" + "[ " + filePath + " ]");
			FileHelper.info("["+fileName + "] 截图成功，保存在：" + "[ " + filePath + " ]");

		} catch (Exception e) {
			filePath = "["+fileName+"]" + " ,截图失败，原因：" + e.getMessage();
			//logger.error(filePath);
			FileHelper.info(filePath);
		}

	}
	


}

