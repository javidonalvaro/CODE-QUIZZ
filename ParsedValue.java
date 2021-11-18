public class ParsedValue {
    String ip;
    Integer time;
    String status;
    String user;

    public String toString() {
        return ip + " | " + time + " | " + status + " | " + user;
    }
}
