/*Copyright (c) 2015-2016 wavemaker-com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker-com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker-com*/
package com.salesdb.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import com.salesdb.Customers;
import com.salesdb.States;
import com.salesdb.service.StatesService;


/**
 * Controller object for domain model class States.
 * @see States
 */
@RestController("salesdb.StatesController")
@Api(value = "StatesController", description = "Exposes APIs to work with States resource.")
@RequestMapping("/salesdb/States")
public class StatesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatesController.class);

    @Autowired
	@Qualifier("salesdb.StatesService")
	private StatesService statesService;

	@ApiOperation(value = "Creates a new States instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public States createStates(@RequestBody States states) {
		LOGGER.debug("Create States with information: {}" , states);

		states = statesService.create(states);
		LOGGER.debug("Created States with information: {}" , states);

	    return states;
	}

    @ApiOperation(value = "Returns the States instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
public States getStates(@PathVariable("id") Integer id) {
        LOGGER.debug("Getting States with id: {}" , id);

        States foundStates = statesService.getById(id);
        LOGGER.debug("States details with id: {}" , foundStates);

        return foundStates;
    }

    @ApiOperation(value = "Updates the States instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public States editStates(@PathVariable("id") Integer id, @RequestBody States states) {
        LOGGER.debug("Editing States with id: {}" , states.getId());

        states.setId(id);
        states = statesService.update(states);
        LOGGER.debug("States details with id: {}" , states);

        return states;
    }

    @ApiOperation(value = "Deletes the States instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
public boolean deleteStates(@PathVariable("id") Integer id) {
        LOGGER.debug("Deleting States with id: {}" , id);

        States deletedStates = statesService.delete(id);

        return deletedStates != null;
    }

    /**
     * @deprecated Use {@link #findStates(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of States instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<States> searchStatesByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering States list by query filter:{}", (Object) queryFilters);
        return statesService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of States instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<States> findStates(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering States list by filter:", query);
        return statesService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of States instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<States> filterStates(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering States list by filter", query);
        return statesService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
    @RequestMapping(value = "/export/{exportType}", method = {RequestMethod.GET,  RequestMethod.POST}, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportStates(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return statesService.export(exportType, query, pageable);
    }

	@ApiOperation(value = "Returns the total count of States instances matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
	@RequestMapping(value = "/count", method = {RequestMethod.GET, RequestMethod.POST})
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countStates( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting States");
		return statesService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getStatesAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return statesService.getAggregatedValues(aggregationInfo, pageable);
    }

    @RequestMapping(value="/{id:.+}/customerses", method=RequestMethod.GET)
    @ApiOperation(value = "Gets the customerses instance associated with the given id.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Customers> findAssociatedCustomerses(@PathVariable("id") Integer id, Pageable pageable) {

        LOGGER.debug("Fetching all associated customerses");
        return statesService.findAssociatedCustomerses(id, pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service StatesService instance
	 */
	protected void setStatesService(StatesService service) {
		this.statesService = service;
	}

}

