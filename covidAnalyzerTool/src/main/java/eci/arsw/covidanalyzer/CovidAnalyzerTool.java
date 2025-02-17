package eci.arsw.covidanalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A Camel Application
 */
public class CovidAnalyzerTool {

    private static final int NUMBER_OF_THREADS = 5;
    private final ArrayList<TransationResulCovid> threads;
    private ResultAnalyzer resultAnalyzer;
    private TestReader testReader;
    private final AtomicInteger amountOfFilesProcessed;
    private int amountOfFilesTotal;

    public CovidAnalyzerTool() {
        threads = new ArrayList<>();
        resultAnalyzer = new ResultAnalyzer();
        testReader = new TestReader();
        amountOfFilesProcessed = new AtomicInteger();
    }

    public void processResultData() {
        amountOfFilesProcessed.set(0);
        List<File> resultFiles = getResultFileList();
        amountOfFilesTotal = resultFiles.size();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            threads.add(new CovidAnalyzerTool(testReader,resultAnalyzer,amountOfFilesProcessed),resultFiles);

        }
        threads.forEach(Thread::start);
        try {
            for (CovidAnalyzerTool t : threads) {
                t.join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<File> getResultFileList() {
        List<File> csvFiles = new ArrayList<>();
        try (Stream<Path> csvFilePaths = Files.walk(Paths.get("src/main/resources/")).filter(path -> path.getFileName().toString().endsWith(".csv"))) {
            csvFiles = csvFilePaths.map(Path::toFile).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvFiles;
    }



    public Set<Result> getPositivePeople() {
        return resultAnalyzer.listOfPositivePeople();
    }

    public void printStatus() {
        String message = "Processed %d out of %d files.\nFound %d positive people:\n%s";
        Set<Result> positivePeople = getPositivePeople();
        String affectedPeople = positivePeople.stream().map(Result::toString).reduce("", (s1, s2) -> s1 + "\n" + s2);
        message = String.format(message, amountOfFilesProcessed.get(), amountOfFilesTotal, positivePeople.size(), affectedPeople);
        System.out.println(message);
    }
    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {

        CovidAnalyzerTool covidAnalyzerTool = new CovidAnalyzerTool();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            if (line.contains("exit"))
                break;

        }
        Thread processingThread = new Thread(() -> covidAnalyzerTool.processResultData());
        processingThread.start();
        processingThread.join();
        covidAnalyzerTool.printStatus();
        System.exit(0);
    }

}

