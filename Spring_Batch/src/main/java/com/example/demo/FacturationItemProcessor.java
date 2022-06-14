package com.example.demo;

import com.example.demo.dao.Facturation;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;


@Component
public class FacturationItemProcessor implements ItemProcessor<Facturation,Facturation> {

    private SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy-HH:mm");
    @Override
    public Facturation process(Facturation facturation) throws Exception {
        facturation.setDateFacturation(dateFormat.parse(facturation.getStrFactureDate()));

        return facturation;
    }
}
