package com.example.demo;

import com.example.demo.dao.Facturation;
import com.example.demo.dao.FacturationRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class facturationItemWriter implements ItemWriter<Facturation> {
   @Autowired
   private FacturationRepository facturationRepository;
    @Override
    public void write(List<? extends Facturation> list) throws Exception {
            facturationRepository.saveAll(list);
    }
}
