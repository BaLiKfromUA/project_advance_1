package test.files;

import balik.advanced.consoleApp.Main;
import balik.advanced.consoleApp.parser.Message;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class runAllTestsFiles {
    private final String OK = Message.TEST_OK.getMessage() + "\n";
    private final String inputPattern = "./tests/input/%s/%s%d.input";
    private final String outputPattern = "./tests/answers/%s/%s%d.answer";
    private final String commandPattern = "%s,%s";

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test
    public void runAll() {
        String[] names = {"increasingNegative","decreasingNegative","increasingPositive","decreasingPositive",
        "insertZero","insertSameRandom","insertExtract","minFromOne","empty","insertExtractIncreasing","insertExtractIncreasingRand",
        "insertRandom"};

        for (final String testName : names) {
            Main.main(new String[]{"--test", String.format(commandPattern,
                    String.format(inputPattern, TestPatterns.SMALL.getSizeName(), testName, TestPatterns.SMALL.getSize()),
                    String.format(outputPattern, TestPatterns.SMALL.getSizeName(), testName, TestPatterns.SMALL.getSize()))});

            assertEquals(OK, systemOutRule.getLog());
            systemOutRule.clearLog();

            Main.main(new String[]{"--test", String.format(commandPattern,
                    String.format(inputPattern, TestPatterns.MEDIUM.getSizeName(), testName, TestPatterns.MEDIUM.getSize()),
                    String.format(outputPattern, TestPatterns.MEDIUM.getSizeName(), testName, TestPatterns.MEDIUM.getSize()))});

            assertEquals(OK, systemOutRule.getLog());
            systemOutRule.clearLog();

            Main.main(new String[]{"--test", String.format(commandPattern,
                    String.format(inputPattern, TestPatterns.BIG.getSizeName(), testName, TestPatterns.BIG.getSize()),
                    String.format(outputPattern, TestPatterns.BIG.getSizeName(), testName, TestPatterns.BIG.getSize()))});

            assertEquals(OK, systemOutRule.getLog());
            systemOutRule.clearLog();

            Main.main(new String[]{"--test", String.format(commandPattern,
                    String.format(inputPattern, TestPatterns.LARGE.getSizeName(), testName, TestPatterns.LARGE.getSize()),
                    String.format(outputPattern, TestPatterns.LARGE.getSizeName(), testName, TestPatterns.LARGE.getSize()))});

            assertEquals(OK, systemOutRule.getLog());
            systemOutRule.clearLog();
        }
    }

    @After
    public void clearLogs() {
        systemOutRule.clearLog();

    }
}
