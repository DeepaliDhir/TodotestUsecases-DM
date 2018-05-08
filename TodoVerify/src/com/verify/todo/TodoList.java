package com.verify.todo;

import org.testng.Assert;
import org.testng.Reporter;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import com.verify.todo.constant.TodoConstants;
import com.verify.todo.utilities.TodoUtils;

public class TodoList {
	public WebDriver driver;

	
	/**
	 * This method will intialize the webdriver before every method
	 * @throws InterruptedException
	 */
	@BeforeMethod
    public void initializeWebDriver() throws InterruptedException
    {
		
        driver=TodoUtils.initWebDriver();      
        
    }
	
	
	/**
	 * This method will test the addition of task in list
	 */
	@Test(priority = 1)
	public void addTaskList() throws InterruptedException {

		TodoUtils.addTasks(driver);
		Reporter.log("Added all the tasks");
		Thread.sleep(3000);

		int allListSize = TodoUtils.getAllTasks(driver);
		Reporter.log("All list count is : "+allListSize);
		Assert.assertEquals(allListSize, 4);

		int activeListSize = TodoUtils.getActiveTasks(driver);
		Reporter.log("Active list count is : "+activeListSize);
		Assert.assertEquals(activeListSize, 4);

	}

	/**
	 * This method will add the tasks in list and mark one task as completed and assert the result 
	 */
	@Test(priority = 2)
	public void markTaskCompleted() throws Exception {
		TodoUtils.addTasks(driver);
		List<WebElement> allElements = driver.findElements(By
				.xpath(TodoConstants.TODO_ALL_ELEMENT_LIST));
		int i = allElements.size();
		for (int j = 0; j < i; j++) {
			WebElement ele = allElements.get(j);
			String x = ele.getText();
			int z = j + 1;
			if (x.equalsIgnoreCase(TodoConstants.TODO_TASK_NAME_3)) {
				driver.findElement(
						By.xpath("//ul[@class='todo-list']/li[" + z
								+ "]/div/input[@type='checkbox']")).click();
				Thread.sleep(3000);
				break;
			}

		}
		int completedListSize = TodoUtils.getCompletedTasks(driver);
		Assert.assertEquals(completedListSize, 1);
	}

	/**
	 * This method will delete the task and verify theresult
	 */
	@Test(priority = 3)
	public void deleteTask() throws InterruptedException {
		TodoUtils.addTasks(driver);
		List<WebElement> allElements = driver.findElements(By
				.xpath(TodoConstants.TODO_ALL_ELEMENT_LIST));
		int i = allElements.size();
		for (int k = 0; k < i; k++) {
			WebElement ele = allElements.get(k);
			String x = ele.getText();
			int z = k + 1;

			if (x.equalsIgnoreCase(TodoConstants.TODO_TASK_NAME_2)) {
				Actions hover = new Actions(driver);
				hover.moveToElement(
						driver.findElement(By
								.xpath("//ul[@class='todo-list']/li[" + z
										+ "]/div/label"))).perform();
				driver.findElement(
						By.xpath("//ul[@class='todo-list']/li[" + z
								+ "]/div/button")).click();
				Thread.sleep(3000);
				break;
			}

		}
		int allListSize = TodoUtils.getAllTasks(driver);
		Assert.assertEquals(allListSize, 3);

	}
	
	
	/**
	 * This method will edit a task and verify the result
	 * @throws InterruptedException
	 */
	@Test(priority = 4)
	public void editTask() throws InterruptedException {
		TodoUtils.addTasks(driver);
		List<WebElement> allElements = driver.findElements(By
				.xpath(TodoConstants.TODO_ALL_ELEMENT_LIST));
		int i = allElements.size();
		for (int k = 0; k < i; k++) {
			WebElement ele = allElements.get(k);
			String x = ele.getText();
			int z = k + 1;

			if (x.equalsIgnoreCase(TodoConstants.TODO_TASK_NAME_2)) {

				Actions action = new Actions(driver);
				WebElement btnElement = driver.findElement(By
						.xpath("//ul[@class='todo-list']/li[" + z
								+ "]/div/label"));
				action.doubleClick(btnElement).sendKeys(" useful").build()
						.perform();
				driver.findElement(By.xpath(TodoConstants.TODO_ADD_TASK_INPUT))
						.click();
				Thread.sleep(2000);

				break;
			}
		}

		List<WebElement> elementsUpdated = driver.findElements(By
				.xpath(TodoConstants.TODO_ALL_ELEMENT_LIST));
		int size = elementsUpdated.size();
		for (int j = 0; j < size; j++) {
			WebElement test = elementsUpdated.get(j);
			String text = test.getText();
			if (text.equalsIgnoreCase("write usecase useful")) {

				break;
			}

		}
	}
	
	/**
	 * This method will select all the task and click on clear completed and verify the result 
	 * @throws InterruptedException
	 */
	@Test(priority=5)
	public void clearCompletedList() throws InterruptedException {
		TodoUtils.addTasks(driver);
		Thread.sleep(3000);
		driver.findElement(By.xpath(TodoConstants.TODO_MARK_ALL_ATSK_COMPLTED))
				.click();
		Thread.sleep(3000);
		driver.findElement(By.xpath(TodoConstants.TODO_CLICK_CLEAR_COMPLETED))
				.click();
		Thread.sleep(3000);
	}
	
	/**
	 * This method will quit and the driver after every test method execute
	 */
	@AfterMethod
	public void close(){
		driver.quit();
	}

	
}
