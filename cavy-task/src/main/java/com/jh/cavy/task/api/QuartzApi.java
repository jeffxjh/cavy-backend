package com.jh.cavy.task.api;

import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.task.domain.Task;
import com.jh.cavy.task.params.JobPageParam;
import com.jh.cavy.task.params.JobParam;
import com.jh.cavy.task.service.QuartzBusService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/task")
@RestController
public class QuartzApi {
    @Resource
    private QuartzBusService quartzBusService;

    @PostMapping
    public void create(@RequestBody JobParam jobParam) {
        quartzBusService.addJob(jobParam);
    }

    @PutMapping
    public void update(@RequestBody JobParam jobParam) {
        quartzBusService.updateJob(jobParam);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        quartzBusService.delJob(id);
    }

    @GetMapping("/run/{id}")
    public void run(@PathVariable String id) {
        quartzBusService.runJob(id);
    }

    @GetMapping("/top/{id}")
    public void stop(@PathVariable String id) {
        quartzBusService.stopJob(id);
    }

    @GetMapping("/resume/{id}")
    public void resume(@PathVariable String id) {
        quartzBusService.resumeJob(id);
    }

    @PostMapping("/page")
    public ResultPage<Task> page(@RequestBody JobPageParam jobPageParam) {
        return quartzBusService.page(jobPageParam);
    }

}
