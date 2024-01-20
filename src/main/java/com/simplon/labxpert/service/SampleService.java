package com.simplon.labxpert.service;

import com.simplon.labxpert.model.dto.SampleDTO;

import java.util.List;
/**
 * Interface for the Sample service.
 * It contains the methods that the service will implement.
 */
public interface SampleService {
    List<SampleDTO> getAllSimple();
    SampleDTO createSample(SampleDTO sampleDTO);
    SampleDTO getSampleById(long sampleId);
    SampleDTO updateSample(SampleDTO sampleDTO);
    void deleteSample(long sampleId);


}
