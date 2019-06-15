package fishmaple.utils;

import fishmaple.DAO.ResourceMapper;
import fishmaple.Objects.FileObject;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class  FileUtil {
    private static Logger log= LoggerFactory.getLogger(FileUtil.class);

    /**
     * 写入文件
     * @param filePath
     * @param content
     * @return
     */
    public static boolean writeStr(String filePath,String content) {
        FileWriter fw=null;
        try {
            fw = new FileWriter(filePath);
            fw.write(content);
            fw.close();
            return true;
        } catch (IOException e) {
            log.error("文件读取发生问题！");
            return false;
        }finally{
        }
    }

    /**
     * 读取文件文本内容
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    public static String getStr(String filePath) throws FileNotFoundException {
            return getStr(new FileInputStream(filePath),"<br>");
    }
    public static String getStr4Html(String filePath) throws FileNotFoundException {
        return getStr4Html(new FileInputStream(filePath));
    }
    public static String getStr4Html(InputStream in)  {
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String s;
            while((s=reader.readLine()) != null){
                s=s.replaceAll(" ","&ensp;");
                sb.append(s+"<br>");
            }
            reader.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static String getStr(InputStream in,String Enter)  {
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String s;
            while((s=reader.readLine()) != null){
                sb.append(s+Enter);
            }
            reader.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    /**
     * 获取父目录
     * @param fileLoad  原始目录
     * @param num 指定父目录层级
     * @return
     */
    public static String getParents(String fileLoad,int num){
        if(num==0)
            return fileLoad;
        else {
            File file = new File(fileLoad);
            return getParents(file.getParent(),num-1);
        }
    }

    public static String  deleteFiles(String filePath){
        File file=new File(filePath);
        if(!file.exists()){
            return "文件不存在或已删除";
        } else{
            deleteFiles(file);
            return "ok";
        }
    }

    private static void deleteFiles(File file){
        File [] files = file.listFiles();//获取包含file对象对应的子目录或者文件
        for(int i =0;i<files.length;i++){
            if(files[i].isFile()){//判断是否为文件
                files[i].delete();//如果是就删除
            }else{
                deleteFiles(files[i]);//否则重新递归到方法中
            }
        }
        file.delete();//最后删除该目录中所有文件后就删除该目录
    }
    /**
     *  获取文件目录下文件
     * */
    public static List<FileObject> getFileList(String filePath){
        File file = new File(filePath);
        if(!file.exists()) {
            file.mkdirs();
        }
            List<FileObject> list= new ArrayList<>();
            for (File temp : file.listFiles()) {
                if (temp.isDirectory()) {
                    list.add(new FileObject(temp.getName(),TimeDate.timestamp2time(temp.lastModified(),1),
                            "",0));
                }else{
                    list.add(new FileObject(temp.getName(),TimeDate.timestamp2time(temp.lastModified(),1),
                            getSuffix(temp.getName()),1));
                }
            }
            return list;

    }

    /**
     * 文件上传
     * @param file  byte[]文件流
     * @param fileName
     * @param filePath
     */
    public static void uploadFile(byte[] file, String fileName,String filePath) {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath+fileName);
            out.write(file);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{

        }

    }

    public static long getFileSize(String filePath){
        File file = new File(filePath);
        if(file.exists()){
            return getFileSize(file);
        }else{
            return 0;
        }
    }
    /**
     * 递归获取文件大小，单位字节
     * */
    public static long getFileSize(File file){
        //定义一个接受文件的变量
        long number = 0;
        //把该路径下一级的文件存放下来
        File[] files = file.listFiles();
        //isFile()方法是判断file是否是一个文件，如果是就返回该文件的大小
        if (file.isFile()) {
            return file.length();
        }
        //判断该文件是否为空，不为空进行遍历
        if (file != null) {
            //快速遍历的方法遍历file集合
            for (File fileTmp : files) {
                number += getFileSize(fileTmp);
            }
        }
        //最后返回文件的总大小
        return number;
    }

    public static String getSuffix(String fileName){
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return suffix;
    }

    public static String readNIO2 (String str)throws IOException {


        FileInputStream fin = new FileInputStream(str);

        // 获取通道
        FileChannel fc = fin.getChannel();

        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        StringBuffer s=new StringBuffer();
        byte[] bs=new byte[1024];
        // 读取数据到缓冲区
        while(fc.read(buffer)>0){
            buffer.flip();

            for (int i=0;buffer.remaining() > 0;i++) {

                byte b = buffer.get();
                bs[i]=b;

                //System.out.print(((char) b));

            }
            s.append(new String(bs));
            buffer.clear();
        }




       fin.close();

        return s.toString();




    }

    public static String readNIO(String pathName) {
        FileInputStream fin = null;
        String str="";
        try {
            fin = new FileInputStream(new File(pathName));
            FileChannel channel = fin.getChannel();

            int capacity = 10000;// 字节
            ByteBuffer bf = ByteBuffer.allocate(capacity);
            System.out.println("限制是：" + bf.limit() + ",容量是：" + bf.capacity() + " ,位置是：" + bf.position());
            int length = -1;

            while ((length = channel.read(bf)) != -1) {

                /*
                 * 注意，读取后，将位置置为0，将limit置为容量, 以备下次读入到字节缓冲中，从0开始存储
                 */
                bf.clear();
                byte[] bytes = bf.array();
                System.out.print(length+"st");

                str = str+new String(bytes);
                //System.out.print(str);
                //System.out.println(bytes[2]+bytes[3]);
                //str=new String(bytes);
                //System.out.write(bytes, 0, length);

                //System.out.println("end................");

                //System.out.println("限制是：" + bf.limit() + "容量是：" + bf.capacity() + "位置是：" + bf.position());

            }

            channel.close();
            return str;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return str;
        }
    }

}
