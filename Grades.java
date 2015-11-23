import java.util.*;

public class Grades {

  public static class Record {
    public String studentId;
    public String testId;
    public Double score;

    public Record(final String student, final String test, final Double score) {
      this.studentId = student;
      this.testId = test;
      this.score = score;
    }

    public String toString() {
      return new String(studentId + "@" + testId + ":" + score);
    }
  }

  public static Double getTopK(Map<String, Double> scores, int K) {
    List<Double> results = new ArrayList<Double>(scores.values());
    Collections.sort(results, Collections.reverseOrder());
    Double sum = 0.0;
    for (int i=0; i<K; i++) sum += results.get(i);
    return new Double(sum/K);
  }

  public static Collection<Record> computeTopK(int K, Collection<Record> testScores) {
    List<Record> finalScores = new ArrayList<Record>();
    Map<String, Map<String, Double>> map = new HashMap<String, Map<String, Double>>();
    for (Record record : testScores) {
      if (map.containsKey(record.studentId)) {
        Map<String,Double> tests = map.get(record.studentId);
        if (tests.containsKey(record.testId)) {
          if (record.score.doubleValue() > tests.get(record.testId).doubleValue()) {
            tests.put(record.testId, record.score);
          }
        }
        else {
          tests.put(record.testId, record.score);
        }
      } else {
        Map<String, Double> tests = new HashMap<String, Double>();
        tests.put(record.testId, record.score);
        map.put(record.studentId, tests);
      }
    }
    for (Map.Entry<String, Map<String, Double>> tests : map.entrySet()) {
      finalScores.add(new Record(tests.getKey(), "final", getTopK(tests.getValue(), K)));
    }
    return finalScores;
  }

  public static void main(String[] args) {
    Collection testScores = new ArrayList<Record>();
    testScores.add(new Record("Adam", "Math", 10.0));
    testScores.add(new Record("Adam", "English", 10.0));
    testScores.add(new Record("Adam", "English", 8.0));
    testScores.add(new Record("Adam", "French", 9.0));
    testScores.add(new Record("Adam", "History", 7.0));
    testScores.add(new Record("Adam", "Biology", 5.0));
    testScores.add(new Record("Adam", "Music", 8.0));
    testScores.add(new Record("Beth", "Math", 8.5));
    testScores.add(new Record("Beth", "English", 10.0));
    testScores.add(new Record("Beth", "French", 8.0));
    testScores.add(new Record("Beth", "French", 9.0));
    testScores.add(new Record("Beth", "History", 8.0));
    testScores.add(new Record("Beth", "Music", 10.0));
    testScores.add(new Record("Beth", "Music", 10.0));
    testScores.add(new Record("Beth", "Music", 3.0));
    System.out.println(testScores.toString());
    Collection<Record> finalScores = computeTopK(Integer.parseInt(args[0]), testScores);
    System.out.println(finalScores.toString());
  }
}
