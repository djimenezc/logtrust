package com.djimenezc.web.rest;

import com.djimenezc.config.JHipsterProperties;
import com.djimenezc.service.manager.LogManager;
import com.djimenezc.web.rest.dto.LogInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api")
public class LogInfoResource {

    private final Logger log = LoggerFactory.getLogger(LogInfoResource.class);

    @Inject
    Environment env;

    @Inject
    private JHipsterProperties jHipsterProperties;

    @Inject
    private LogManager logManager;

    @RequestMapping(value = "/logInfo",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public LogInfoDTO getLogInfo(@RequestParam(value = "connected") String connected, @RequestParam(value = "received") String received) {

        log.info("GetLogInfo connected "+connected + " connected " +received);

        LogInfoDTO logInfo = logManager.getLogParserService().getLogInfo(connected,received);

        log.info(logInfo.toString());
        return logInfo;
    }

}
