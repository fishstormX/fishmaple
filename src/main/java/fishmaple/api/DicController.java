package fishmaple.api;


import fishmaple.DAO.DictionaryMapper;
import fishmaple.DTO.Dictionary;
import fishmaple.Objects.BaiduSearchAssObject;
import fishmaple.Objects.DictionaryObject;
import fishmaple.Service.DicService;
import fishmaple.shiro.ShiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * @author 鱼鱼
 * 词典的相关接口
 * 调用百度的接口
 * */
@RestController
@RequestMapping("/api/dictionary")
public class DicController {
    @Autowired
    DictionaryMapper dictionaryMapper;
    @Autowired
    DicService dicService;
    @Autowired
    ShiroService shiroService;

    //获取词条列表
    @RequestMapping(value="/entries",method = RequestMethod.GET)
    public List<DictionaryObject> getEntries(){
        List<DictionaryObject> list=dictionaryMapper.getDictionaryObjects();
        list=dicService.setRandomColor(list);
        return list;
    }

    @RequestMapping(value="/entry/{key}",method = RequestMethod.GET)
    public Dictionary getEntryByKey(@PathVariable String key){
            return  dictionaryMapper.getDictionaryByKey(key);
    }

    @RequestMapping(value="/entry",method = RequestMethod.POST)
    public String setEntry(@RequestBody Dictionary dic){
        if(!dic.getAuthor().equals("匿名")&&!dic.getAuthor().equals(shiroService.getCurrentUser().getName())){
            return "未知参数：用户名与发布者不匹配";
        }
        if(dic.getKey().length()>16){
            return "词条名请控制在15个字符内！";
        }
        if(dic.getKey().equals("")||dic.getValue().equals("")){
            return "词条和内容不得为空！";
        }
        else if(getEntryByKey(dic.getKey())!=null){
            return "存在相同词条！";
        }
        else{
            dicService.setEntry(dic.getKey(),dic.getValue(),dic.getAuthor());
            return "success";
        }
    }

    @RequestMapping(value="/tp/associational/{key}",method=RequestMethod.GET)
    public List<BaiduSearchAssObject> getAss(@PathVariable String key) throws IOException {

        return dicService.getKeyAss(key);
    }
    @RequestMapping(value="/tp/associational",method=RequestMethod.GET)
    public List<BaiduSearchAssObject> getEnptyKey()  {
        return new ArrayList<>();
    }

    @RequestMapping("all")
    public List<Dictionary> getAll(){
        return dicService.getAll();
    }

}
