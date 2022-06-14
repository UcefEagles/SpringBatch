package com.example.demo;


import com.example.demo.dao.Facturation;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;


@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
   @Autowired  private JobBuilderFactory jobBuilderFactory;
   @Autowired private StepBuilderFactory stepBuilderFactory;
   @Autowired private ItemReader<Facturation> facturationItemReader;
   @Autowired private ItemWriter<Facturation> facturationItemWriter;
   @Autowired  private ItemProcessor<Facturation,Facturation> FacturationItemProcessor;


   @Bean
   public Job FactureJob(){
       Step step1=stepBuilderFactory.get("step-load-data")
               .<Facturation,Facturation>chunk(100)
               .reader((facturationItemReader))
               .processor(FacturationItemProcessor)
               .writer(facturationItemWriter)
               .build();

       return jobBuilderFactory.get("bank-data-loader-job")
               .start(step1).build();
   }

   @Bean
   public FlatFileItemReader<Facturation> flatFileItemReader(@Value("${inputFile}") Resource inputFile){
      FlatFileItemReader<Facturation> flatFileItemReader=new FlatFileItemReader<>();
      flatFileItemReader.setName("I2AD");
      flatFileItemReader.setLinesToSkip(1);
      flatFileItemReader.setResource(inputFile);
      flatFileItemReader.setLineMapper(lineMapper());
      return flatFileItemReader;
   }
   @Bean
   public LineMapper<Facturation> lineMapper() {
      DefaultLineMapper<Facturation> lineMapper=new DefaultLineMapper<>();
      DelimitedLineTokenizer lineTokenizer=new DelimitedLineTokenizer();
      lineTokenizer.setDelimiter(",");
      lineTokenizer.setStrict(false);
      lineTokenizer.setNames("id","id_customer","strFactureDate","amount");
      lineMapper.setLineTokenizer(lineTokenizer);
      BeanWrapperFieldSetMapper fieldSetMapper=new BeanWrapperFieldSetMapper();
      fieldSetMapper.setTargetType(Facturation.class);
      lineMapper.setFieldSetMapper(fieldSetMapper);
      return lineMapper;

   }


}
