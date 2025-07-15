package spring.boot.deasfioAPI.repository;

import org.springframework.stereotype.Component;
import spring.boot.deasfioAPI.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserRepository {
    private final Map<UUID, User> db = new ConcurrentHashMap<>();

    public void saveAll(Collection<User> users){
        users.forEach(user -> {db.put(user.getId(), user);});
    }

    public Collection<User> findAll(){
        return db.values();
    }
}
