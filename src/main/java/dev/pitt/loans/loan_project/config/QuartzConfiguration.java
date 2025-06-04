package dev.pitt.loans.loan_project.config;

import dev.pitt.loans.loan_project.services.jobs.JobMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Slf4j
@Configuration
public class QuartzConfiguration {

	@Value("${quartz.jobCron}")
	private String jobCron;
	
	@Value("${quartz.jobGroup}")
    private String jobGroup;

    @Bean
    CronTriggerFactoryBean mailTrigger() { // Fire
    	log.info( "jobCron "+ jobCron);
    	log.info( "jobGroup "+ jobGroup);
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(mailJob().getObject());
        cronTriggerFactoryBean.setCronExpression(jobCron);
        cronTriggerFactoryBean.setGroup(jobGroup);
        return cronTriggerFactoryBean;
    }

    @Bean
    JobDetailFactoryBean mailJob() { // Tarea
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(JobMailService.class);
        jobDetailFactoryBean.setGroup(jobGroup);
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }

    /*
    @Bean
    public Properties quartzProperties() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        Properties properties = null;
        try {
            propertiesFactoryBean.afterPropertiesSet();
            properties = propertiesFactoryBean.getObject();
        } catch (IOException e) {
        }

        return properties;
    }*/
}
