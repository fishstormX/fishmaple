package fishmaple.Service;

import org.springframework.stereotype.Service;

@Service
public interface AnonymousService {
    public String getName(String sessionId);
    public String changeName(String sessionId);
}
