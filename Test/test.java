package Test;

public class test {
    public static void main(String args[]) {
        demoSwordsMan();
        demoMagician();
    }
    static void demoSwordsMan() {
        var swordsMan = new SwordsMan();
        swordsMan.setName("John");
        swordsMan.setLevel(1);
        swordsMan.setBlood(200);
        showBlood(swordsMan);
        //System.out.printf("劍士: (%s, %d, %d)%n", swordsMan.getName(), swordsMan.getLevel(), swordsMan.getBlood());
    }
    static void demoMagician() {
        var magician = new Magician();
        magician.setName("Mars");
        magician.setLevel(100);
        magician.setBlood(10000);
        showBlood(magician);
        //System.out.printf("魔法師: (%s, %d, %d)%n", magician.getName(), magician.getLevel(), magician.getBlood());
    }
    static void showBlood(Role role) {
        System.out.printf("%s 血量 %d%n", role.getName(), role.getBlood());
    }
}
