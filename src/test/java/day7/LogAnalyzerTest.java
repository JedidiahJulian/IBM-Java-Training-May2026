package day7;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

public class LogAnalyzerTest {

    private final Path targetSummaryPath = Path.of("src/main/resources/summary.txt");

    /**
     * Prepares the output location used by LogAnalyzer before each scenario.
     * The production class writes to resources/summary.txt, which is mapped to
     * src/main/resources/summary.txt in this project, so removing this file keeps
     * assertions isolated from previous test runs.
     */
    private void initEnvironment() throws Exception {
        Files.createDirectories(Path.of("src/main/resources"));
        Files.deleteIfExists(targetSummaryPath);
    }

    /**
     * Verifies that LogAnalyzer can be instantiated.
     * This is a simple construction check for the public class before testing its
     * static main workflow.
     */
    @Test
    void exec001() {
        LogAnalyzer analyzer = new LogAnalyzer();
        assertNotNull(analyzer);
    }

    /**
     * Verifies that a malformed log entry missing timestamp bracket structure is skipped.
     * The exec002 fixture contains invalid timestamp bracket examples, so the test
     * captures System.out and confirms the skip message is printed.
     */
    @Test
    void exec002() throws Exception {
        initEnvironment();
        String file = "src/test/resources/exec002/server.log";

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            LogAnalyzer.main(new String[]{file});
        } finally {
            System.setOut(originalOut);
        }

