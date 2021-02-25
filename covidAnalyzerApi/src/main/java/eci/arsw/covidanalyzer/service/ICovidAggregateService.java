package eci.arsw.covidanalyzer.service;

import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;

import java.util.UUID;

public interface ICovidAggregateService {

    /**
     * Add a new result into the specified result type storage.
     *
     * @param result
     * @param type
     * @return
     */
    boolean aggregateResult(Result result, ResultType type) throws Exception;
    

    /**
     * Get all the results for the specified result type.
     *
     * @param type
     * @return
     */
    boolean getResult(ResultType type) throws Exception;

    /**
     * 
     * @param id
     * @param type
     */
    void upsertPersonWithMultipleTests(UUID id, ResultType type);


}
