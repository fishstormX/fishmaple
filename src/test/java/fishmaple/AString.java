package fishmaple;

public class AString {
    public static void main(String args[]){
        String a="ss";
        String b="ss";
        String q=new String ("ss");
        String c="s"+"s";
        String d="s";
        String e="s"+d;
        System.out.println(a==b);
                System.out.println(c==e);

        System.out.println(a==q);
        System.out.println(c==a);
    }
}
