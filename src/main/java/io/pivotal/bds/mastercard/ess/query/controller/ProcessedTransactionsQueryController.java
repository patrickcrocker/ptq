package io.pivotal.bds.mastercard.ess.query.controller;

import io.pivotal.bds.mastercard.ess.query.service.ProcessedTransactionsQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class ProcessedTransactionsQueryController {
    @Autowired
    ProcessedTransactionsQueryService processedTransactionsQueryService;

    @RequestMapping(value = "/processedTransactions/{custClstrId}", method = RequestMethod.GET)
    public
    @ResponseBody
    List queryByCustomer(@PathVariable("custClstrId") String custClstrId,
                         @RequestParam("start_date") String startDate,
                         @RequestParam("end_date") String endDate,
                         @RequestParam("limit") Long limit) {
        Date startTime = new Date();
        System.err.println("Start: " + startTime + "***");
        List results = new ArrayList();
        results.add("Start: " + startTime + "***");
        List transactions = processedTransactionsQueryService.queryByCustomer(custClstrId, "01/01/2016 00:00:00", "12/29/2016 01:01:01", limit);
        Date endTime = new Date();
        System.err.println("End: " + endTime + "***");
        results.add("End: " + endTime + "***");
        long timeDiff = getDateDiff(startTime,endTime,TimeUnit.MINUTES);
        results.add("Total Query Time (in Seconds): " + timeDiff + "***");
        results.add(transactions);
        System.err.println(results);
        return results;
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.SECONDS);
    }
}