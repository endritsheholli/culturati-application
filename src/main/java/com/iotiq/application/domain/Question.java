package com.iotiq.application.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
public class Question extends AbstractPersistable<UUID> {
    private String title;
    private String hint;
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "question_id")
    private List<Option> options = new ArrayList<>();;
    private String rightAnswer;
    private String correctAnswerPoints;
    private String penalty;
    private String moreInformationUrl;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    private Set<GamerQuestion> gamerQuestion = new HashSet<>();
    
    // Convert the list of option texts to a list of Option entities
    public void setOptions(List<String> options){
        this.options = options.stream()
                .map(optionText -> {
                    Option option = new Option();
                    option.setText(optionText);
                    return option;
                })
                .collect(Collectors.toList());
    }
}
