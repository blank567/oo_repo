public class Dairy {
    private final String year;
    private final String month;
    private int type;
    private final String advName1;
    private String advName2;
    private final String name;

    public Dairy(String year, String month, int type, String advName1, String name) {
        this.year = year;
        this.month = month;
        this.type = type;
        this.advName1 = advName1;
        this.name = name;
    }

    public Dairy(String year, String month, String advName1, String advName2, String name) {
        this.year = year;
        this.month = month;
        this.advName1 = advName1;
        this.advName2 = advName2;
        this.name = name;
    }

    public boolean readDairy(String year, String month) {
        boolean foundMatch = false;
        if (this.year.equals(year) && this.month.equals(month)) {
            foundMatch = true;
            String output = year + "/" + month + " " + advName1;
            if (advName2 == null) {
                output += (type == 1) ? " used " : " AOE-attacked with ";
                output += name;
            } else {
                output += " attacked " + advName2 + " with " + name;
            }
            System.out.println(output);
        }
        return foundMatch;
    }
}
