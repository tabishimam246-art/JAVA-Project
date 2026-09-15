package DS;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import JAVA_2.LogRecords;

public class InMemoryIndex {
    public Map<String, List<Integer>> keywordIndex;
    public Map<String, TreeMap<LocalDateTime, List<Integer>>> timeIndex;
    public Map<Integer, LogRecords> recordStore;

    public InMemoryIndex(Map<String, List<Integer>> keywordIndex, Map<String, TreeMap<LocalDateTime, List<Integer>>> timeIndex, Map<Integer, LogRecords> recordStore) {
        this.keywordIndex = keywordIndex;
        this.timeIndex = timeIndex;
        this.recordStore = recordStore;
    }

    public void add(LogRecords record) {
        if(record == null)
            return;

        recordStore.put(record.getEventId(), record);
        //adding keyword with reference to severity
        addToKeywordIndex(record.getSeverity(), record.getEventId());

        //adding keyword with reference to exception type
        if(record.getExceptionType() != null && !record.getExceptionType().isEmpty()) {
            //to add in keywordIndex
            addToKeywordIndex(record.getExceptionType(), record.getEventId());

            //to add in time index
            addToTimeIndex(record.getExceptionType(), record.getEventTime(), record.getEventId());
        }

        String[] words = record.getMessage().split("\\s+");

        //adding keyword with reference to message
        for(String word : words) {
            addToKeywordIndex(word.toLowerCase(), record.getEventId());
        }
    }

    public void addToKeywordIndex(String keyword, int eventId) {
        keyword = keyword.toLowerCase();
        keywordIndex.putIfAbsent(keyword, new ArrayList<>());
        keywordIndex.get(keyword).add(eventId);
    }

    public void addToTimeIndex(String exceptionType, LocalDateTime time, int eventId) {
        exceptionType = exceptionType.toLowerCase();

        timeIndex.putIfAbsent(exceptionType, new TreeMap<>());

        TreeMap<LocalDateTime, List<Integer>> map = timeIndex.get(exceptionType);
        map.putIfAbsent(time, new ArrayList<>());
        map.get(time).add(eventId);
    }

    public List<LogRecords> searchByKeyword(String keyword) {
        keyword = keyword.toLowerCase();

        List<LogRecords> result = new ArrayList<>();
        for(LogRecords record : recordStore.values()) {
            if(record.getMessage().toLowerCase().contains(keyword)) {
                result.add(record);
            }
        }

        return result;
    }

    public List<LogRecords> searchByExceptionType(String exceptionType) {
        exceptionType = exceptionType.toLowerCase();
        List<Integer> ids = new ArrayList<>();
        if(keywordIndex.containsKey(exceptionType)) {
            ids = keywordIndex.getOrDefault(exceptionType, new ArrayList<>());
        }
        return getRecords(ids);
    }

    public List<LogRecords> searchBySeverity(String severity) {
        severity = severity.toUpperCase();
        List<LogRecords> result = new ArrayList<>();

        for(LogRecords record : recordStore.values()) {
            if(record.severity.equalsIgnoreCase(severity)) {
                result.add(record);
            }
        }

        return result;
    }

    private List<LogRecords> getRecords(List<Integer> ids) {
        List<LogRecords> records = new ArrayList<>();

        for(Integer id : ids) {
            LogRecords record = recordStore.get(id);
            if(record != null)
                records.add(record);
        }
        return records;
    }
}