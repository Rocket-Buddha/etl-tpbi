package edu.palermo.master.ti.bi.core;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @PostMapping("/start")
    public String startJob() {
        try {
            JobExecution jobExecution = jobLauncher.run(job, new JobParameters());
            return "Job iniciado con éxito. Job ID: " + jobExecution.getId();
        } catch (Exception e) {
            return "Error al iniciar el job: " + e.getMessage();
        }
    }
}