        try {
            assertTrue(output.toString().contains("Skipping malformed line"), "Should log skipping message to console!");
        } finally {
            Files.deleteIfExists(targetSummaryPath);
        }
    }

    /**
     * Verifies summary generation for a valid three-entry log file.
     * The exec003 fixture covers INFO, WARN, and ERROR entries and checks that
     * total counts, error messages, and earliest/latest timestamps match the expected report.
     */
    @Test
    void exec003() throws Exception {
        initEnvironment();
        String expectedFileContent = Files.readString(Path.of("src/test/resources/exec003/summary.txt")).trim().replace("\r\n", "\n");
        String file = "src/test/resources/exec003/server.log";

        LogAnalyzer.main(new String[]{file});

        try {
            assertTrue(Files.exists(targetSummaryPath), "The summary file must be generated in src/main/resources/");
            String summaryFileContent = Files.readString(targetSummaryPath).trim().replace("\r\n", "\n");
            assertEquals(expectedFileContent, summaryFileContent);
        } finally {
            Files.deleteIfExists(targetSummaryPath);
        }
    }

    /**
     * Verifies missing input file handling.
     * The analyzer should print the file-not-found message, return early, and avoid
     * creating the summary output file.
     */
    @Test
    void exec004() throws Exception {
        initEnvironment();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            LogAnalyzer.main(new String[]{"src/test/resources/exec004/missing.log"});
        } finally {
            System.setOut(originalOut);
        }

        assertTrue(output.toString().contains("Log file not found."));
        assertFalse(Files.exists(targetSummaryPath), "Summary file should NOT be created for missing logs!");
    }

    /**
     * Verifies that a line with an invalid message delimiter is skipped.
     * The exec005 fixture has a timestamp and level but no ": " separator, which
     * exercises the missing-message validation branch.
     */
    @Test
    void exec005() throws Exception {
        initEnvironment();
        String file = "src/test/resources/exec005/server.log";

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            LogAnalyzer.main(new String[]{file});
        } finally {
            System.setOut(originalOut);
        }

        try {
            assertTrue(output.toString().contains("Skipping malformed line"));
        } finally {
            Files.deleteIfExists(targetSummaryPath);
        }
    }

    /**
     * Verifies successful processing for a valid log without ERROR entries.
     * The exec006 fixture confirms INFO/WARN counts, an empty Error Messages section,
     * summary file creation, and the success message printed to System.out.
     */
    @Test
    void exec006() throws Exception {
        initEnvironment();
        String expectedFileContent = Files.readString(Path.of("src/test/resources/exec006/summary.txt")).trim().replace("\r\n", "\n");
        String file = "src/test/resources/exec006/server.log";

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            LogAnalyzer.main(new String[]{file});
        } finally {
            System.setOut(originalOut);
        }

        assertTrue(output.toString().contains("Analysis complete. Summary written to summary.txt"));

        try {
            assertTrue(Files.exists(targetSummaryPath));
            String summaryFileContent = Files.readString(targetSummaryPath).trim().replace("\r\n", "\n");
            assertEquals(expectedFileContent, summaryFileContent);
        } finally {
            Files.deleteIfExists(targetSummaryPath);
        }
    }

    /**
     * Verifies unsupported log levels are rejected.
     * The exec007 fixture uses DEBUG with otherwise valid syntax, so the invalid-level
     * branch is exercised without failing earlier parsing checks.
     */
    @Test
    void exec007() throws Exception {
        initEnvironment();
        String file = "src/test/resources/exec007/server.log";

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            LogAnalyzer.main(new String[]{file});
        } finally {
            System.setOut(originalOut);
        }

        try {
            assertTrue(output.toString().contains("Skipping malformed line"));
        } finally {
            Files.deleteIfExists(targetSummaryPath);
        }
    }

    /**
     * Verifies entries missing a message payload are skipped.
     * The exec008 fixture includes a valid timestamp and level but omits the
     * required message delimiter/content.
     */
    @Test
    void exec008() throws Exception {
        initEnvironment();
        String file = "src/test/resources/exec008/server.log";

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            LogAnalyzer.main(new String[]{file});
        } finally {
            System.setOut(originalOut);
        }

        try {
            assertTrue(output.toString().contains("Skipping malformed line"));
        } finally {
            Files.deleteIfExists(targetSummaryPath);
        }
    }

    /**
     * Verifies full summary output for a mixed valid log file.
     * The exec009 fixture includes multiple INFO entries plus WARN and ERROR so the
     * generated report can be compared against a complete expected snapshot.
     */
    @Test
    void exec009() throws Exception {
        initEnvironment();
        String expectedFileContent = Files.readString(Path.of("src/test/resources/exec009/summary.txt")).trim().replace("\r\n", "\n");
        String file = "src/test/resources/exec009/server.log";

        LogAnalyzer.main(new String[]{file});

        try {
            assertTrue(Files.exists(targetSummaryPath));
            String summaryFileContent = Files.readString(targetSummaryPath).trim().replace("\r\n", "\n");
            assertEquals(expectedFileContent, summaryFileContent);
        } finally {
            Files.deleteIfExists(targetSummaryPath);
        }
    }

    /**
     * Verifies earliest/latest timestamp detection when logs are not chronological.
     * The exec010 fixture places an earlier timestamp after the first entry and a later
     * timestamp after that, covering both timestamp update branches.
     */
    @Test
    void exec010() throws Exception {
        initEnvironment();
        String expectedFileContent = Files.readString(Path.of("src/test/resources/exec010/summary.txt")).trim().replace("\r\n", "\n");
        String file = "src/test/resources/exec010/server.log";

        LogAnalyzer.main(new String[]{file});

        try {
            assertTrue(Files.exists(targetSummaryPath));
            String summaryFileContent = Files.readString(targetSummaryPath).trim().replace("\r\n", "\n");
            assertEquals(expectedFileContent, summaryFileContent);
        } finally {
            Files.deleteIfExists(targetSummaryPath);
        }
    }

    /**
     * Verifies the generic IOException read handler.
     * Normal Windows file failures usually become FileNotFoundException, so this test
     * mocks FileReader construction and forces read(...) to throw IOException.
     */
    @Test
    void exec011() throws Exception {
        initEnvironment();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try (MockedConstruction<FileReader> ignored = mockConstruction(FileReader.class,
                (mock, context) -> when(mock.read(any(char[].class), anyInt(), anyInt()))
                        .thenThrow(new IOException("forced read failure")))) {
            LogAnalyzer.main(new String[]{"src/test/resources/exec001/server.log"});
        } finally {
            System.setOut(originalOut);
        }

        assertTrue(output.toString().contains("Error reading file."));
        assertFalse(Files.exists(targetSummaryPath));
    }

    /**
     * Verifies the summary write IOException handler.
     * FileWriter is mocked to fail during write(...), confirming the analyzer reports
     * the write error while still following its current completion-message behavior.
     */
    @Test
    void exec012() throws Exception {
        initEnvironment();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try (MockedConstruction<FileWriter> ignored = mockConstruction(FileWriter.class,
                (mock, context) -> doThrow(new IOException("forced write failure"))
                        .when(mock).write(any(char[].class), anyInt(), anyInt()))) {
            LogAnalyzer.main(new String[]{"src/test/resources/exec003/server.log"});
        } finally {
            System.setOut(originalOut);
        }

        assertTrue(output.toString().contains("Error writing summary file."));
        assertTrue(output.toString().contains("Analysis complete. Summary written to summary.txt"));
    }
}
