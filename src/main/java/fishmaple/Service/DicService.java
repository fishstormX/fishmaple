package fishmaple.Service;

import fishmaple.DAO.DictionaryMapper;
import fishmaple.DTO.Dictionary;
import fishmaple.Objects.BaiduSearchAssObject;
import fishmaple.Objects.DictionaryObject;
import fishmaple.thirdPart.baiduWebWorm.BaiduWormService;
import fishmaple.utils.EncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@Service
public class DicService {
    @Autowired
    DictionaryMapper dictionaryMapper;

    @Autowired
    BaiduWormService baiduWormService;
    public List<BaiduSearchAssObject> getKeyAss(String key) throws IOException {
        key=key.replaceAll(" ","");
        key=key.replaceAll("%20","");
        String[] s=baiduWormService.getAssociational(key);
        List<BaiduSearchAssObject> list=new ArrayList<>();
        for(String temp:s){
            if(s.length<15){
                BaiduSearchAssObject object=new BaiduSearchAssObject();
                object.setValue(temp);
                list.add(object);
            }
        }
        return list;
    }

    public List<DictionaryObject> setRandomColor(List<DictionaryObject> list){
        for(DictionaryObject dic:list){
            dic.setFontColor("#"+getDandomHex());
        }
        return list;
    }

    public Dictionary getDicByName(String key){
        Dictionary dic=dictionaryMapper.getDictionaryByKey(key);
        return dic;
    }

    public void setEntry(String key,String value,String author){
        value= EncoderUtil.htmlEncode(value);
        value=changeSpace(value);
        dictionaryMapper.setDictionary(key,value,author, System.currentTimeMillis()/1000);
    }

    public List<Dictionary> getAll(){
        return dictionaryMapper.getDictionary();
    }

    //获取随机颜色代码
    private String getDandomHex(){
        StringBuffer result = new StringBuffer();
        for(int i=0;i<6;i++) {
            result.append(Integer.toHexString(new Random().nextInt(16)));
        }
        return result.toString().toUpperCase();
    }
    private String changeSpace(String content){

        return content.replaceAll(" ","&ensp;").replaceAll("@br@","<br>");

    }
}
