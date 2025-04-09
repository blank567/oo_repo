public class Attacked {
    private String year;
    private String month;
    private String attackedName;
    private String equipmetName;
    private int type;

    public Attacked(String year, String month, String attackedName, String equipmetName, int type) {
        this.year = year;
        this.month = month;
        this.attackedName = attackedName;
        this.equipmetName = equipmetName;
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getAttackedName() {
        return attackedName;
    }

    public String getEquipmetName() {
        return equipmetName;
    }

    public int getType() {
        return type;
    }
}
