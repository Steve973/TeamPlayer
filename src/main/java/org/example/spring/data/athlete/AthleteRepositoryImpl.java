package org.example.spring.data.athlete;

import org.example.spring.model.Athlete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Custom Athlete repository implementation providing advanced querying.
 */
public class AthleteRepositoryImpl implements AthleteRepositoryCustom {
    final private MongoTemplate mongoTemplate;

    /**
     * Construct the Athlete repository and automatically wire the MongoTemplate.
     *
     * @param mongoTemplate the MongoTemplate automatically wired by Spring
     */
    public AthleteRepositoryImpl(@Autowired MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Returns an Aggregation that matches the searchable property, and provides pagination by
     * skipping the number of previous records returned on previous pages, and limits returned
     * results to the specified page size.  Records are sorted as specified in the parameters.
     *
     * @param propertyName the name of the property for searching
     * @param propertyValue the property value to search for
     * @param pageable paging and search information
     * @return an Aggregation that searches based on the property, and with the requested pagination
     */
    private static Aggregation getAggregation(String propertyName, String propertyValue, Pageable pageable) {
        MatchOperation matchOperation = match(Criteria.where(propertyName).is(propertyValue));
        SkipOperation skipOperation = skip((long) (pageable.getPageNumber() * pageable.getPageSize()));
        LimitOperation limitOperation = limit(pageable.getPageSize());
        SortOperation sortOperation = sort(pageable.getSort());

        return newAggregation(matchOperation, skipOperation, limitOperation, sortOperation);
    }

    /**
     * Find all Athletes by team.
     *
     * @param team Team name to use to search for Athletes
     * @param pageable Gets paging and search information from the parameters
     * @return a Page of Athletes that are on the specified Team.
     */
    @Override
    public Page<Athlete> findAllByTeam(String team, Pageable pageable) {
        long total = getCount("position.team.name", team);
        Aggregation aggregation = getAggregation("position.team.name", team, pageable);
        List<Athlete> aggregationResults = mongoTemplate.aggregate(aggregation, Athlete.class, Athlete.class).getMappedResults();
        return new PageImpl<>(aggregationResults, pageable, total);
    }

    /**
     * Find all athletes by Position.
     *
     * @param position the Position name to search for Athletes by
     * @param pageable Gets paging and search information from the parameters
     * @return all Athletes that have the specified Position
     */
    @Override
    public Page<Athlete> findAllByPosition(String position, Pageable pageable) {
        long total = getCount("position.name", position);
        Aggregation aggregation = getAggregation("position.name", position, pageable);
        List<Athlete> aggregationResults = mongoTemplate.aggregate(aggregation, Athlete.class, Athlete.class).getMappedResults();
        return new PageImpl<>(aggregationResults, pageable, total);
    }

    /**
     * Find all athletes by Conference
     *
     * @param conference the name of the Conference to search for Athletes by
     * @param pageable Gets paging and search information from the parameters
     * @return all Athletes in the specified Conference
     */
    @Override
    public Page<Athlete> findAllByConference(String conference, Pageable pageable) {
        long total = getCount("position.team.conference", conference);
        Aggregation aggregation = getAggregation("position.team.conference", conference, pageable);
        List<Athlete> aggregationResults = mongoTemplate.aggregate(aggregation, Athlete.class, Athlete.class).getMappedResults();
        return new PageImpl<>(aggregationResults, pageable, total);
    }

    /**
     * Find all Athletes by Division.
     *
     * @param division the name of the Division to search for Athletes by
     * @param pageable Gets paging and search information from the parameters
     * @return all Athletes in the specified Division
     */
    @Override
    public Page<Athlete> findAllByDivision(String division, Pageable pageable) {
        long total = getCount("position.team.division", division);
        Aggregation aggregation = getAggregation("position.team.division", division, pageable);
        List<Athlete> aggregationResults = mongoTemplate.aggregate(aggregation, Athlete.class, Athlete.class).getMappedResults();
        return new PageImpl<>(aggregationResults, pageable, total);
    }

    /**
     * Get the count of all of the entities that can be found by a given search.
     * I am open to better ways of doing this, but it seemed to be slightly difficult
     * to make paging work very well with Aggregation pipelines, so this is what
     * I came up with.
     *
     * @param propertyName property name for the search
     * @param propertyValue property value for the search
     * @return the total number of results where the property name is equal to the property value
     */
    private long getCount(String propertyName, String propertyValue) {
        Query countQuery = new Query(Criteria.where(propertyName).is(propertyValue));
        return mongoTemplate.count(countQuery, Athlete.class);
    }
}
