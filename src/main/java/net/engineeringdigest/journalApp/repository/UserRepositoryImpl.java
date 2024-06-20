package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;  //MongoTemplate is a class that SpringDataMongoDB provides us. We can use this to interact with a database.

    public List<User> getUsersForSA() {

        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        //The above three queries providing an 'and' conditions which says email should be in a valid email address and sentimentAnalysis should be true.

        //If we want to give or conditions, we can give it like:
//        Criteria criteria = new Criteria();
//        query.addCriteria(criteria.orOperator(Criteria.where("email").exists(true),
//                Criteria.where("sentimentAnalysis").is(true)));

        List<User> users = mongoTemplate.find(query, User.class);//this will find the query condition that we have given, from the User class (email and sentimentAnalysis).
        return users;
    }
}
