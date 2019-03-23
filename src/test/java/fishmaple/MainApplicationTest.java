package fishmaple;

import fishmaple.DAO.BlMapper;
import fishmaple.DAO.CanMapper;
import fishmaple.DAO.IssueMapper;
import fishmaple.DTO.IssueTest;
import fishmaple.thirdPart.bilibiliWebWorm.BlUserObject;
import fishmaple.thirdPart.bilibiliWebWorm.BlWormService;
import fishmaple.utils.JedisUtil;


import fishmaple.utils.TimeDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.util.Objects.hash;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class MainApplicationTest {

    @Autowired
    CanMapper canMapper;

    //ArrayDeque<Integer> a=new ArrayDeque<>();

    @Test
    public void v(){
        long q=System.currentTimeMillis();
        //List<IssueTest> list= issueMapper.findAllIssueTest();
        System.out.print(System.currentTimeMillis()-q);
    }
    /*@Test
    public void printAllBeans() {
        SpringContextHolder s=new SpringContextHolder();
        s.setApplicationContext(ctx);
        s.printAllBeans(null);
    }

    @Test
    public void printAlBeans() {
        System.out.println(hash("key"));
        System.out.println(hash("key") & 0x7FFFFFFF);
        System.out.println((hash("key") & 0x7FFFFFFF) % 112);
        System.out.println(hash("key") % 112);
    }






    public static void main(String args[]){
        TTT task = new TTT();

        MyThread1 thread1 = new MyThread1(task);
        thread1.start();

        MyThread2 thread2 = new MyThread2(task);
        thread2.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         }

    @Test
    public void contextLoads() throws InterruptedException {

        Map<String,String> m= new HashMap<>();
       m.put("a","111");
       m.put("c","1111");
       m.put("b","1");
       for(Map.Entry<String,String> entry:m.entrySet()){
           System.out.println(entry.getKey()+" "+entry.getValue());
       }
        /* BlUserObject blUserObject=new BlUserObject();
        Jedis jedis= JedisUtil.getJedis();
        Long i=1L;
       while(true){
           jedis.set("blUserId",i.toString());
           blUserObject=blWormService.getBlUser(i);
            blMapper.save(i,blUserObject.getSex(),blUserObject.getFans()
            ,blUserObject.getName(),blUserObject.getFace(),blUserObject.getRank());
           Thread.sleep(500);
            i++;
       }
    }
*/
}
