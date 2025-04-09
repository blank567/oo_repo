import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class Solve {
    public static void main(String[] args) {
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        ArrayList<ArrayList<String>> inputInfo = new ArrayList<>();
        ArrayList<Dairy> dairies = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        int inputcnt = 0;
        while (sc.hasNext()) {
            String nextLine = sc.nextLine();
            String[] strings = nextLine.trim().split(" +");
            inputInfo.add(new ArrayList<>(Arrays.asList(strings)));
            inputcnt++;
        }
        for (int i = 0; i < inputcnt; ++i) {
            ArrayList<String> tmp = inputInfo.get(i);
            final String cnt = tmp.get(0);
            Map<String, Runnable> handlers = new HashMap<>();
            handlers.put("1", () -> type1(tmp, adventurers));
            handlers.put("2", () -> type2(tmp, adventurers));
            handlers.put("3", () -> type3(tmp, adventurers));
            handlers.put("4", () -> type4(tmp, adventurers));
            handlers.put("5", () -> type5(tmp, adventurers));
            handlers.put("6", () -> type6(tmp, adventurers));
            handlers.put("7", () -> type7(tmp, adventurers));
            handlers.put("8", () -> type8(tmp, adventurers));
            handlers.put("9", () -> type9(tmp, adventurers));
            handlers.put("10", () -> type10(tmp, adventurers));
            handlers.put("11", () -> type11(tmp, adventurers));
            handlers.put("12", () -> type12(tmp, adventurers));
            handlers.put("13", () -> type13(tmp, adventurers));
            handlers.put("15", () -> type15(tmp, dairies));
            handlers.put("16", () -> type16(tmp, adventurers));
            handlers.put("17", () -> type17(tmp, adventurers));
            Optional.ofNullable(handlers.get(cnt)).ifPresent(Runnable::run);
            if ("14".equals(cnt)) {
                i = i + type14(tmp, adventurers, inputInfo, i, dairies);
            }
        }
    }

    public static void type1(ArrayList<String> array, ArrayList<Adventurer> adventurers) {
        int advId = Integer.parseInt(array.get(1));
        String name = array.get(2);
        Adventurer ad = new Adventurer(advId, name);
        adventurers.add(ad);
    }

    public static void type2(ArrayList<String> array, ArrayList<Adventurer> adventurers) {
        int advId = Integer.parseInt(array.get(1));
        int botId = Integer.parseInt(array.get(2));
        String name = array.get(3);
        int capacity = Integer.parseInt(array.get(4));
        Bottle bo = new Bottle(botId, name, capacity);
        for (Adventurer adventurer : adventurers) {
            if (adventurer.getId() == advId) {
                adventurer.getBottleMap().put(botId, bo);
                break;
            }
        }
    }

    public static void type3(ArrayList<String> array, ArrayList<Adventurer> adventurers) {
        int advId = Integer.parseInt(array.get(1));
        int botId = Integer.parseInt(array.get(2));
        for (Adventurer adventurer : adventurers) {
            if (adventurer.getId() == advId) {
                Bottle tmp = adventurer.getBottleMap().remove(botId);
                adventurer.getOwnBottleMap().remove(botId);
                System.out.print(adventurer.getBottleMap().size() + " ");
                System.out.println(tmp.getName());
                break;
            }
        }
    }

    public static void type4(ArrayList<String> array, ArrayList<Adventurer> adventurers) {
        int advId = Integer.parseInt(array.get(1));
        int equId = Integer.parseInt(array.get(2));
        String name = array.get(3);
        int star = Integer.parseInt(array.get(4));
        Equipment eq = new Equipment(equId, name, star);
        for (Adventurer adventurer : adventurers) {
            if (adventurer.getId() == advId) {
                adventurer.getEquipmentMap().put(equId, eq);
                break;
            }
        }
    }

    public static void type5(ArrayList<String> array, ArrayList<Adventurer> adventurers) {
        int advId = Integer.parseInt(array.get(1));
        int equId = Integer.parseInt(array.get(2));
        for (Adventurer adventurer : adventurers) {
            if (adventurer.getId() == advId) {
                Equipment tmp = adventurer.getEquipmentMap().remove(equId);
                for (Equipment equipment : adventurer.getOwnEquipment()) {
                    if (equipment.getId() == equId) {
                        adventurer.getOwnEquipment().remove(equipment);
                        break;
                    }
                }
                System.out.print(adventurer.getEquipmentMap().size() + " ");
                System.out.println(tmp.getName());
                break;
            }
        }
    }

    public static void type6(ArrayList<String> array, ArrayList<Adventurer> adventurers) {
        int advId = Integer.parseInt(array.get(1));
        int equId = Integer.parseInt(array.get(2));
        for (Adventurer adventurer : adventurers) {
            if (adventurer.getId() == advId) {
                Equipment tmp = adventurer.getEquipmentMap().get(equId);
                tmp.getMoreStar();
                System.out.println(tmp.getName() + " " + tmp.getStar());
                break;
            }
        }
    }

    public static void type7(ArrayList<String> array, ArrayList<Adventurer> adventurers) {
        int advId = Integer.parseInt(array.get(1));
        int foodId = Integer.parseInt(array.get(2));
        String name = array.get(3);
        int power = Integer.parseInt(array.get(4));
        Food fo = new Food(foodId, name, power);
        for (Adventurer adventurer : adventurers) {
            if (adventurer.getId() == advId) {
                adventurer.getFoodMap().put(foodId, fo);
                break;
            }
        }
    }

    public static void type8(ArrayList<String> array, ArrayList<Adventurer> adventurers) {
        int advId = Integer.parseInt(array.get(1));
        int foodId = Integer.parseInt(array.get(2));
        for (Adventurer adventurer : adventurers) {
            if (adventurer.getId() == advId) {
                Food tmp = adventurer.getFoodMap().remove(foodId);
                System.out.print(adventurer.getFoodMap().size() + " ");
                System.out.println(tmp.getName());
                if (adventurer.getOwnFoodMap().get(foodId) != null) {
                    adventurer.getOwnFoodMap().remove(foodId);
                }
                break;
            }
        }
    }

    public static void type9(ArrayList<String> array, ArrayList<Adventurer> adventurers) {
        int advId = Integer.parseInt(array.get(1));
        int equId = Integer.parseInt(array.get(2));
        for (Adventurer adventurer : adventurers) {
            if (adventurer.getId() == advId) {
                Equipment tmp = adventurer.getEquipmentMap().get(equId);
                for (Equipment equipment : adventurer.getOwnEquipment()) {
                    if (tmp.getName().equals(equipment.getName())) {
                        adventurer.getOwnEquipment().remove(equipment);
                        break;
                    }
                }
                adventurer.getOwnEquipment().add(tmp);
                break;
            }
        }
    }

    public static void type10(ArrayList<String> array, ArrayList<Adventurer> adventurers) {
        int advId = Integer.parseInt(array.get(1));
        int botId = Integer.parseInt(array.get(2));
        int cnt = 0;
        for (Adventurer adventurer : adventurers) {
            if (adventurer.getId() == advId) {
                Bottle tmp1 = adventurer.getBottleMap().get(botId);
                for (Bottle tmp : adventurer.getOwnBottleMap().values()) {
                    if (tmp.getName().equals(tmp1.getName())) {
                        cnt++;
                    }
                }
                if ((adventurer.getLevel() / 5 + 1) > cnt) {
                    adventurer.getOwnBottleMap().put(botId, tmp1);
                }
                break;
            }
        }
    }

    public static void type11(ArrayList<String> array, ArrayList<Adventurer> adventurers) {
        int advId = Integer.parseInt(array.get(1));
        int foodId = Integer.parseInt(array.get(2));
        for (Adventurer adventurer : adventurers) {
            if (adventurer.getId() == advId) {
                Food tmp = adventurer.getFoodMap().get(foodId);
                adventurer.getOwnFoodMap().put(foodId, tmp);
                break;
            }
        }
    }

    public static void type12(ArrayList<String> array, ArrayList<Adventurer> adventurers) {
        int advId = Integer.parseInt(array.get(1));
        String name = array.get(2);
        int flag = 0;
        for (Adventurer adventurer : adventurers) {
            if (adventurer.getId() == advId) {
                TreeMap<Integer, Bottle> sortedMap = new TreeMap<>(adventurer.getOwnBottleMap());
                for (Map.Entry<Integer, Bottle> entry : sortedMap.entrySet()) {
                    Integer key = entry.getKey();
                    Bottle tmp = sortedMap.get(key);
                    if (tmp.getName().equals(name)) {
                        flag = 1;
                        System.out.print(tmp.getID() + " ");
                        if (tmp.getCapacity() == 0) {
                            adventurer.getBottleMap().remove(tmp.getID());
                            adventurer.getOwnBottleMap().remove(tmp.getID());
                        } else {
                            adventurer.getMoreHitPoint(tmp.getCapacity());
                            adventurer.getOwnBottleMap().get(tmp.getID()).setCapacity(0);
                        }
                        System.out.println(adventurer.getHitPoint());
                        break;
                    }
                }
                break;
            }
        }
        if (flag == 0) {
            System.out.println("fail to use " + name);
        }
    }

    public static void type13(ArrayList<String> array, ArrayList<Adventurer> adventurers) {
        int advId = Integer.parseInt(array.get(1));
        String name = array.get(2);
        int flag = 0;
        for (Adventurer adventurer : adventurers) {
            if (adventurer.getId() == advId) {
                Set<Integer> keys = adventurer.getOwnFoodMap().keySet();
                for (Integer key : keys) {
                    Food tmp = adventurer.getOwnFoodMap().get(key);
                    if (tmp.getName().equals(name)) {
                        flag = 1;
                        System.out.print(tmp.getId() + " ");
                        adventurer.getMoreLevel(tmp.getPower());
                        adventurer.getFoodMap().remove(tmp.getId());
                        adventurer.getOwnFoodMap().remove(tmp.getId());
                        System.out.println(adventurer.getLevel());
                        break;
                    }
                }
                break;
            }
        }
        if (flag == 0) {
            System.out.println("fail to eat " + name);
        }
    }

    public static int type14(ArrayList<String> array, ArrayList<Adventurer> adventurers,
                             ArrayList<ArrayList<String>> inputInfo,
                             int j, ArrayList<Dairy> dairies) {
        System.out.println("Enter Fight Mode");
        int m = Integer.parseInt(array.get(1));
        int k = Integer.parseInt(array.get(2));
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            names.add(array.get(i + 3));
        }
        for (int i = 0; i < k; i++) {
            String line = inputInfo.get(j + i + 1).get(0);
            ArrayList<String> res = Method.process(line);
            if (res.get(0).equals("1")) {
                String year = res.get(1);
                String month = res.get(2);
                String advName1 = res.get(3);
                String name = res.get(4);
                if (Method.judge(advName1, names)) {
                    Method.judgeBot(name, adventurers, advName1, dairies, year, month);
                } else {
                    System.out.println("Fight log error");
                }
            } else if (res.get(0).equals("2")) {
                String year = res.get(1);
                String month = res.get(2);
                String advName1 = res.get(3);
                String advName2 = res.get(4);
                String name = res.get(5);
                if (Method.judge(advName1, names) && Method.judge(advName2, names)) {
                    Method.judgeEqu(name, adventurers, advName1, advName2, year, month, dairies);
                } else {
                    System.out.println("Fight log error");
                }
            } else if (res.get(0).equals("3")) {
                String year = res.get(1);
                String month = res.get(2);
                String advName1 = res.get(3);
                String name = res.get(4);
                if (Method.judge(advName1, names)) {
                    Method.judgeEqu(name, adventurers, advName1, year, month, names, dairies);
                } else {
                    System.out.println("Fight log error");
                }
            }
        }
        return k;
    }

    public static void type15(ArrayList<String> array, ArrayList<Dairy> dairies) {
        String line = array.get(1);
        ArrayList<String> res = Method.process(line);
        String year = res.get(0);
        String month = res.get(1);
        boolean foundMatch = false;
        for (Dairy dairy : dairies) {
            if (dairy.getYear().equals(year) && dairy.getMonth().equals(month)) {
                foundMatch = true;
                String output = year + "/" + month + " " + dairy.getAdvName1();

                if (dairy.getAdvName2() == null) {
                    output += (dairy.getType() == 1) ? " used " : " AOE-attacked with ";
                    output += dairy.getName();
                } else {
                    output += " attacked " + dairy.getAdvName2() + " with " + dairy.getName();
                }

                System.out.println(output);
            }
        }

        if (!foundMatch) {
            System.out.println("No Matched Log");
        }
    }

    public static void type16(ArrayList<String> array, ArrayList<Adventurer> adventurers) {
        int advId = Integer.parseInt(array.get(1));
        for (Adventurer adventurer : adventurers) {
            if (adventurer.getId() == advId) {
                adventurer.readAttackArrayList();
                return;
            }
        }
        System.out.println("No Matched Log");
    }

    public static void type17(ArrayList<String> array, ArrayList<Adventurer> adventurers) {
        int advId = Integer.parseInt(array.get(1));
        for (Adventurer adventurer : adventurers) {
            if (adventurer.getId() == advId) {
                adventurer.readAttackedArrayList();
                return;
            }
        }
        System.out.println("No Matched Log");
    }
}
