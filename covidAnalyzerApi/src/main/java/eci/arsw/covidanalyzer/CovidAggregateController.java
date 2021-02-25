package eci.arsw.covidanalyzer;

import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;
import eci.arsw.covidanalyzer.service.ICovidAggregateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class CovidAggregateController {

    private ICovidAggregateService covidAggregateService;

    //TODO: Implemente todos los metodos POST que hacen falta.

    @RequestMapping(value = "/covid/result/true-positive", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addTruePositiveResult(Result result) throws Exception {
        try{
            covidAggregateService.aggregateResult(result, ResultType.TRUE_POSITIVE);
            return  new ResponseEntity("Se registro correctamente el resultado", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity("No fue posible el registro del resultado",HttpStatus.BAD_REQUEST);
        }

    }

    //TODO: Implemente todos los metodos GET que hacen falta.

    @RequestMapping(value = "/covid/result/true-positive", method = RequestMethod.GET)
    public ResponseEntity<?> getTruePositiveResult() throws Exception {
        covidAggregateService.getResult(ResultType.TRUE_POSITIVE);
        return ResponseEntity.ok("Hello World");
    }


    //TODO: Implemente el método.

    @RequestMapping(value = "/covid/result/persona/{id}", method = RequestMethod.PUT)
    public ResponseEntity savePersonaWithMultipleTests() throws Exception {
        //TODO
        covidAggregateService.getResult(ResultType.TRUE_POSITIVE);
        return null;
    }
    
}