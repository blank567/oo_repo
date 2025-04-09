public class Attack {
    private final String year;
    private final String month;
    private String attackName;
    private final String equipmetName;

    public Attack(String year, String month, String attackName, String equipmetName) {
        this.year = year;
        this.month = month;
        this.attackName = attackName;
        this.equipmetName = equipmetName;
    }

    public Attack(String year, String month, String equipmetName) {
        this.year = year;
        this.month = month;
        this.equipmetName = equipmetName;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getAttackName() {
        return attackName;
    }

    public String getEquipmetName() {
        return equipmetName;
    }
}
