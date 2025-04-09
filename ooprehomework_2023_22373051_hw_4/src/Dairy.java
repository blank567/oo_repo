public class Dairy {
    private String year;
    private String month;
    private int type;
    private String advName1;
    private String advName2;
    private String name;

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

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public int getType() {
        return type;
    }

    public String getAdvName2() {
        return advName2;
    }

    public String getAdvName1() {
        return advName1;
    }

    public String getName() {
        return name;
    }
}
