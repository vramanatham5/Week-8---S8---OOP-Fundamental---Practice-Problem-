import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

interface ExamQuestion {
    double score();
}

class ExactMatchQuestion implements ExamQuestion {
    private final String correctAnswer;
    private final String studentAnswer;
    private final double points;

    ExactMatchQuestion(String correctAnswer, String studentAnswer, double points) {
        this.correctAnswer = correctAnswer;
        this.studentAnswer = studentAnswer;
        this.points = points;
    }

    @Override public double score() { return correctAnswer.equals(studentAnswer) ? points : 0; }
}

class EssayQuestion implements ExamQuestion {
    private final String[] keywords;
    private final String studentAnswer;
    private final double points;

    EssayQuestion(String correctAnswer, String studentAnswer, double points) {
        this.keywords = correctAnswer.split(",");
        this.studentAnswer = studentAnswer.toLowerCase(Locale.ROOT);
        this.points = points;
    }

    @Override
    public double score() {
        int matches = 0;
        for (String keyword : keywords) {
            if (studentAnswer.contains(keyword.trim().toLowerCase(Locale.ROOT))) matches++;
        }
        if (matches >= 2) return points * 0.75;
        if (matches == 1) return points * 0.50;
        return 0;
    }
}

public class Main {
    private static List<String> tokens(String line) {
        Matcher matcher = Pattern.compile("\"([^\"]*)\"|(\\S+)").matcher(line);
        List<String> result = new ArrayList<>();
        while (matcher.find()) result.add(matcher.group(1) != null ? matcher.group(1) : matcher.group(2));
        return result;
    }

    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.US);
        Map<String, Function<List<String>, ExamQuestion>> questionTypes = new HashMap<>();
        questionTypes.put("MCQ", p -> new ExactMatchQuestion(p.get(2), p.get(3), Double.parseDouble(p.get(4))));
        questionTypes.put("TF", p -> new ExactMatchQuestion(p.get(2), p.get(3), Double.parseDouble(p.get(4))));
        questionTypes.put("ESSAY", p -> new EssayQuestion(p.get(2), p.get(3), Double.parseDouble(p.get(4))));

        var reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        int count = Integer.parseInt(reader.readLine().trim());
        double total = 0;
        for (int i = 0; i < count; i++) {
            List<String> parts = tokens(reader.readLine());
            Function<List<String>, ExamQuestion> constructor = questionTypes.get(parts.get(0));
            if (constructor == null) throw new IllegalArgumentException("Unknown question type: " + parts.get(0));
            double score = constructor.apply(parts).score();
            total += score;
            System.out.printf("%s: %.2f%n", parts.get(0), score);
        }
        System.out.printf("Total Score: %.2f%n", total);
    }
}