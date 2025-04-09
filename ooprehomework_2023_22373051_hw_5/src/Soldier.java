public class Soldier {
    private String name;
    private String incantation;

    public Soldier(String name, String incantation) {
        this.name = name;
        this.incantation = incantation;
    }

    public void cutIncantation(int a, int b) {
        if (a > b || a > incantation.length()) {
            incantation = "";
        } else if (b >= incantation.length()) {
            incantation = incantation.substring(a);
        } else {
            incantation = incantation.substring(a, b + 1);
        }
    }

    //  2 means "to" , you will see it in the os codes  next semester
    public void appendStr2Incantation(String str) {
        incantation += str;
    }

    public Soldier cloneSoldier() {
        return new Soldier(this.name, this.incantation);
    }

    public boolean notQualifiedByStandard(int standard) {
        int head = incantation.indexOf('@');
        int tail = head + 1;
        int totalCount = 0;
        while (tail < incantation.length()) {
            if (incantation.charAt(tail) != '@') {
                tail++;
                continue;
            }
            int count = 0;
            for (int i = head; i <= tail; i++) {
                if (incantation.charAt(i) >= 'A' && incantation.charAt(i) <= 'Z') {
                    count++;
                }
                if (incantation.charAt(i) >= 'a' && incantation.charAt(i) <= 'z') {
                    count--;
                }
            }
            if (count >= 0) {
                totalCount++;
            }
            head = tail;
            tail++;
        }
        return totalCount < standard;
    }

    public boolean hasString(String str) {
        return incantation.contains(str);
    }

    public boolean equal(Soldier soldier) {
        return this.name.equals(soldier.name) && this.incantation.equals(soldier.incantation);
    }
}

