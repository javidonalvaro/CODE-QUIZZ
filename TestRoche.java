import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class TestRoche {

    private static String FILE_URL = "./input.txt";
    private static Integer MAX_ALLOWED = 4;
    private static Integer MAX_DIFFERENCE = 300000;


    static List<ParsedValue> retrievedLines;
    static Map<String, Analysis> analysis;

    public static void main(String[] args) {
        readFile();
        validateReadedLines();
    }

    private static void validateReadedLines() {

        analysis = new HashMap<>();

        for (ParsedValue pv : retrievedLines) {
            if (!pv.status.equals("SUCCESS"))
                if (analysis.get(pv.ip) == null) {
                    Analysis a = new Analysis();
                    a.ip = pv.ip;
                    a.status = pv.status;
                    a.time = pv.time;
                    a.user = pv.user;
                    a.counter = 1;
                    analysis.put(a.ip, a);
                } else {
                    Analysis a = analysis.get(pv.ip);
                    if ((a.time - pv.time) >= MAX_DIFFERENCE) {
                        a.counter = 0;
                    }
                    if ((a.counter > MAX_ALLOWED) && ((a.time - pv.time) < MAX_DIFFERENCE)) {
                        System.out.println(a.ip);
                    }
                    a.counter = a.counter + 1;
                    a.time = pv.time;
                }
        }
    }

    private static void readFile() {
        retrievedLines = new ArrayList<>();
        try {
            // read input file
            FileReader fr = new FileReader(new File(FILE_URL));
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                ParsedValue pv = parseString(line);
                //System.out.println(pv);
                retrievedLines.add(pv);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ParsedValue parseString(String input) {
        StringTokenizer tokenizer = new StringTokenizer(input,",");
        ParsedValue pv = new ParsedValue();
        pv.ip = tokenizer.nextToken();
        pv.time = Integer.parseInt(tokenizer.nextToken());
        pv.status = tokenizer.nextToken();
        pv.user = tokenizer.nextToken();

        return pv;
    }
}