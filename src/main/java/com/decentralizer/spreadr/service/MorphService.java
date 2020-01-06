package com.decentralizer.spreadr.service;

import com.decentralizer.spreadr.data.MorphRepository;
import com.decentralizer.spreadr.data.entities.Morph;
import com.decentralizer.spreadr.data.kafkaDTO.MorphDTOK;
import com.decentralizer.spreadr.data.requestDTO.RequestMorph;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MorphService {

    public static final String MORPH_TOPIC = "morph";
    private final MorphRepository morphRepository;
    private final KafkaTemplate<String, MorphDTOK> morphDTOKKafkaTemplate;
    private final ModelMapper modelMapper;

    public void saveToDb(MorphDTOK morphDTOK) {
        Morph morph = modelMapper.map(morphDTOK, Morph.class);
        saveToDb(morph);
    }

    public void sendOnKafka(RequestMorph requestMorph) {
        MorphDTOK morphDTOK = modelMapper.map(requestMorph, MorphDTOK.class);
        sendOnKafka(morphDTOK);
    }

    public void saveToDb(Morph morph) {
        morphRepository.save(morph);
    }

    public void sendOnKafka(MorphDTOK morphDTOK) {
        morphDTOKKafkaTemplate.send(MORPH_TOPIC, morphDTOK);
    }
}
