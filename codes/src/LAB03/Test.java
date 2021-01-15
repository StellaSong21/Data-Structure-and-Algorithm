package LAB03;

public class Test {
    public static void main(String[] args){
        int n = 4;
        int d = 3;
        System.out.println((int)Math.floor(Math.log(n * (d - 1) + 1)/Math.log(d)));


        System.out.println((int)Math.ceil(Math.log(n * (d - 1) + 1)/Math.log(d) - 1));
    }
}
