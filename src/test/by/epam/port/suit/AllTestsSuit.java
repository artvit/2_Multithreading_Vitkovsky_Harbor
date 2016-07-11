package test.by.epam.port.suit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.by.epam.port.action.HarborRunnerTest;
import test.by.epam.port.action.ShipIdGeneratorTest;
import test.by.epam.port.parser.JsonParserTest;

@Suite.SuiteClasses( { JsonParserTest.class, ShipIdGeneratorTest.class, HarborRunnerTest.class} )
@RunWith(Suite.class)
public class AllTestsSuit {
}
