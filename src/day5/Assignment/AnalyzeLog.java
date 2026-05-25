package day5.Assignment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AnalyzeLog {
    private HashMap<String, Integer> logCounter; 
    private ArrayList<String> errorMessages;
    private int numberOfEntries;

    private LocalDateTime early;
    private LocalDateTime latest;
    private DateTimeFormatter formatter;

    public AnalyzeLog(){
        this.logCounter = new HashMap<>();
        this.errorMessages = new ArrayList<>();

        this.logCounter.put("INFO", 0);
        this.logCounter.put("ERROR", 0);
        this.logCounter.put("WARN", 0);

        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        this.numberOfEntries = 0;
    }

    public void analyzeFile(String fileName){
        try (BufferedReader br = new BufferedReader(new FileReader(fileName));){
            String line;

            while ((line = br.readLine()) != null){
                validate(line);

                int closingBracket = line.indexOf("]");
                String timeStampString = line.substring(1, closingBracket);

                String remainingLines = line.substring(closingBracket + 1).trim();
                int colonIndex = remainingLines.indexOf(":");

                String messageLevel = remainingLines.substring(0, colonIndex);
                String message = remainingLines.substring(colonIndex + 2);

                LocalDateTime timestamp = LocalDateTime.parse(timeStampString, formatter);

                this.numberOfEntries++;

                logCounter.put(messageLevel, logCounter.get(messageLevel) + 1);

                if (messageLevel.equals("ERROR")){
                    errorMessages.add(message);
                }

                if (early == null || timestamp.isBefore(early)){
                    early = timestamp;
                }

                if (latest == null || timestamp.isAfter(latest)){
                    latest = timestamp;
                }
            }

            WriteFile("src/day5/Assignment/summary.txt");
            System.out.println("Summary created...");

        }catch(FileNotFoundException e){
            System.out.println("File " + fileName + " not found");
        }catch (IOException e){
            System.out.println("Error reading file.");
        }catch(MalformedLogEntryException e){
            System.out.println("Malformed log entry: " + e.getMessage());
        }
    }

    private void WriteFile(String fileName){
       try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))){
        bw.write("Log Summary Report");
        bw.newLine();
        bw.write("-------------------");
        bw.newLine();

        bw.write("Total Entries: " + numberOfEntries);
        bw.newLine();
        bw.write("INFO: ");
        bw.write(String.valueOf(logCounter.get("INFO")));
        bw.newLine();
        bw.write("WARN: ");
        bw.write(String.valueOf(logCounter.get("WARN")));
        bw.newLine();
        bw.write("ERROR: ");
        bw.write(String.valueOf(logCounter.get("ERROR")));
        bw.newLine();
        bw.newLine();

        for (String messages : errorMessages){
            bw.write("- " + messages);
            bw.newLine();
        }

        bw.newLine();

        bw.write("Earliest Timestamp: " + early);
        bw.newLine();
        bw.write("Latest Timestamp: " + latest);

       }catch (IOException e){
        System.out.println(e.getMessage());
       }
    }

    private void validate(String line) throws MalformedLogEntryException{
        if (!line.startsWith("[") || !line.contains("]")){
            throw new MalformedLogEntryException(line);
        }

        int closingBracketIndex = line.indexOf("]");

        if (closingBracketIndex + 2 >= line.length()){
            throw new MalformedLogEntryException(line);
        }

        String remainingLines = line.substring(closingBracketIndex + 1).trim();

        if (!(remainingLines.startsWith("INFO:") || remainingLines.startsWith("WARN:") || remainingLines.startsWith("ERROR:"))){
            throw new MalformedLogEntryException(remainingLines);
        }
    }
}
