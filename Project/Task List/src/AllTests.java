import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ GroupTest.class, SortingTest.class, TaskListTest.class,
		TaskTest.class })
public class AllTests {

}
