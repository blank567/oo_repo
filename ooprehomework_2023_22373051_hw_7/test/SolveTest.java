import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class SolveTest {
    private Solve solve;

    @Test
    public void solve() {
        String sampleInput = "131\n" +
                "1 712257 tv7Cw\n" +
                "1 951293 d!gbC\n" +
                "1 274389 2RD!r15L\n" +
                "4 274389 66053 9BnZaPY 3 2989001 RegularEquipment\n" +
                "4 712257 317886 9BnZaPY 1 2715170 EpicEquipment 0.931\n" +
                "2 951293 551355 k?vGm 87 2283453 ReinforcedBottle 0.883\n" +
                "4 712257 126907 E$XWhQ8% 4 2147199 RegularEquipment\n" +
                "7 951293 942661 *St32mTp 4 2258497\n" +
                "12 951293 yny*NCln\n" +
                "4 712257 519060 9BnZaPY 3 2331470 CritEquipment 100\n" +
                "14 3 8 tv7Cw 2RD!r15L d!gbC\n" +
                "2000/01-2RD!r15L@tv7Cw-GK1dTg$SR\n" +
                "2000/01-tv7Cw-yny*NCln\n" +
                "2000/01-d!gbC-yny*NCln\n" +
                "2000/02-2RD!r15L@d!gbC-9BnZaPY\n" +
                "2000/02-d!gbC-k?vGm\n" +
                "2000/02-d!gbC-k?vGm\n" +
                "2000/03-d!gbC-^RYFbGq\n" +
                "2000/04-2RD!r15L@#-E$XWhQ8%\n" +
                "7 274389 553176 Zor68x 4 2896629\n" +
                "22 274389\n" +
                "8 951293 942661\n" +
                "7 274389 877931 YBkK/zx4 1 2800275\n" +
                "4 951293 945396 E$XWhQ8% 2 2872464 CritEquipment 53\n" +
                "4 712257 713989 9BnZaPY 3 2453780 CritEquipment 99\n" +
                "2 951293 60570 k?vGm 52 2282382 ReinforcedBottle 0.306\n" +
                "2 274389 868021 k?vGm 93 2303029 RecoverBottle 0.911\n" +
                "6 951293 945396\n" +
                "2 712257 269753 k?vGm 95 2324582 ReinforcedBottle 0.220\n" +
                "4 274389 476697 9BnZaPY 5 2195457 RegularEquipment\n" +
                "11 274389 877931\n" +
                "7 274389 234412 Zor68x 1 2987607\n" +
                "7 951293 291285 *St32mTp 3 2690887\n" +
                "2 274389 760488 yny*NCln 63 2270655 RecoverBottle 0.445\n" +
                "13 274389 Zor68x\n" +
                "6 274389 66053\n" +
                "4 712257 449362 GK1dTg$SR 3 2749263 CritEquipment 59\n" +
                "13 951293 YBkK/zx4\n" +
                "8 951293 291285\n" +
                "2 712257 994726 k?vGm 66 2726674 ReinforcedBottle 0.786\n" +
                "2 951293 291297 ^RYFbGq 70 2437824 RegularBottle\n" +
                "2 712257 169236 k?vGm 76 2681852 RecoverBottle 0.977\n" +
                "2 274389 679298 yny*NCln 41 2495386 RegularBottle\n" +
                "7 274389 690967 *St32mTp 4 2486730\n" +
                "12 274389 ^RYFbGq\n" +
                "4 951293 210438 9BnZaPY 3 2051918 RegularEquipment\n" +
                "2 712257 155449 yny*NCln 16 2022089 ReinforcedBottle 0.985\n" +
                "4 951293 566499 E$XWhQ8% 1 2359481 RegularEquipment\n" +
                "3 951293 60570\n" +
                "10 712257 169236\n" +
                "9 712257 713989\n" +
                "4 712257 123253 E$XWhQ8% 5 2034265 CritEquipment 93\n" +
                "22 274389\n" +
                "4 712257 524181 E$XWhQ8% 1 2117547 CritEquipment 55\n" +
                "2 274389 79686 yny*NCln 70 2515666 RecoverBottle 0.260\n" +
                "2 274389 266362 ^RYFbGq 74 2103841 ReinforcedBottle 0.843\n" +
                "7 274389 830736 YBkK/zx4 3 2581210\n" +
                "7 951293 270873 YBkK/zx4 2 2206731\n" +
                "12 712257 k?vGm\n" +
                "19 951293\n" +
                "7 951293 817059 *St32mTp 4 2428346\n" +
                "4 951293 739103 GK1dTg$SR 3 2678121 CritEquipment 74\n" +
                "2 951293 713546 yny*NCln 35 2300430 ReinforcedBottle 0.648\n" +
                "21 274389 868021\n" +
                "2 712257 28492 ^RYFbGq 32 2069815 RecoverBottle 0.220\n" +
                "12 951293 ^RYFbGq\n" +
                "7 712257 664190 YBkK/zx4 5 2753414\n" +
                "4 274389 951086 9BnZaPY 5 2608442 EpicEquipment 0.759\n" +
                "18 712257 951293\n" +
                "2 712257 752362 ^RYFbGq 54 2765124 ReinforcedBottle 0.427\n" +
                "4 951293 377391 GK1dTg$SR 3 2625909 CritEquipment 98\n" +
                "2 951293 248569 yny*NCln 86 2795851 RegularBottle\n" +
                "4 274389 523454 E$XWhQ8% 3 2869491 RegularEquipment\n" +
                "2 951293 556105 yny*NCln 45 2581571 RegularBottle\n" +
                "4 274389 332043 9BnZaPY 3 2889216 EpicEquipment 0.125\n" +
                "18 274389 712257\n" +
                "2 951293 345944 yny*NCln 30 2862252 RecoverBottle 0.178\n" +
                "11 951293 817059\n" +
                "4 712257 689739 E$XWhQ8% 3 2130839 EpicEquipment 0.339\n" +
                "4 951293 431694 E$XWhQ8% 4 2125569 RegularEquipment\n" +
                "2 951293 920237 ^RYFbGq 49 2816404 ReinforcedBottle 0.702\n" +
                "20 274389\n" +
                "6 951293 431694\n" +
                "22 712257\n" +
                "2 274389 856809 k?vGm 23 2004691 RecoverBottle 0.015\n" +
                "4 274389 374503 E$XWhQ8% 1 2030482 RegularEquipment\n" +
                "18 274389 951293\n" +
                "2 274389 217332 ^RYFbGq 80 2967608 ReinforcedBottle 0.614\n" +
                "2 274389 673986 ^RYFbGq 61 2394620 RecoverBottle 0.572\n" +
                "7 951293 830153 *St32mTp 3 2628977\n" +
                "7 951293 410093 *St32mTp 1 2816764\n" +
                "2 712257 380479 k?vGm 28 2350657 ReinforcedBottle 0.287\n" +
                "5 274389 332043\n" +
                "7 951293 997267 Zor68x 3 2755925\n" +
                "2 951293 144024 yny*NCln 33 2034382 RecoverBottle 0.818\n" +
                "2 712257 263446 yny*NCln 20 2012227 ReinforcedBottle 0.342\n" +
                "12 274389 k?vGm\n" +
                "6 951293 945396\n" +
                "7 951293 720497 *St32mTp 5 2115227\n" +
                "7 951293 401330 YBkK/zx4 3 2089473\n" +
                "3 712257 994726\n" +
                "4 274389 287778 GK1dTg$SR 5 2475181 RegularEquipment\n" +
                "7 712257 360434 Zor68x 4 2348672\n" +
                "2 712257 549615 yny*NCln 97 2220164 ReinforcedBottle 0.968\n" +
                "7 274389 884183 *St32mTp 2 2037011\n" +
                "12 274389 ^RYFbGq\n" +
                "5 274389 523454\n" +
                "4 274389 978636 E$XWhQ8% 3 2526770 RegularEquipment\n" +
                "6 274389 951086\n" +
                "7 274389 650498 YBkK/zx4 1 2726080\n" +
                "2 712257 680297 yny*NCln 96 2368349 RegularBottle\n" +
                "2 274389 30688 k?vGm 98 2164401 RecoverBottle 0.308\n" +
                "2 274389 839006 yny*NCln 13 2921753 ReinforcedBottle 0.843\n" +
                "4 951293 817281 E$XWhQ8% 3 2625096 CritEquipment 56\n" +
                "12 951293 ^RYFbGq\n" +
                "4 712257 2087 E$XWhQ8% 3 2861846 CritEquipment 77\n" +
                "20 274389\n" +
                "2 274389 874459 yny*NCln 22 2932681 RegularBottle\n" +
                "14 3 5 tv7Cw d!gbC 2RD!r15L\n" +
                "2001/05-d!gbC-yny*NCln\n" +
                "2001/05-tv7Cw@2RD!r15L-9BnZaPY\n" +
                "2001/05-d!gbC@#-GK1dTg$SR\n" +
                "2001/05-tv7Cw-k?vGm\n" +
                "2001/05-2RD!r15L@d!gbC-9BnZaPY\n" +
                "7 712257 747511 YBkK/zx4 4 2870635\n" +
                "4 951293 218686 E$XWhQ8% 3 2212016 RegularEquipment\n" +
                "12 951293 k?vGm\n" +
                "7 274389 631480 *St32mTp 3 2508694\n" +
                "12 712257 k?vGm\n" +
                "11 951293 401330\n" +
                "7 951293 643254 YBkK/zx4 4 2104909\n" +
                "4 274389 402108 9BnZaPY 3 2804288 EpicEquipment 0.301\n" +
                "12 274389 yny*NCln\n" +
                "12 274389 k?vGm\n" +
                "2 274389 936867 k?vGm 56 2408309 ReinforcedBottle 0.757\n" +
                "23 951293 248933 ^RYFbGq RecoverBottle 0.375\n" +
                "2 274389 824871 ^RYFbGq 39 2032971 RecoverBottle 0.803\n" +
                "12 274389 ^RYFbGq\n" +
                "4 274389 960916 E$XWhQ8% 2 2837304 RegularEquipment\n" +
                "7 274389 797234 *St32mTp 4 2444589\n" +
                "2 712257 445659 ^RYFbGq 42 2920816 RecoverBottle 0.185\n" +
                "8 274389 234412\n" +
                "12 951293 k?vGm\n" +
                "20 712257\n";
        System.setIn(new ByteArrayInputStream(sampleInput.getBytes()));
        solve = new Solve(new Scanner(System.in));

        solve.solve();
    }

    @Test
    public void type1() {
    }

    @Test
    public void type2() {
    }

    @Test
    public void type3() {
    }

    @Test
    public void type4() {
    }

    @Test
    public void type5() {
    }

    @Test
    public void type6() {
    }

    @Test
    public void type7() {
    }

    @Test
    public void type8() {
    }

    @Test
    public void type9() {
    }

    @Test
    public void type10() {
    }

    @Test
    public void type11() {
    }

    @Test
    public void type12() {
    }

    @Test
    public void type13() {
    }

    @Test
    public void type14() {
    }

    @Test
    public void type15() {
    }

    @Test
    public void type16() {
    }

    @Test
    public void type17() {
    }

    @Test
    public void type18() {
    }

    @Test
    public void type19() {
    }

    @Test
    public void type20() {
    }

    @Test
    public void type21() {
    }
}