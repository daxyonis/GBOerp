import static org.junit.Assert.*;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import play.test.WithBrowser; 

public class WebSoumListTest extends WithBrowser {              
          
    @Test
    public void openPageListeSoumissions() {
        browser.goTo("/appsoumissions/");
        testLogin();
        browser.await().atMost(3000);        
        
        // Check all is there        
        assertEquals("Liste des soumissions", browser.getDriver().getTitle());
        assertEquals("Liste des soumissions", browser.getDriver().findElement(By.cssSelector("h1")).getText());        

        // The following was generated using Selenium IDE
        assertTrue(isElementPresent(By.cssSelector("span.etiquette")));
        assertTrue(isElementPresent(By.id("numero")));
        assertTrue(isElementPresent(By.id("rep")));
        assertTrue(isElementPresent(By.id("statut")));
        assertTrue(isElementPresent(By.id("dateDebut")));
        assertTrue(isElementPresent(By.id("dateFin")));
        assertTrue(isElementPresent(By.cssSelector("input.form-control")));
        assertTrue(isElementPresent(By.id("table")));
    }
    
    private void testLogin(){
	assertNotNull(browser.$("title").getText());
	assertEquals("/login", browser.url());        
	browser.fill("input").with("test@groupeboconcept.com","test");
	browser.click("button[type='submit']");
       }
    
    private boolean isElementPresent(By by) {
	try{ 
	    browser.getDriver().findElement(by);
	    return true;
	} catch (NoSuchElementException e) {
	    return false;
	}
    }
}


