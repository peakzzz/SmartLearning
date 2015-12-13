package edu.sjsu.assess.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjoshi on 4/26/15.
 */

public class Pagination
{
    List<? extends Object> results = new ArrayList<>();
    double totalPages = 0;
    int pageSize = 25;

    public Pagination() {

    }

    public Pagination(List<? extends Object> results, int pageSize) {
        this.results = results;
        this.pageSize = pageSize;
        totalPages = (results.size()/pageSize);
    }

    public List<? extends Object> getResultsForPage(List<? extends Object> results, int pageNo) {
        int startIndex = pageSize*(pageNo-1);
        int endIndex = pageSize*(pageNo)-1;
        if(endIndex > (results.size()-1)) {
            endIndex = results.size()-1;
        }
        totalPages = (results.size() / pageSize)+((results.size()%pageSize) > 0? 1: 0);
        totalPages = totalPages>0? totalPages:1;
        return results.subList(startIndex, endIndex);
    }


    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public double getTotalPages()
    {
        return totalPages;
    }

    public void setTotalPages(double totalPages)
    {
        this.totalPages = totalPages;
    }

    public List<? extends Object> getResults()
    {
        return results;
    }

    public void setResults(List<? extends Object> results)
    {
        this.results = results;
    }
}
