import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Method {

    public ArrayList<String> process(String input) {
        ArrayList<String> res = new ArrayList<>();
        String pattern1 = "(\\d{4})/(\\d{2})-([^@]+)-(.+)";
        String pattern2 = "(\\d{4})/(\\d{2})-(.+)@([^#]+)-(.+)";
        String pattern3 = "(\\d{4})/(\\d{2})-(.+)@#-(.+)";
        String pattern4 = "(\\d{4})/(\\d{2})";
        Pattern regexPattern1 = Pattern.compile(pattern1);
        Pattern regexPattern2 = Pattern.compile(pattern2);
        Pattern regexPattern3 = Pattern.compile(pattern3);
        Pattern regexPattern4 = Pattern.compile(pattern4);
        Matcher matcher1 = regexPattern1.matcher(input);
        Matcher matcher2 = regexPattern2.matcher(input);
        Matcher matcher3 = regexPattern3.matcher(input);
        Matcher matcher4 = regexPattern4.matcher(input);
        if (matcher1.matches()) {
            res.add("1");
            res.add(matcher1.group(1));
            res.add(matcher1.group(2));
            res.add(matcher1.group(3));
            res.add(matcher1.group(4));
        } else if (matcher2.matches()) {
            res.add("2");
            res.add(matcher2.group(1));
            res.add(matcher2.group(2));
            res.add(matcher2.group(3));
            res.add(matcher2.group(4));
            res.add(matcher2.group(5));
        } else if (matcher3.matches()) {
            res.add("3");
            res.add(matcher3.group(1));
            res.add(matcher3.group(2));
            res.add(matcher3.group(3));
            res.add(matcher3.group(4));
        } else if (matcher4.matches()) {
            res.add(matcher4.group(1));
            res.add(matcher4.group(2));
        }
        return res;
    }

    public boolean judge(String name, ArrayList<String> names) {
        for (String tmp : names) {
            if (tmp.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void judgeBot(String name, ArrayList<Adventurer> adventurers, String advName,
                         String year, String month, ArrayList<Dairy> dairies) {
        boolean match = false;
        for (Adventurer adventurer : adventurers) {
            if (adventurer.getName().equals(advName)) {
                match = adventurer.useOwnBottle(name);
                break;
            }
        }
        if (!match) {
            System.out.println("Fight log error");
        } else {
            Dairy dairy = new Dairy(year, month, 1, advName, name);
            dairies.add(dairy);
        }
    }

    public void judgeEqu(String name, ArrayList<Adventurer> adventurers, String advName1,
                         String advName2, String year,
                         String month, ArrayList<Dairy> dairies) {
        for (Adventurer adventurer : adventurers) {
            if (adventurer.getName().equals(advName1)) {
                for (Equipment equipment : adventurer.getOwnEquipment()) {
                    if (equipment.getName().equals(name)) {
                        for (Adventurer adventurer1 : adventurers) {
                            if (adventurer1.getName().equals(advName2)) {
                                String type = adventurer.findInWhichEquipment(equipment.getId());
                                adventurer.equipmentAttack(type, adventurer1, equipment.getId());
                                System.out.print(adventurer1.getId() + " ");
                                System.out.println(adventurer1.getHitPoint());
                                Attack attack = new Attack(year, month, advName2, name);
                                adventurer.getAttacks().add(attack);
                                Attacked attacked = new Attacked(year, month, advName1, name, 1);
                                adventurer1.getAttackeds().add(attacked);
                                Dairy dairy = new Dairy(year, month, advName1, advName2, name);
                                dairies.add(dairy);
                                return;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Fight log error");
    }

    public void judgeEqu(String name, ArrayList<Adventurer> adventurers, String advName,
                         String year, String month, ArrayList<String> names,
                         ArrayList<Dairy> dairies) {
        int flag = 0;
        for (Adventurer adventurer : adventurers) {
            if (adventurer.getName().equals(advName)) {
                for (Equipment equipment : adventurer.getOwnEquipment()) {
                    if (equipment.getName().equals(name)) {
                        for (String tmp : names) {
                            for (Adventurer adventurer1 : adventurers) {
                                if (adventurer1.getName().equals(tmp) && !tmp.equals(advName)) {
                                    flag = 1;
                                    String tp = adventurer.findInWhichEquipment(equipment.getId());
                                    adventurer.equipmentAttack(tp, adventurer1, equipment.getId());
                                    System.out.print(adventurer1.getHitPoint() + " ");
                                    Attacked attacked = new Attacked(year, month, advName, name, 2);
                                    adventurer1.getAttackeds().add(attacked);
                                    break;
                                }
                            }
                        }
                        Dairy dairy = new Dairy(year, month, 2, advName, name);
                        dairies.add(dairy);
                        Attack attack = new Attack(year, month, name);
                        adventurer.getAttacks().add(attack);
                        System.out.println();
                        break;
                    }
                }
                break;
            }
        }
        if (flag == 0) {
            System.out.println("Fight log error");
        }
    }
}
