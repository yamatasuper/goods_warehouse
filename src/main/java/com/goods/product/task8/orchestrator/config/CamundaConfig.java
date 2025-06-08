package com.goods.product.task8.orchestrator.config;

import javax.sql.DataSource;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class CamundaConfig {

  @Bean
  public SpringProcessEngineConfiguration processEngineConfiguration(
      DataSource dataSource, PlatformTransactionManager transactionManager) {
    SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();
    config.setDataSource(dataSource);
    config.setTransactionManager(transactionManager);
    config.setDatabaseSchemaUpdate("true"); // Обновление схемы БД
    config.setHistory("full"); // Включение полной истории
    config.setJobExecutorActivate(true); // Активация Job Executor
    return config;
  }

  @Bean
  public ProcessEngineFactoryBean processEngine(SpringProcessEngineConfiguration config) {
    ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
    factoryBean.setProcessEngineConfiguration(config);
    return factoryBean;
  }
}
