package JAVA_2;

import DBMS.DBManager;
import DS.InMemoryIndex;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;


public class ParserWorker extends Thread {
    private final BlockingQueue<String> rawQueue;
    private final InMemoryIndex index;
    private final DBManager dbManager;
    private int remainingEndMarkers;

    private int eventId = 1;

    public ParserWorker(BlockingQueue<String> rawQueue, InMemoryIndex index, DBManager dbManager, int producerCount) {
        this.rawQueue = rawQueue;
        this.index = index;
        this.dbManager = dbManager;
        this.remainingEndMarkers = producerCount;
    }

    public void run() {
        try {
            while(true) {
                String line = rawQueue.take();

                if(isEndMarker(line)) {
                    if(--remainingEndMarkers == 0) {
                        break;
                    }
                    continue;
                }

                LogRecords record = parseLine(line);

                if(record != null) {
                    //store in hashmap and treemap
                    index.add(record);

                    //store in database
                    dbManager.insertLogEvent(record);
                }
            }
            System.out.println("Parsed successfully.");
        } catch(InterruptedException e) {
            System.out.println("Parse Thread Interrupted");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private LogRecords parseLine(String line) {
        try {
            String[] parts = line.split("\\|");
            if(parts.length != 7) {
                System.out.println("Invalid log format");
                return null;
            }

            String sourceName = parts[1];
            LocalDateTime eventTime = LocalDateTime.parse(parts[0]);
            String zone = parts[2];
            String severity = parts[3];
            String exceptionType = parts[4];
            String message = parts[5];
            String stackTrace = parts[6];

            return new LogRecords(eventId++, sourceName, zone, eventTime, severity, exceptionType, message, stackTrace);
        } catch (Exception e) {
            System.out.println("Error Parsing Log : "+line);
            return null;
        }
    }

    private boolean isEndMarker(String line) {
        return line.equals("END");
    }
}
