package at.spengergasse.sj2324seedproject.presentation.api.commands;


import at.spengergasse.sj2324seedproject.domain.Producer;

public record ProducerCommand(String shortname, String name){
    public ProducerCommand(Producer producer){
        this(producer.getName(), producer.getShortname());
    }
}
