package com.simplon.labxpert.controller;

import com.simplon.labxpert.model.dto.AnalysisDTO;
import com.simplon.labxpert.service.AnalysisService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class for managing analysis.
 *
 * @Author Ayoub Ait Si Ahmad
 */
@RestController
@RequestMapping("/api/v1/analysis")
public class AnalysisController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnalysisController.class);

    private final AnalysisService analysisService;

    @Autowired
    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    /**
     * This method allows to get all analysis.
     *
     * @return a list of all analysis.
     */
    @GetMapping
    @PreAuthorize("hasRole('TECHNICIAN') or hasRole('LABORATORY_MANAGER') or hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<AnalysisDTO>> getAllAnalysis() {
        LOGGER.info("Getting all analysis");
        List<AnalysisDTO> analysisDTOList = analysisService.findAllAnalysis();
        return new ResponseEntity<>(analysisDTOList, HttpStatus.OK);
    }

    /**
     * This method allows to get an analysis by id.
     *
     * @param id the id of the analysis to get.
     * @return an analysis.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('TECHNICIAN') or hasRole('LABORATORY_MANAGER') or hasRole('ADMINISTRATOR')")
    public ResponseEntity<AnalysisDTO> getAnalysisById(@PathVariable Long id) {
        LOGGER.info("Getting analysis with id : {}", id);
        AnalysisDTO analysisDTO = analysisService.findAnalysisById(id);
        return new ResponseEntity<>(analysisDTO, HttpStatus.OK);
    }

    /**
     * This method allows to create an analysis.
     *
     * @param analysisDTO the analysis to create.
     * @return the created analysis.
     */
    @PostMapping
    @PreAuthorize("hasRole('TECHNICIAN') or hasRole('LABORATORY_MANAGER') or hasRole('ADMINISTRATOR')")
    public ResponseEntity<AnalysisDTO> createAnalysis(@Valid @RequestBody AnalysisDTO analysisDTO) {
        LOGGER.info("Creating analysis from controller");
        AnalysisDTO analysisDTOCreated = analysisService.createAnalysis(analysisDTO);
        return new ResponseEntity<>(analysisDTOCreated, HttpStatus.CREATED);
    }

    /**
     * This method allows to start an analysis.
     * in implementation, it will be used to start an analysis and change its status to "IN_ANALYSIS".
     *
     * @param id the id of the analysis to start.
     * @return the started analysis.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TECHNICIAN') or hasRole('LABORATORY_MANAGER') or hasRole('ADMINISTRATOR')")
    public ResponseEntity<?> startAnalysis(@PathVariable Long id) {
        LOGGER.info("Starting analysis with id : {}", id);
        analysisService.startAnalysis(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This method allows to delete an analysis.
     *
     * @param id the id of the analysis to delete.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TECHNICIAN') or hasRole('LABORATORY_MANAGER') or hasRole('ADMINISTRATOR')")
    public ResponseEntity<String> deleteAnalysis(@PathVariable Long id) {
        LOGGER.info("Deleting analysis with id : {}", id);
        String message = analysisService.deleteAnalysis(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
