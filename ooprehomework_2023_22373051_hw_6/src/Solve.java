import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class Solve {
    private final ArrayList<Adventurer> adventurers;
    private final ArrayList<Dairy> dairies;
    private final Scanner scanner;
    private final Method method;

    public Solve(Scanner scanner) {
        this.scanner = scanner;
        this.adventurers = new ArrayList<>();
        this.dairies = new ArrayList<>();
        this.method = new Method();
    }

    public void solve() {
        int n = scanner.nextInt();
        for (int i = 0; i < n; ++i) {
            int type = scanner.nextInt();
            if (type == 1) {
                type1();
            } else if (type == 2) {
                type2();
            } else if (type == 3) {
                type3();
            } else if (type == 4) {
                type4();
            } else if (type == 5) {
                type5();
            } else if (type == 6) {
                type6();
            } else if (type == 7) {
                type7();
            } else if (type == 8) {
                type8();
            } else if (type == 9) {
                type9();
            } else if (type == 10) {
                type10();
            } else if (type == 11) {
                type11();
            } else if (type == 12) {
                type12();
            } else if (type == 13) {
                type13();
            } else if (type == 14) {
                type14();
            } else if (type == 15) {
                type15();
            } else if (type == 16) {
                type16();
            } else if (type == 17) {
                type17();
            } else if (type == 18) {
                type18();
            } else if (type == 19) {
                type19();
            } else if (type == 20) {
                type20();
            } else if (type == 21) {
                type21();
            }
        }
    }

    public Adventurer findAdventure(int advId) {
        for (Adventurer adventurer : adventurers) {
            if (adventurer.getId() == advId) {
                return adventurer;
            }
        }
        return null;
    }

    public void type1() {
        int advId = scanner.nextInt();
        String name = scanner.next();
        Adventurer ad = new Adventurer(advId, name);
        adventurers.add(ad);
    }

    public void type2() {
        int advId = scanner.nextInt();
        int botId = scanner.nextInt();
        String name = scanner.next();
        int capacity = scanner.nextInt();
        Bottle bo = new Bottle(botId, name, capacity);
        Adventurer adventurer = findAdventure(advId);
        adventurer.getBottleMap().put(botId, bo);

        long price = scanner.nextLong();
        String com = scanner.next();
        if (com.equals("RegularBottle")) {
            RegularBottle regularBottle = new RegularBottle(botId, name, capacity, price, com);
            adventurer.getRegularBottleMap().put(botId, regularBottle);
        } else if (com.equals("RecoverBottle")) {
            double ratio = scanner.nextDouble();
            RecoverBottle recoverBo = new RecoverBottle(botId, name, capacity, price, com, ratio);
            adventurer.getRecoverBottleMap().put(botId, recoverBo);
        } else if (com.equals("ReinforcedBottle")) {
            double ratio = scanner.nextDouble();
            ReinforcedBottle reBo = new ReinforcedBottle(botId, name, capacity, price, com, ratio);
            adventurer.getReinforcedBottleMap().put(botId, reBo);
        }
    }

    public void type3() {
        int advId = scanner.nextInt();
        int botId = scanner.nextInt();
        Adventurer adventurer = findAdventure(advId);
        adventurer.removeBottle(botId);
    }

    public void type4() {
        int advId = scanner.nextInt();
        int equId = scanner.nextInt();
        String name = scanner.next();
        int star = scanner.nextInt();
        Equipment eq = new Equipment(equId, name, star);
        Adventurer adventurer = findAdventure(advId);
        adventurer.getEquipmentMap().put(equId, eq);

        long price = scanner.nextLong();
        String com = scanner.next();
        if (com.equals("RegularEquipment")) {
            RegularEquipment regularEq = new RegularEquipment(equId, name, star, price, com);
            adventurer.getRegularEquipmentMap().put(equId, regularEq);
        } else if (com.equals("CritEquipment")) {
            int critical = scanner.nextInt();
            CritEquipment critEq = new CritEquipment(equId, name, star, price, com, critical);
            adventurer.getCritEquipmentMap().put(equId, critEq);
        } else if (com.equals("EpicEquipment")) {
            double ratio = scanner.nextDouble();
            EpicEquipment epicEquipment = new EpicEquipment(equId, name, star, price, com, ratio);
            adventurer.getEpicEquipmentMap().put(equId, epicEquipment);
        }
    }

    public void type5() {
        int advId = scanner.nextInt();
        int equId = scanner.nextInt();
        Adventurer adventurer = findAdventure(advId);
        adventurer.removeEquipment(equId);
    }

    public void type6() {
        int advId = scanner.nextInt();
        int equId = scanner.nextInt();
        Adventurer adventurer = findAdventure(advId);
        adventurer.addEquipmentStar(equId);
    }

    public void type7() {
        int advId = scanner.nextInt();
        int foodId = scanner.nextInt();
        String name = scanner.next();
        int power = scanner.nextInt();
        long price = scanner.nextLong();
        Food fo = new Food(foodId, name, power, price);
        Adventurer adventurer = findAdventure(advId);
        if (adventurer == null) {
            return;
        }
        adventurer.getFoodMap().put(foodId, fo);
    }

    public void type8() {
        int advId = scanner.nextInt();
        int foodId = scanner.nextInt();
        Adventurer adventurer = findAdventure(advId);
        Food tmp = adventurer.getFoodMap().remove(foodId);
        System.out.print(adventurer.getFoodMap().size() + " ");
        System.out.println(tmp.getName());
        adventurer.getOwnFoodMap().remove(foodId);
    }

    public void type9() {
        int advId = scanner.nextInt();
        int equId = scanner.nextInt();
        Adventurer adventurer = findAdventure(advId);
        if (adventurer == null) {
            return;
        }
        Equipment tmp = adventurer.getEquipmentMap().get(equId);
        for (Equipment equipment : adventurer.getOwnEquipment()) {
            if (tmp.getName().equals(equipment.getName())) {
                adventurer.getOwnEquipment().remove(equipment);
                break;
            }
        }
        adventurer.getOwnEquipment().add(tmp);
    }

    public void type10() {
        int advId = scanner.nextInt();
        int botId = scanner.nextInt();
        int cnt = 0;
        Adventurer adventurer = findAdventure(advId);
        if (adventurer == null) {
            return;
        }
        Bottle tmp1 = adventurer.getBottleMap().get(botId);
        for (Bottle tmp : adventurer.getOwnBottleMap().values()) {
            if (tmp.getName().equals(tmp1.getName())) {
                cnt++;
            }
        }
        if ((adventurer.getLevel() / 5 + 1) > cnt) {
            adventurer.getOwnBottleMap().put(botId, tmp1);
        }
    }

    public void type11() {
        int advId = scanner.nextInt();
        int foodId = scanner.nextInt();
        Adventurer adventurer = findAdventure(advId);
        if (adventurer == null) {
            return;
        }
        Food tmp = adventurer.getFoodMap().get(foodId);
        adventurer.getOwnFoodMap().put(foodId, tmp);
    }

    public void type12() {
        int advId = scanner.nextInt();
        String name = scanner.next();
        boolean match;
        Adventurer adventurer = findAdventure(advId);
        if (adventurer == null) {
            System.out.println("fail to use " + name);
            return;
        }
        match = adventurer.useOwnBottle(name);
        if (!match) {
            System.out.println("fail to use " + name);
        }
    }

    public void type13() {
        int advId = scanner.nextInt();
        String name = scanner.next();
        for (Adventurer adventurer : adventurers) {
            if (adventurer.getId() == advId) {
                Set<Integer> keys = adventurer.getOwnFoodMap().keySet();
                for (Integer key : keys) {
                    Food tmp = adventurer.getOwnFoodMap().get(key);
                    if (tmp.getName().equals(name)) {
                        System.out.print(tmp.getId() + " ");
                        adventurer.getMoreLevel(tmp.getPower());
                        adventurer.getFoodMap().remove(tmp.getId());
                        adventurer.getOwnFoodMap().remove(tmp.getId());
                        System.out.println(adventurer.getLevel());
                        return;
                    }
                }
            }
        }
        System.out.println("fail to eat " + name);
    }

    public void type14() { //还可以优化吧
        System.out.println("Enter Fight Mode");
        int m = scanner.nextInt();
        int k = scanner.nextInt();
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            names.add(scanner.next());
        }
        for (int i = 0; i < k; i++) {
            String line = scanner.next();
            ArrayList<String> res = method.process(line);
            String year = res.get(1);
            String month = res.get(2);
            String advName1 = res.get(3);
            switch (res.get(0)) {
                case "1":
                    String name = res.get(4);
                    if (method.judge(advName1, names)) {
                        method.judgeBot(name, adventurers, advName1, year, month, dairies);
                    } else {
                        System.out.println("Fight log error");
                    }
                    break;
                case "2":
                    String advName2 = res.get(4);
                    name = res.get(5);
                    if (method.judge(advName1, names) && method.judge(advName2, names)) {
                        method.judgeEqu(name, adventurers, advName1, advName2,
                                year, month, dairies);
                    } else {
                        System.out.println("Fight log error");
                    }
                    break;
                case "3":
                    name = res.get(4);
                    if (method.judge(advName1, names)) {
                        method.judgeEqu(name, adventurers, advName1, year, month, names, dairies);
                    } else {
                        System.out.println("Fight log error");
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void type15() {
        String line = scanner.next();
        ArrayList<String> res = method.process(line);
        String year = res.get(0);
        String month = res.get(1);
        boolean foundMatch;
        boolean match = false;
        for (Dairy dairy : dairies) {
            foundMatch = dairy.readDairy(year, month);
            if (foundMatch) {
                match = true;
            }
        }
        if (!match) {
            System.out.println("No Matched Log");
        }
    }

    public void type16() {
        int advId = scanner.nextInt();
        for (Adventurer adventurer : adventurers) {
            if (adventurer.getId() == advId) {
                adventurer.readAttackArrayList();
                return;
            }
        }
        System.out.println("No Matched Log");
    }

    public void type17() {
        int advId = scanner.nextInt();
        for (Adventurer adventurer : adventurers) {
            if (adventurer.getId() == advId) {
                adventurer.readAttackedArrayList();
                return;
            }
        }
        System.out.println("No Matched Log");
    }

    public void type18() {
        int advId1 = scanner.nextInt();
        int advId2 = scanner.nextInt();
        Adventurer adventurer1 = findAdventure(advId1);
        Adventurer adventurer2 = findAdventure(advId2);
        if (adventurer1 != null && adventurer2 != null) {
            if (!adventurer1.getEmployees().contains(adventurer2)) {
                adventurer1.getEmployees().add(adventurer2);
            }

        }
    }

    public void type19() {
        int advId = scanner.nextInt();
        Adventurer adventurer = findAdventure(advId);
        System.out.println(adventurer.count() + " " + adventurer.sumOfAllPrice());
    }

    public void type20() {
        int advId = scanner.nextInt();
        Adventurer adventurer = findAdventure(advId);
        System.out.println(adventurer.getMaxPrice());
    }

    public void type21() {
        int advId = scanner.nextInt();
        int comId = scanner.nextInt();
        Adventurer adventurer = findAdventure(advId);
        adventurer.findInWhichClass(comId);
    }
}
