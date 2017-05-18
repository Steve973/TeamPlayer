package org.example.spring.data.athlete;

import org.example.spring.model.Athlete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Custom Athlete repository implementation providing advanced querying.
 */
public class AthleteRepositoryImpl implements AthleteRepositoryCustom {
    final private MongoTemplate mongoTemplate;

    public AthleteRepositoryImpl(@Autowired MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    private static Aggregation getAggregation(String propertyName, String propertyValue, Pageable pageable) {
        MatchOperation matchOperation = match(Criteria.where(propertyName).is(propertyValue));
        SkipOperation skipOperation = skip((long) (pageable.getPageNumber() * pageable.getPageSize()));
        LimitOperation limitOperation = limit(pageable.getPageSize());
        SortOperation sortOperation = sort(pageable.getSort());

        return newAggregation(matchOperation, skipOperation, limitOperation, sortOperation);
    }

    @Override
    public Page<Athlete> findAllByTeam(String team, Pageable pageable) {
        long total = getCount("position.team.name", team);
        Aggregation aggregation = getAggregation("position.team.name", team, pageable);
        List<Athlete> aggregationResults = mongoTemplate.aggregate(aggregation, Athlete.class, Athlete.class).getMappedResults();
        return new PageImpl<>(aggregationResults, pageable, total);
    }

    @Override
    public Page<Athlete> findAllByPosition(String position, Pageable pageable) {
        long total = getCount("position.name", position);
        Aggregation aggregation = getAggregation("position.name", position, pageable);
        List<Athlete> aggregationResults = mongoTemplate.aggregate(aggregation, Athlete.class, Athlete.class).getMappedResults();
        return new PageImpl<>(aggregationResults, pageable, total);
    }

    @Override
    public Page<Athlete> findAllByConference(String conference, Pageable pageable) {
        long total = getCount("position.team.conference", conference);
        Aggregation aggregation = getAggregation("position.team.conference", conference, pageable);
        List<Athlete> aggregationResults = mongoTemplate.aggregate(aggregation, Athlete.class, Athlete.class).getMappedResults();
        return new PageImpl<>(aggregationResults, pageable, total);
    }

    @Override
    public Page<Athlete> findAllByDivision(String division, Pageable pageable) {
        long total = getCount("position.team.division", division);
        Aggregation aggregation = getAggregation("position.team.division", division, pageable);
        List<Athlete> aggregationResults = mongoTemplate.aggregate(aggregation, Athlete.class, Athlete.class).getMappedResults();
        return new PageImpl<>(aggregationResults, pageable, total);
    }

    private long getCount(String propertyName, String propertyValue) {
        MatchOperation matchOperation = match(Criteria.where(propertyName).is(propertyValue));
        GroupOperation groupOperation = group(propertyName).count().as("count");
        Aggregation aggregation = newAggregation(matchOperation, groupOperation);
        return mongoTemplate.aggregate(aggregation, Athlete.class, NumberOfResults.class).getMappedResults().get(0).getCount();
    }

    private class NumberOfResults {
        private int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
