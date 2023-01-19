package com.app.core.api.controller.scheduler;

import com.app.core.api.exception.AppSchedulingException;
import com.app.core.api.service.scheduler.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/scheduler/process")
public class SchedulerProcess {

    private final SchedulerService schedulerService;

    public SchedulerProcess(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    /** this method will be called every second by a cron job to process all scheduled items */
    @ResponseBody
    @RequestMapping(value = "/actions", method = RequestMethod.GET)
    public ResponseEntity<String> processScheduledActions() {

        schedulerService.processScheduledActions();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/leadCampaignFlow/{leadCampaignFlowId}/interaction/{interactionId}", method = RequestMethod.GET)
    public ResponseEntity<String> processScheduledAction(@PathVariable String leadCampaignFlowId, @PathVariable Integer interactionId) {

        log.info("Receiving Scheduled Action Request leadCampaignFlowId: {} and interactionId {}", leadCampaignFlowId, interactionId);

        try{
            schedulerService.processScheduledAction(leadCampaignFlowId, interactionId);
        }
        catch (Exception e){
            throw new AppSchedulingException(e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
