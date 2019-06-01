package fishmaple.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Stack;

public class TimeDate {
    /**
     * @param flag  2 ---:: 1 ---
     * */
    public static String timestamp2time(Long timestamp,int flag){
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
        if(flag==2){
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else if(flag==1){
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }else if(flag==0){
            sdf = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
        }
        return sdf.format(new Date(timestamp));
    }

    public static String getTimeStampNow(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        // 输出字符串
        return df.format(new Date());
    }

    public static Long getTimeNowToDb(){
        return System.currentTimeMillis()/1000;
    }


    public static void main(String args[]){
        Stack<Integer> s=new Stack<>();
        s.push(12);
        //System.ous.pop();

        /*ArrayDeque<Integer> a=new ArrayDeque<>();
        a.add(1);
        a.add(3);
        a.addLast(5);
        a.addFirst(7);
        System.out.println(a);
        a.remove();
        System.out.println(a);
        a.removeFirst();
        System.out.println(a);
        a.removeLast();
        System.out.println(a);
        int[] s=new  int[10];
        s[0]=12;s[1]=22;s[2]=32;
        int head=0; int tail=9;
        for(int q:s){
            System.out.print(q+" ");
        }
        s[head = (head - 1) & (s.length - 1)] = 8;
        System.out.println();
        for(int q:s){
            System.out.print(q+" ");
        }
        System.out.println(head);
        s[head = (head - 1) & (s.length - 1)] = 42;
        System.out.println();
        for(int q:s){
            System.out.print(q+" ");
        }
        System.out.println(head);*/
    }
}
