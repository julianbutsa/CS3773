package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
@RunWith(Suite.class)
@Suite.SuiteClasses({
   TestJSON.class,
   TestLogin.class,
   TestMyUtilities.class,
   TestWebService.class
})



public class AllTests {   
}  