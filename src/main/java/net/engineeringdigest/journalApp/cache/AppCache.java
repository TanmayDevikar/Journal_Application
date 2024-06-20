package net.engineeringdigest.journalApp.cache;

import net.engineeringdigest.journalApp.entity.ConfigJournalAppEntity;
import net.engineeringdigest.journalApp.repository.ConfigJounralAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public Map<String, String> appCache;
    
    @Autowired
    private ConfigJounralAppRepository configJounralAppRepository;

    @PostConstruct  //Once the bean of the class gets created, this method will get called automatically once.
    public void init() {
        appCache = new HashMap<>();
        List<ConfigJournalAppEntity> all = configJounralAppRepository.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity: all) {
            appCache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }

    }
}
