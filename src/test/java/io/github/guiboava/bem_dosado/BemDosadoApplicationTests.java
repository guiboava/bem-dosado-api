package io.github.guiboava.bem_dosado;

import io.github.guiboava.bem_dosado.controller.dto.PatientRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.TaskRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.UserRequestDTO;
import io.github.guiboava.bem_dosado.controller.mappers.*;
import io.github.guiboava.bem_dosado.entity.model.*;
import io.github.guiboava.bem_dosado.entity.model.enums.Dependency;
import io.github.guiboava.bem_dosado.entity.model.enums.Gender;
import io.github.guiboava.bem_dosado.entity.model.enums.UserType;
import io.github.guiboava.bem_dosado.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

@SpringBootTest
class BemDosadoApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private TaskTypeService taskTypeService;

    @Autowired
    private MedicationService medicationService;

    @Autowired
    private PublicEmergencyService publicEmergencyService;

    @Autowired
    private PatientContactService patientContactService;

    @Autowired
    private PatientHealthService patientHealthService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskTypeMapper taskTypeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private MedicationMapper medicationMapper;

    @Autowired
    private PublicEmergencyMapper publicEmergencyMapper;

    @Autowired
    private PatientContactMapper patientContactMapper;

    @Autowired
    private PatientHealthMapper patientHealthMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Test
    void initialValues() {

        assumeTrue(userService.getByEmail("guiboava@hotmail.com") == null, "Usuário já existe, pulando teste");

        //Criar usuario inicial
        User testUser = createTestUser();

        //Pacientes iniciais
        Patient p1 = createTestPatient(testUser, "Maria Silva", "123.456.789-00", Gender.F, Dependency.G1);
        Patient p2 = createTestPatient(testUser, "João Souza", "234.567.890-11", Gender.M, Dependency.G2);
        Patient p3 = createTestPatient(testUser, "Ana Pereira", "345.678.901-22", Gender.F, Dependency.G1);
        Patient p4 = createTestPatient(testUser, "Carlos Lima", "456.789.012-33", Gender.M, Dependency.G3);

        //Vincular pacientes ao usuario inicial
        userService.addPatientToUser(testUser.getId(), p1.getId());
        userService.addPatientToUser(testUser.getId(), p2.getId());
        userService.addPatientToUser(testUser.getId(), p3.getId());
        userService.addPatientToUser(testUser.getId(), p4.getId());

        //Criar tipos de tarefa iniciais.
        createTestTaskType(testUser, "Administração de medicamentos");
        createTestTaskType(testUser, "Monitoramento de sinais vitais");
        createTestTaskType(testUser, "Higiene pessoal");
        createTestTaskType(testUser, "Alimentação do paciente");
        createTestTaskType(testUser, "Exercícios e mobilidade");
        createTestTaskType(testUser, "Acompanhamento médico");
        createTestTaskType(testUser, "Atividades de lazer e socialização");
        createTestTaskType(testUser, "Manutenção do ambiente");
        createTestTaskType(testUser, "Registro e relatórios");
        createTestTaskType(testUser, "Atendimento a emergências");

        //Criar medicamentos iniciais.
        Set<Medication> listMedication = new HashSet<>();
        listMedication.add(createTestMedication(testUser, "Paracetamol", 500.0, "A cada 8h", "Tomar após as refeições"));
        listMedication.add(createTestMedication(testUser, "Dipirona", 600.0, "A cada 6h", "Evitar uso prolongado"));
        listMedication.add(createTestMedication(testUser, "Ibuprofeno", 400.0, "A cada 8h", "Tomar com alimento"));
        listMedication.add(createTestMedication(testUser, "Omeprazol", 20.0, "Uma vez ao dia", "Tomar 30 min antes do café da manhã"));
        listMedication.add(createTestMedication(testUser, "Metformina", 850.0, "Duas vezes ao dia", "Controlar glicemia"));
        listMedication.add(createTestMedication(testUser, "Losartana", 50.0, "Uma vez ao dia", "Monitorar pressão arterial"));
        listMedication.add(createTestMedication(testUser, "Simvastatina", 20.0, "Uma vez ao dia", "Evitar consumo de álcool"));
        listMedication.add(createTestMedication(testUser, "Amoxicilina", 500.0, "A cada 8h", "Completar o tratamento"));
        listMedication.add(createTestMedication(testUser, "Clonazepam", 0.5, "Duas vezes ao dia", "Não interromper abruptamente"));
        listMedication.add(createTestMedication(testUser, "Ranitidina", 150.0, "Duas vezes ao dia", "Evitar uso por longo prazo"));

        //Criar contatos de emercencia publica iniciais.
        createPublicEmergency(testUser, "Polícia Militar", "190", "Serviço de emergência policial.");
        createPublicEmergency(testUser, "Bombeiros", "193", "Atendimento de incêndios e resgates.");
        createPublicEmergency(testUser, "SAMU", "192", "Serviço de atendimento móvel de urgência.");
        createPublicEmergency(testUser, "Defesa Civil", "199", "Atendimento a desastres naturais.");
        createPublicEmergency(testUser, "Guarda Municipal", "153", "Segurança e fiscalização local.");

        //Criar e adicionar contato de paciente inicial.
        createPatientContact(testUser, p1, "Maria Silva", "+5511999991111", "Mãe");
        createPatientContact(testUser, p2, "João Souza", "+5511999992222", "Pai");
        createPatientContact(testUser, p3, "Ana Pereira", "+5511999993333", "Tia");
        createPatientContact(testUser, p4, "Carlos Lima", "+5511999994444", "Amigo");


        // Criar relatorio de saude de pacientes.
        // Paciente 1
        createPatientHealth(testUser, p1, 120, 80, 98, 90, new BigDecimal("36.6"));
        createPatientHealth(testUser, p1, 122, 82, 97, 92, new BigDecimal("36.7"));
        createPatientHealth(testUser, p1, 118, 78, 99, 88, new BigDecimal("36.5"));
        createPatientHealth(testUser, p1, 125, 85, 96, 95, new BigDecimal("36.8"));
        createPatientHealth(testUser, p1, 119, 79, 98, 90, new BigDecimal("36.6"));

        // Paciente 2
        createPatientHealth(testUser, p2, 130, 85, 97, 100, new BigDecimal("36.8"));
        createPatientHealth(testUser, p2, 128, 83, 96, 98, new BigDecimal("36.7"));
        createPatientHealth(testUser, p2, 132, 87, 95, 102, new BigDecimal("36.9"));
        createPatientHealth(testUser, p2, 129, 84, 97, 101, new BigDecimal("36.8"));
        createPatientHealth(testUser, p2, 131, 86, 96, 99, new BigDecimal("36.7"));

        // Paciente 3
        createPatientHealth(testUser, p3, 110, 75, 99, 85, new BigDecimal("36.5"));
        createPatientHealth(testUser, p3, 112, 77, 98, 87, new BigDecimal("36.6"));
        createPatientHealth(testUser, p3, 108, 74, 100, 83, new BigDecimal("36.4"));
        createPatientHealth(testUser, p3, 111, 76, 97, 86, new BigDecimal("36.5"));
        createPatientHealth(testUser, p3, 109, 75, 98, 85, new BigDecimal("36.5"));

        // Paciente 4
        createPatientHealth(testUser, p4, 140, 90, 95, 110, new BigDecimal("37.0"));
        createPatientHealth(testUser, p4, 138, 88, 94, 108, new BigDecimal("36.9"));
        createPatientHealth(testUser, p4, 142, 91, 96, 112, new BigDecimal("37.1"));
        createPatientHealth(testUser, p4, 139, 89, 95, 109, new BigDecimal("37.0"));
        createPatientHealth(testUser, p4, 141, 90, 97, 111, new BigDecimal("37.0"));

        List<TaskType> listTaskType = taskTypeService.getAll()
                .stream()
                .map(taskTypeMapper::toEntityFromRequest)
                .toList();


        Random random = new Random();
        int min = 0;
        int max = 9;

        createTask(testUser, p1, listTaskType.get(random.nextInt(max - min + 1) + min), listMedication, "Dar medicação matinal", LocalDateTime.now().plusHours(1), 1);
        createTask(testUser, p2, listTaskType.get(random.nextInt(max - min + 1) + min), listMedication, "Verificar pressão arterial", LocalDateTime.now().plusHours(2), 2);
        createTask(testUser, p3, listTaskType.get(random.nextInt(max - min + 1) + min), listMedication, "Trocar curativo", LocalDateTime.now().plusHours(3), 3);
        createTask(testUser, p4, listTaskType.get(random.nextInt(max - min + 1) + min), listMedication, "Medir temperatura", LocalDateTime.now().plusHours(4), 1);
        createTask(testUser, p1, listTaskType.get(random.nextInt(max - min + 1) + min), listMedication, "Aplicar injeção", LocalDateTime.now().plusHours(5), 2);
        createTask(testUser, p2, listTaskType.get(random.nextInt(max - min + 1) + min), listMedication, "Dar banho", LocalDateTime.now().plusHours(6), 1);
        createTask(testUser, p3, listTaskType.get(random.nextInt(max - min + 1) + min), listMedication, "Trocar fralda", LocalDateTime.now().plusHours(7), 3);
        createTask(testUser, p4, listTaskType.get(random.nextInt(max - min + 1) + min), listMedication, "Verificar glicemia", LocalDateTime.now().plusHours(8), 2);
        createTask(testUser, p1, listTaskType.get(random.nextInt(max - min + 1) + min), listMedication, "Acompanhar exercícios", LocalDateTime.now().plusHours(9), 1);
        createTask(testUser, p2, listTaskType.get(random.nextInt(max - min + 1) + min), listMedication, "Dar lanche", LocalDateTime.now().plusHours(10), 2);

        createTask(testUser, p3, listTaskType.get(random.nextInt(max - min + 1) + min), listMedication, "Revisar medicação", LocalDateTime.now().plusHours(11), 1);
        createTask(testUser, p4, listTaskType.get(random.nextInt(max - min + 1) + min), listMedication, "Organizar documentos", LocalDateTime.now().plusHours(12), 2);
        createTask(testUser, p1, listTaskType.get(random.nextInt(max - min + 1) + min), listMedication, "Hidratar paciente", LocalDateTime.now().plusHours(13), 3);
        createTask(testUser, p2, listTaskType.get(random.nextInt(max - min + 1) + min), listMedication, "Verificar sinais vitais", LocalDateTime.now().plusHours(14), 1);
        createTask(testUser, p3, listTaskType.get(random.nextInt(max - min + 1) + min), listMedication, "Aplicar pomada", LocalDateTime.now().plusHours(15), 2);
        createTask(testUser, p4, listTaskType.get(random.nextInt(max - min + 1) + min), listMedication, "Ajudar na alimentação", LocalDateTime.now().plusHours(16), 1);
        createTask(testUser, p1, listTaskType.get(random.nextInt(max - min + 1) + min), listMedication, "Trocar roupa", LocalDateTime.now().plusHours(17), 2);
        createTask(testUser, p2, listTaskType.get(random.nextInt(max - min + 1) + min), listMedication, "Limpar quarto", LocalDateTime.now().plusHours(18), 3);
        createTask(testUser, p3, listTaskType.get(random.nextInt(max - min + 1) + min), listMedication, "Medir oximetria", LocalDateTime.now().plusHours(19), 1);
        createTask(testUser, p4, listTaskType.get(random.nextInt(max - min + 1) + min), listMedication, "Dar banho noturno", LocalDateTime.now().plusHours(20), 2);


    }

    private User createTestUser() {

        User user = new User();

        user.setName("Guilherme Boava");
        user.setLogin("guiboava");
        user.setEmail("guiboava@hotmail.com");
        user.setCpf("123.456.789-00");
        user.setUserType(UserType.C);
        user.setPassword("senha123");
        user.setGender(Gender.M);
        user.setPhoneNumber("+5511999999999");
        user.setCep("12345-678");
        user.setBirthDate(LocalDate.of(1998, 11, 4));
        user.setBase64Image(null);

        user.setCreatedDate(LocalDateTime.now());
        user.setUpdated_date(LocalDateTime.now());


        user.setPatients(new HashSet<>());
        user.setTasks(null);

        UserRequestDTO dto = userMapper.toRequestDTO(user);

        UUID userUUID = userService.save(dto);

        return userService.getEntityById(userUUID);
    }

    private Patient createTestPatient(User user, String name, String cpf, Gender gender, Dependency dependency) {
        Patient patient = new Patient();

        patient.setName(name);
        patient.setCpf(cpf);
        patient.setBirthDate(LocalDate.of(1945, 5, 12));
        patient.setGender(gender);
        patient.setCep("12345-678");
        patient.setDependency(dependency);
        patient.setHealthPlan("Plano Saúde 1");
        patient.setCardNumber("1234567890");
        patient.setAllergies("Nenhuma");
        patient.setMedicationsDescription("Nenhum medicamento atual");
        patient.setNote("Paciente necessita atenção especial.");
        patient.setBase64Image(null);


        patient.setCreatedByUser(user);
        patient.setUpdatedByUser(user);


        patient.setCreatedDate(LocalDateTime.now());
        patient.setUpdated_date(LocalDateTime.now());


        patient.setUsers(new HashSet<>());
        patient.setTasks(null);
        patient.setPatientHealths(null);
        patient.setContacts(null);

        PatientRequestDTO dto = patientMapper.toRequestDTO(patient);

        return patientService.getEntityById(patientService.save(dto));

    }

    private void createTestTaskType(User user, String describe) {
        TaskType taskType = new TaskType();

        taskType.setDescribe(describe);
        taskType.setCreatedByUser(user);
        taskType.setUpdatedByUser(user);
        taskType.setCreatedDate(LocalDateTime.now());
        taskType.setUpdated_date(LocalDateTime.now());
        taskType.setTasks(null);

        taskTypeService.save(taskTypeMapper.toRequestDTO(taskType));

    }

    private Medication createTestMedication(User user, String name, Double dosage, String frequency, String observation) {
        Medication medication = new Medication();

        medication.setName(name);
        medication.setDosage(dosage);
        medication.setFrequency(frequency);
        medication.setObservation(observation);
        medication.setCreatedByUser(user);
        medication.setUpdatedByUser(user);
        medication.setCreatedDate(LocalDateTime.now());
        medication.setUpdated_date(LocalDateTime.now());
        medication.setTasks(new HashSet<>());

        return medicationService.getById(medicationService.save(medicationMapper.toRequestDTO(medication)));

    }

    private void createPublicEmergency(User user, String name, String phoneNumber, String description) {
        PublicEmergency publicEmergency = new PublicEmergency();

        publicEmergency.setServiceName(name);
        publicEmergency.setPhoneNumber(phoneNumber);

        publicEmergency.setDescription(description);


        publicEmergency.setCreatedByUser(user);
        publicEmergency.setUpdatedByUser(user);
        publicEmergency.setCreatedDate(LocalDateTime.now());
        publicEmergency.setUpdated_date(LocalDateTime.now());

        publicEmergencyService.save(publicEmergencyMapper.toRequestDTO(publicEmergency));

    }

    public void createPatientContact(User user, Patient patient, String name, String phoneNumber, String affiliation) {
        PatientContact contact = new PatientContact();

        contact.setName(name);
        contact.setPhoneNumber(phoneNumber);
        contact.setAffiliation(affiliation);

        contact.setCreatedByUser(user);
        contact.setUpdatedByUser(user);

        contact.setCreatedDate(LocalDateTime.now());
        contact.setUpdated_date(LocalDateTime.now());

        patientContactService.save(patientContactMapper.toRequestDTO(contact), patient.getId());

    }

    public void createPatientHealth(User user, Patient patient, Integer bloodPressure, Integer heartRate, Integer oximetry, Integer bloodGlucose, BigDecimal temperature) {

        PatientHealth health = new PatientHealth();

        health.setBloodPressure(bloodPressure);
        health.setHeartRate(heartRate);
        health.setOximetry(oximetry);
        health.setBloodGlucose(bloodGlucose);
        health.setTemperature(temperature);

        health.setCreatedByUser(user);
        health.setUpdatedByUser(user);

        health.setCreatedDate(LocalDateTime.now());
        health.setUpdated_date(LocalDateTime.now());

        health.setPatient(patient);

        patientHealthService.save(patientHealthMapper.toRequestDTO(health), patient.getId());
    }

    public void createTask(User user, Patient patient, TaskType taskType, Set<Medication> medications, String describe, LocalDateTime scheduledDate, Integer priority) {

        Task task = new Task();

        task.setDescribe(describe);
        task.setScheduledDate(scheduledDate);
        task.setPriority(priority);

        task.setCreatedByUser(user);
        task.setUpdatedByUser(user);

        task.setCreatedDate(LocalDateTime.now());
        task.setUpdated_date(LocalDateTime.now());

        task.setTaskType(taskType);

        task.setPatient(patient);

        task.setMedications(medications);

        TaskRequestDTO requestDTO = taskMapper.toRequestDTO(task);

        taskService.save(user.getId(), requestDTO);

    }

}
