package ru.netology.patient.service.medical;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
//                 *** Начальный вариант ***
//class MedicalServiceImplTest {
//    @Test
//    void checkBloodPressure_was_message_sent() {
//        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
//        SendAlertService alertService = Mockito.mock(SendAlertService.class);
//        String patientId = "123";
//
//        BloodPressure currentBP = new BloodPressure(120,80);
//        BigDecimal currentT = BigDecimal.valueOf(36.5);
//        BloodPressure newBP = new BloodPressure(130,90);
//        HealthInfo healthInfo = new HealthInfo(currentT, currentBP);
//        PatientInfo patientInfo = new PatientInfo(patientId, "Mark", "Twain", LocalDate.of(2007,12,23), healthInfo);
//
//        Mockito.when(patientInfoRepository.getById(patientId)).thenReturn(patientInfo);
//        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
//        String expectedMessage = String.format("Warning, patient with id: %s, need help", patientId);
//        medicalService.checkBloodPressure(patientId, currentBP);
//        Mockito.verify(alertService, Mockito.never()).send(expectedMessage);
//        medicalService.checkBloodPressure(patientId, newBP);
//        Mockito.verify(alertService, Mockito.atLeastOnce()).send(expectedMessage);
//
//    }
//    @Test
//    void checkBloodPressure_message_content() {
//        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
//        SendAlertService alertService = Mockito.mock(SendAlertService.class);
//        String patientId = "123";
//
//        BloodPressure currentBP = new BloodPressure(120, 80);
//        BigDecimal currentT = BigDecimal.valueOf(36.5);
//        BloodPressure newBP = new BloodPressure(130, 90);
//        HealthInfo healthInfo = new HealthInfo(currentT, currentBP);
//        PatientInfo patientInfo = new PatientInfo(patientId, "Mark", "Twain", LocalDate.of(2007, 12, 23), healthInfo);
//
//        Mockito.when(patientInfoRepository.getById(patientId)).thenReturn(patientInfo);
//        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
//        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
//        medicalService.checkBloodPressure(patientId, newBP);
//
//        Mockito.verify(alertService).send(argumentCaptor.capture());
//        String expectedMessage = String.format("Warning, patient with id: %s, need help", patientId);
//        Assertions.assertEquals(expectedMessage, argumentCaptor.getValue());
//    }
//
//    @Test
//    void checkTemperature() {
//        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
//        SendAlertService alertService = Mockito.mock(SendAlertService.class);
//        String patientId = "123";
//
//        BloodPressure currentBP = new BloodPressure(120,80);
//        BigDecimal currentTemp = BigDecimal.valueOf(36.5);
//        BigDecimal newTemp = BigDecimal.valueOf(32);
//        HealthInfo healthInfo = new HealthInfo(currentTemp, currentBP);
//        PatientInfo patientInfo = new PatientInfo(patientId, "Mark", "Twain", LocalDate.of(2007,12,23), healthInfo);
//
//        Mockito.when(patientInfoRepository.getById(patientId)).thenReturn(patientInfo);
//        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
//        String expectedMessage = String.format("Warning, patient with id: %s, need help", patientId);
//        medicalService.checkTemperature(patientId, currentTemp);
//        Mockito.verify(alertService, Mockito.never()).send(expectedMessage);
//        medicalService.checkTemperature(patientId, newTemp);
//        Mockito.verify(alertService, Mockito.atLeastOnce()).send(expectedMessage);
//    }
//    @Test
//    void checkTemperature_message_content() {
//        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
//        SendAlertService alertService = Mockito.mock(SendAlertService.class);
//        String patientId = "123";
//
//        BloodPressure currentBP = new BloodPressure(120,80);
//        BigDecimal currentTemp = BigDecimal.valueOf(36.5);
//        BigDecimal newTemp = BigDecimal.valueOf(32);
//        HealthInfo healthInfo = new HealthInfo(currentTemp, currentBP);
//        PatientInfo patientInfo = new PatientInfo(patientId, "Mark", "Twain", LocalDate.of(2007,12,23), healthInfo);
//        Mockito.when(patientInfoRepository.getById(patientId)).thenReturn(patientInfo);
//        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
//        String expectedMessage = String.format("Warning, patient with id: %s, need help", patientId);
//        medicalService.checkTemperature(patientId, newTemp);
//
//        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
//        Mockito.verify(alertService).send(argumentCaptor.capture());
//        Assertions.assertEquals(expectedMessage, argumentCaptor.getValue());
//    }

//                *** @BeforeEach & @MethodSource ***
class MedicalServiceImplTest {
    PatientInfoRepository patientInfoRepository;
    SendAlertService alertService;
    MedicalServiceImpl medicalService;
    String patientId;
    BloodPressure currentBP;
    BigDecimal currentTemp;
    String expectedMessage;
    HealthInfo healthInfo;
    PatientInfo patientInfo;

    @BeforeEach
    public void setUp() {
        patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        alertService = Mockito.mock(SendAlertService.class);
        medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        patientId = "123";
        currentBP = (new BloodPressure(120, 80));
        currentTemp =  BigDecimal.valueOf(36.5);
        expectedMessage = String.format("Warning, patient with id: %s, need help", patientId);
        healthInfo = new HealthInfo(currentTemp, currentBP);
        patientInfo = new PatientInfo(patientId, "Mark", "Twain", LocalDate.of(2007, 12, 23), healthInfo);
        Mockito.when(patientInfoRepository.getById(patientId)).thenReturn(patientInfo);
    }

    public static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.of(new BloodPressure(120, 80), BigDecimal.valueOf(36.5), false),
                Arguments.of(new BloodPressure(130, 90), BigDecimal.valueOf(32), true));
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    void checkBloodPressure_was_message_sent(BloodPressure newBP, BigDecimal newTemp, boolean shouldAlert) {
        medicalService.checkBloodPressure(patientId, newBP);
        if (shouldAlert) {
            Mockito.verify(alertService, Mockito.atLeastOnce()).send(expectedMessage);
        } else {
            Mockito.verify(alertService, Mockito.never()).send(expectedMessage);
        }
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    void checkBloodPressure_message_content(BloodPressure newBP, BigDecimal newTemp, boolean shouldAlert) {
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        medicalService.checkBloodPressure(patientId, newBP);
        if (shouldAlert) {
            Mockito.verify(alertService).send(argumentCaptor.capture());
            Assertions.assertEquals(expectedMessage, argumentCaptor.getValue());
        }
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    void checkTemperature_was_message_sent(BloodPressure newBP, BigDecimal newTemp, boolean shouldAlert) {
        medicalService.checkTemperature(patientId, newTemp);
        if (shouldAlert) {
            Mockito.verify(alertService, Mockito.atLeastOnce()).send(expectedMessage);
        } else {
            Mockito.verify(alertService, Mockito.never()).send(expectedMessage);
        }
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    void checkTemperature_message_content(BloodPressure newBP, BigDecimal newTemp, boolean shouldAlert) {
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        medicalService.checkTemperature(patientId, newTemp);
        if (shouldAlert) {
            Mockito.verify(alertService).send(argumentCaptor.capture());
            Assertions.assertEquals(expectedMessage, argumentCaptor.getValue());
        }
    }
}