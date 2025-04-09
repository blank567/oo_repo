import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class Solve {
    public static void main(String[] args) {
        ArrayList<ArrayList<String>> inputInfo = new ArrayList<>();
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine().trim());
        for (int i = 0; i < n; ++i) {
            String nextLine = sc.nextLine(); // 读取本行指令
            String[] strings = nextLine.trim().split(" +"); // 按空格对行进行分割
            inputInfo.add(new ArrayList<>(Arrays.asList(strings))); // 将指令分割后的各个部分存进容器中
        }
        for (int i = 0; i < n; ++i) {
            ArrayList<String> tmp = inputInfo.get(i);
            String cnt = tmp.get(0);
            switch (cnt) {
                case "1":
                    type1(tmp, adventurers);
                    break;
                case "2":
                    type2(tmp, adventurers);
                    break;
                case "3":
                    type3(tmp, adventurers);
                    break;
                case "4":
                    type4(tmp, adventurers);
                    break;
                case "5":
                    type5(tmp, adventurers);
                    break;
                case "6":
                    type6(tmp, adventurers);
                    break;
                case "7":
                    type7(tmp, adventurers);
                    break;
                case "8":
                    type8(tmp, adventurers);
                    break;
                case "9":
                    type9(tmp, adventurers);
                    break;
                case "10":
                    type10(tmp, adventurers);
                    break;
                case "11":
                    type11(tmp, adventurers);
                    break;
                case "12":
                    type12(tmp, adventurers);
                    break;
                case "13":
                    type13(tmp, adventurers);
                    break;
                default:

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
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
        bo.trash();
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
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
        eq.trash();
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
        fo.trash();
        fo.trash();
        fo.trash();
        fo.trash();
        fo.trash();
        fo.trash();
        fo.trash();
        fo.trash();
        fo.trash();
        fo.trash();
        fo.trash();
        fo.trash();
        fo.trash();
        fo.trash();
        fo.trash();
        fo.trash();
        fo.trash();
        fo.trash();
        fo.trash();
        fo.trash();
        fo.trash();
        fo.trash();
        fo.trash();
        fo.trash();
        fo.trash();
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
                adventurer.setOwnEquipment(tmp);
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
}
