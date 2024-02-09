package com.simplon.labxpert.controller;

import com.simplon.labxpert.model.dto.PatientDTO;
import com.simplon.labxpert.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * PatientController is a class that manage patient-related related operations
 * <p>This class interacts with the PatientServices to perform patient-related tasks.</p>
 *
 * @author Chaimaa MAHY
 */
@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Retrieves a list of all patients in the system.
     *
     * <p>This endpoint is mapped to the HTTP GET method using the {@code @GetMapping} annotation.
     * It returns a {@code ResponseEntity<List<PatientDTO>>} containing a list of {@code PatientDTO} objects.</p>
     *
     * <p>If the operation is successful, it responds with an HTTP status OK (200) and the list of patients.
     * If an exception occurs during the operation, it responds with an HTTP status INTERNAL_SERVER_ERROR (500).</p>
     *
     * @return A {@code ResponseEntity<List<PatientDTO>>} containing a list of {@code PatientDTO} objects.
     * The HTTP status is OK (200) if the operation is successful, INTERNAL_SERVER_ERROR (500) otherwise.
     */
    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        LOGGER.info("Fetching all patients");
        List<PatientDTO> patientsDTOS = patientService.getAllPatients();
        return new ResponseEntity<>(patientsDTOS, HttpStatus.OK);
    }

    /**
     * Create a new Patient and add it to the system.
     *
     * <p>This endpoint is mapped to the HTTP POST method using the {@code @PostMapping} annotation.
     * It returns a {@code ResponseEntity<PatientDTO>} containing a {@code PatientDTO} object.</p>
     *
     * <p>If the operation is successful, it responds with an HTTP status OK (200) and the created patient.
     * If there is a conflict (e.g., duplicate patient information) it responds with an HTTP status CONFLICT(409).
     * If an exception occurs during the operation, it responds with an HTTP status INTERNAL_SERVER_ERROR (500).</p>
     *
     * @param patientDTO A {@code PatientDTO} object representing the patient's details. It is expected in the request body.
     * @return A {@code ResponseEntity<PatientDTO>} containing a {@code PatientDTO} object.
     * The HTTP status is OK (200) if the operation is successful. if there is a conflict  (e.g., duplicate patient information) it responds with an HTTP status CONFLICT(409).
     * otherwise it responds with an HTTP status INTERNAL_SERVER_ERROR (500) .
     */

    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody PatientDTO patientDTO) {
        PatientDTO createdPatient = patientService.createPatient(patientDTO);
        LOGGER.info("Patient created successfully");
        return new ResponseEntity<>(createdPatient, HttpStatus.OK);
    }


    /**
     * Retrieves a Patient by his id  from the system.
     *
     * <p>This endpoint is mapped to the HTTP GET method using the {@code @GetMapping} annotation.
     * It returns a {@code ResponseEntity<PatientDTO>} containing a {@code PatientDTO} object.</p>
     *
     * @param patientId A {@code patientId} object representing the patient's id. It is expected in the path variable.
     *                  <p>If the operation is successful, it responds with an HTTP status OK (200) and the patient retrieved.
     *                  If there it patient with the patient id don't exist with an HTTP status NOT_FOUND(404).
     *                  If an exception occurs during the operation, it responds with an HTTP status INTERNAL_SERVER_ERROR (500).</p>
     * @return A {@code ResponseEntity<PatientDTO>} containing a {@code PatientDTO} object.
     * The HTTP status is OK (200) if the operation is successful. If the patient with the patientId doesn't exist return respond  with an HTTP status NOT_FOUND(404).
     * otherwise if there is an exception it responds with an HTTP status INTERNAL_SERVER_ERROR (500) .
     */

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientDTO> getPatientByID(@PathVariable long patientId) {
        PatientDTO patient = patientService.getPatientById(patientId);
        LOGGER.info("patient has been fetched successfully ");
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    /**
     * Update a Patient already existing in the system.
     *
     * <p>This endpoint is mapped to the HTTP PUT method using the {@code @PutMapping} annotation.
     * It returns a {@code ResponseEntity<PatientDTO>} containing a {@code PatientDTO} of the updated object.</p>
     *
     * @param patientId A {@code patientId} object representing the patient's id. It is expected in the path variable.
     * @param patient   A {@code patient} object representing the patientDTO. It is expected in the body.
     *                  <p>If the operation is successful, it responds with an HTTP status OK (200) and the patient updated.
     *                  If there is no patient with the patientId return HTTP status NOT_FOUND(404).
     *                  If an exception occurs during the operation, it responds with an HTTP status INTERNAL_SERVER_ERROR (500).</p>
     * @return A {@code ResponseEntity<PatientDTO>} containing a {@code PatientDTO} of the updated object.
     * The HTTP status is OK (200) if the operation is successful. If the patient with the patientId doesn't exist return respond  with an HTTP status NOT_FOUND(404).
     * otherwise if there is other exception it responds with an HTTP status INTERNAL_SERVER_ERROR (500) .
     */
    @PutMapping("/{patientId}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable long patientId, @Valid @RequestBody PatientDTO patient) {
        PatientDTO updatedPatient = patientService.updatePatient(patientId, patient);
        LOGGER.info("patient has been updated successfully ");
        return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
    }

    /**
     * Remove a Patient from  the system.
     *
     * <p>This endpoint is mapped to the HTTP DELETE method using the {@code @DeleteMapping} annotation.
     * It returns void.</p>
     *
     * @param patientId A {@code patientId} object representing the patient's id. It is expected in the path variable.
     *                  <p>If the operation is successful, it return with an HTTP status OK (200).
     *                  If there it patient with the patient id don't exist with an HTTP status NOT_FOUND(404).
     *                  If an other exception occurs during the operation, it responds with an HTTP status INTERNAL_SERVER_ERROR (500).</p>
     */

    @DeleteMapping("/{patientId}")
    public ResponseEntity<String> deletePatient(@PathVariable long patientId) {
        patientService.deletePatient(patientId);
        LOGGER.info("patient has been deleted successfully ");
        return new ResponseEntity<>("patient has been deleted successfully ", HttpStatus.OK);
    }

    /**
     * Retrieves a Patient by his email  from the system.
     *
     * <p>This endpoint is mapped to the HTTP GET method using the {@code @GetMapping} annotation.
     * It returns a {@code ResponseEntity<PatientDTO>} containing a {@code PatientDTO} object.</p>
     *
     * @param email A {@code patientId} object representing the patient's Email. It is expected in the request Parameter.
     *              <p>If the operation is successful, it return with an HTTP status OK (200) and the patient retrieved.
     *              If there is no patient with the patient email respond HTTP status NOT_FOUND(404).
     *              If an exception occurs during the operation, it responds with an HTTP status INTERNAL_SERVER_ERROR (500).</p>
     * @return A {@code ResponseEntity<PatientDTO>} containing a {@code PatientDTO} object.
     * The HTTP status is OK (200) if the operation is successful. If the patient with the patientId doesn't exist return respond  with an HTTP status NOT_FOUND(404).
     * otherwise if there is an exception it responds with an HTTP status INTERNAL_SERVER_ERROR (500) .
     */
    @GetMapping("/byEmail")
    public ResponseEntity<PatientDTO> getPatientByEmail(@RequestParam String email) {
        PatientDTO patient = patientService.getPatientByEmail(email);
        LOGGER.info("patient fetched successfully ");
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

}





