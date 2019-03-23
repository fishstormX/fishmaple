package fishmaple.api;

import fishmaple.DAO.NotesMapper;
import fishmaple.DAO.UserMapper;
import fishmaple.DAO.WordsMapper;
import fishmaple.DTO.Notes;
import fishmaple.DTO.User;
import fishmaple.DTO.Words;
import fishmaple.Objects.NotesObj;
import fishmaple.shiro.TokenService;
import fishmaple.utils.EncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/m")
public class MobileApi {
    @Autowired
    WordsMapper wordsMapper;
    @Autowired
    TokenService tokenService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    NotesMapper notesMapper;
    @GetMapping("/words")
    public Words getWords(){
        return wordsMapper.getRandOne();
    }

    @GetMapping("/who")
    public Map<String,String> getUser(@RequestBody Map<String,String> map){
        String id=tokenService.getUserId(map.get("token"));
        Map<String,String> userMap=new HashMap<>();
        if(null!=id) {
            User user = userMapper.selectUserById(id);
            userMap.put("name",user.getName());
            userMap.put("id",user.getId());
        }else{
            userMap.put("name","");
        }
        return userMap;
    }

    @GetMapping("/notes")
    public Map<String,Object> getNotes(@RequestParam String uid, @RequestParam String token){
        Map<String,Object> result = new HashMap<>();
        String tuid=tokenService.getUserId(token);
        if(tuid==null){
            result.put("msg","用户会话过期");
        }
        else{
            if(!tuid.equals(uid)){
            //TODO 鉴权
            }
            result.put("msg","SUCCESS");
            result.put("data",notesMapper.getNotesByUid(uid));
        }
        return result;
    }

    @PostMapping("/notes")
    public Map<String,Object> getNotes(@RequestBody NotesObj notesObj){
        Map<String,Object> result = new HashMap<>();
        String tuid=tokenService.getUserId(notesObj.getToken());
        if(tuid==null){
            result.put("msg","用户会话过期");
        }
        else{
            Notes notes=notesObj.getNotes();
            if(!tuid.equals(notes.getUid())){
                result.put("msg","权限错误");
                return result;
            }else{
                String uuid=EncoderUtil.getUUID(1);
                notes.setId(uuid);
                notesMapper.setNotes(notes);
                result.put("msg","SUCCESS");
                result.put("data",uuid);
            }
        }
        return result;
    }
}
