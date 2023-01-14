package com.payeshgaran.workflow.service;

import com.payeshgaran.workflow.exceptions.LogicErrorException;
import com.payeshgaran.workflow.model.Person;
import com.payeshgaran.workflow.model.Questionnaire;
import com.payeshgaran.workflow.model.QuestionnaireDto;
import com.payeshgaran.workflow.repository.PersonRepository;
import com.payeshgaran.workflow.repository.QuestionnaireRepository;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.*;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.inject.Inject;
import java.util.*;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService, JavaDelegate {

    @Inject
    private RuntimeService runtimeService;

    @Inject
    private FormService formService;

    @Inject
    private TaskService taskService;

    @Value("${server.port}")
    String port;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;
    @Autowired
    private PersonRepository personRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

    }

    @Override
    public String StartProcess(QuestionnaireDto questionnaireDto) throws InterruptedException {

        if (questionnaireDto == null)
            throw new LogicErrorException("Your new questionnaire is not accepted and valid!\nCheck your parameters again");

        runtimeService.startProcessInstanceByKey("Questionnaire");

        Person person = personRepository.getByPriority(1L);

        Thread.sleep(1000);

        TaskDto thisTask = getLatestTaskByUserId(person.getUserId());

        Questionnaire questionnaire = new Questionnaire();

        questionnaire.setTaskId(thisTask.getId());
        questionnaire.setExecutionId(thisTask.getExecutionId());
        questionnaire.setAssignee(thisTask.getAssignee());

        questionnaire.setMedicalNumber(questionnaireDto.getMedicalNumber());
        questionnaire.setDoctorHelp(questionnaireDto.getDoctorHelp());
        questionnaire.setSurgeryNoInyear(questionnaireDto.getSurgeryNoInyear());
        questionnaire.setHarmPurporst(questionnaireDto.isHarmPurporst());
        questionnaire.setClinicAddress(questionnaireDto.getClinicAddress());
        questionnaire.setClinicTelephone(questionnaireDto.getClinicTelephone());
        questionnaire.setHospitalAddress(questionnaireDto.getHospitalAddress());
        questionnaire.setHospitalTelephone(questionnaireDto.getHospitalTelephone());
        questionnaire.setHomeAddress(questionnaireDto.getHomeAddress());
        questionnaire.setHomeTelephone(questionnaireDto.getHomeTelephone());
        questionnaire.setResident(questionnaireDto.isResident());
        questionnaire.setPlasticSurgeryCover(questionnaireDto.isPlasticSurgeryCover());
        questionnaire.setResidentAmount(questionnaireDto.getResidentAmount());

        questionnaireRepository.save(questionnaire);

        return String.format("Questionnaire Process Started...\nNew task assigned to: \"%s\"\n%s\ntask Id:\n%s", person.getUserId(),thisTask.getName(),thisTask.getId());

    }

    @Override
    public ArrayList<TaskDto> getUserTaskList(String userId) {

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                "http://localhost:" + port + "/engine-rest/task?assignee=" + userId, HttpMethod.GET, null,
                new ParameterizedTypeReference<ArrayList<TaskDto>>() {
                }).getBody();

    }

    @Override
    public TaskDto getLatestTaskByUserId(String userId) {
        ArrayList<TaskDto> taskDtos = getUserTaskList(userId);
        taskDtos.sort(new DateCompare());
        return taskDtos.get(taskDtos.size()-1);
    }

    @Override
    public Iterable<QuestionnaireDto> showUserQuestionnaires(String userId) {

        Iterable<Questionnaire> userQuestionnaires= questionnaireRepository.getUserQuestionnaires(userId);
        List<QuestionnaireDto> userQuestionnairesDto = new ArrayList<>();

        for (Questionnaire questionnaire: userQuestionnaires){
            userQuestionnairesDto.add(new QuestionnaireDto(
                    questionnaire.getMedicalNumber(),
                    questionnaire.getDoctorHelp(),
                    questionnaire.getSurgeryNoInyear(),
                    questionnaire.isHarmPurporst(),
                    questionnaire.getClinicAddress(),
                    questionnaire.getClinicTelephone(),
                    questionnaire.getHospitalAddress(),
                    questionnaire.getHospitalTelephone(),
                    questionnaire.getHomeAddress(),
                    questionnaire.getHomeTelephone(),
                    questionnaire.getHospitalTelephone(),
                    questionnaire.getHomeAddress(),
                    questionnaire.getHomeTelephone(),
                    questionnaire.isResident(),
                    questionnaire.isPlasticSurgeryCover(),
                    questionnaire.getResidentAmount()
            ));
        }
        return userQuestionnairesDto;

    }

    @Override
    public String answerToQuestionnaire(String taskId, String answer) {

        if (( !answer.equals("accept")) && ( !answer.equals("assignment")))
            throw new LogicErrorException(String.format("Your answer: %s is not accepted and valid parameter!",answer));
        if(questionnaireRepository.getQuestionnaireByTaskId(taskId)==null)
            throw new LogicErrorException("The task from this task id not found!: " + taskId);

        String userId= questionnaireRepository.getQuestionnaireByTaskId(taskId).getAssignee();
        Person person = personRepository.getByUserId(userId);
        String priority = person.getPriority().toString();
        String accept="decision"+priority;

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(accept, answer);
        formService.submitTaskForm(taskId, properties);

        String returnMessage="";
        if (answer.equals("accept")){
            returnMessage = "You have agreed to this questionnaire and successfully completed the task.";
        }
        else {
            returnMessage = "You passed this task on to the next person and successfully completed the task.";
        }
        return returnMessage;
    }

    @Override
    public String correctionQuestionnaire(String taskId, QuestionnaireDto questionnaireDto) {

        if (questionnaireDto == null)
            throw new LogicErrorException("Your new questionnaire is not accepted and valid!\nCheck your parameters again");
        if(questionnaireRepository.getQuestionnaireByTaskId(taskId)==null)
            throw new LogicErrorException("The task from task id not found!\n" + taskId);
        String answer = "correction";

        Questionnaire questionnaireOld = questionnaireRepository.getQuestionnaireByTaskId(taskId);
        String userId= questionnaireRepository.getQuestionnaireByTaskId(taskId).getAssignee();
        Person person = personRepository.getByUserId(userId);
        String priority = person.getPriority().toString();
        String accept="decision"+priority;

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(accept, answer);
        formService.submitTaskForm(taskId, properties);

        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setTaskId(taskId);
        questionnaire.setExecutionId("After Correction");
        questionnaire.setAssignee(person.getUserId());

        questionnaire.setMedicalNumber(questionnaireDto.getMedicalNumber());
        questionnaire.setDoctorHelp(questionnaireDto.getDoctorHelp());
        questionnaire.setSurgeryNoInyear(questionnaireDto.getSurgeryNoInyear());
        questionnaire.setHarmPurporst(questionnaireDto.isHarmPurporst());
        questionnaire.setClinicAddress(questionnaireDto.getClinicAddress());
        questionnaire.setClinicTelephone(questionnaireDto.getClinicTelephone());
        questionnaire.setHospitalAddress(questionnaireDto.getHospitalAddress());
        questionnaire.setHospitalTelephone(questionnaireDto.getHospitalTelephone());
        questionnaire.setHomeAddress(questionnaireDto.getHomeAddress());
        questionnaire.setHomeTelephone(questionnaireDto.getHomeTelephone());
        questionnaire.setResident(questionnaireDto.isResident());
        questionnaire.setPlasticSurgeryCover(questionnaireDto.isPlasticSurgeryCover());
        questionnaire.setResidentAmount(questionnaireDto.getResidentAmount());

        questionnaireRepository.save(questionnaire);

        return "You You made a correction on this questionnaire and successfully completed the task\n\nChanged fields:\n"
                +compareQuestionnaires(questionnaireOld,questionnaireDto);
    }

    @Override
    public String compareQuestionnaires(Questionnaire questionnaireOld, QuestionnaireDto questionnaireDto) {

        String message="";

        if (!questionnaireOld.getMedicalNumber().equals(questionnaireDto.getMedicalNumber())){
            message = message.concat(questionnaireOld.getMedicalNumber()+" changed to: \t"+ questionnaireDto.getMedicalNumber()+"\n");
        }
        if (!questionnaireOld.getDoctorHelp().equals(questionnaireDto.getDoctorHelp())){
            message = message.concat(questionnaireOld.getDoctorHelp()+" changed to: \t"+ questionnaireDto.getDoctorHelp()+"\n");
        }
        if ( ! (Objects.equals(questionnaireOld.getSurgeryNoInyear(), questionnaireDto.getSurgeryNoInyear())) ){
            message = message.concat(questionnaireOld.getSurgeryNoInyear().toString()+" changed to: \t"+ questionnaireDto.getSurgeryNoInyear().toString()+"\n");
        }
        if ( ! (  questionnaireOld.isHarmPurporst() == questionnaireDto.isHarmPurporst()  ) ){
            message = message.concat(questionnaireOld.isHarmPurporst()+" changed to: \t"+ questionnaireDto.isHarmPurporst()+"\n");
        }
        if (!questionnaireOld.getClinicAddress().equals(questionnaireDto.getClinicAddress())){
            message = message.concat(questionnaireOld.getClinicAddress()+" changed to: \t"+ questionnaireDto.getClinicAddress()+"\n");
        }
        if (!questionnaireOld.getClinicTelephone().equals(questionnaireDto.getClinicTelephone())){
            message = message.concat(questionnaireOld.getClinicTelephone()+" changed to: \t"+ questionnaireDto.getClinicTelephone()+"\n");
        }
        if (!questionnaireOld.getHospitalAddress().equals(questionnaireDto.getHospitalAddress())){
            message = message.concat(questionnaireOld.getHospitalAddress()+" changed to: \t"+ questionnaireDto.getHospitalAddress()+"\n");
        }
        if (!questionnaireOld.getHospitalTelephone().equals(questionnaireDto.getHospitalTelephone())){
            message = message.concat(questionnaireOld.getHospitalTelephone()+" changed to: \t"+ questionnaireDto.getHospitalTelephone()+"\n");
        }
        if (!questionnaireOld.getHomeAddress().equals(questionnaireDto.getHomeAddress())){
            message = message.concat(questionnaireOld.getHomeAddress()+" changed to: \t"+ questionnaireDto.getHomeAddress()+"\n");
        }
        if (!questionnaireOld.getHomeTelephone().equals(questionnaireDto.getHomeTelephone())){
            message = message.concat(questionnaireOld.getHomeTelephone()+" changed to: \t"+ questionnaireDto.getHomeTelephone()+"\n");
        }
        if (!questionnaireOld.isResident()==(questionnaireDto.isResident())){
            message = message.concat(questionnaireOld.isResident()+" changed to: \t"+ questionnaireDto.isResident()+"\n");
        }
        if (!questionnaireOld.isPlasticSurgeryCover()==(questionnaireDto.isPlasticSurgeryCover())){
            message = message.concat(questionnaireOld.isPlasticSurgeryCover()+" changed to: \t"+ questionnaireDto.isPlasticSurgeryCover()+"\n");
        }
        if (! (Objects.equals(questionnaireOld.getResidentAmount(), questionnaireDto.getResidentAmount()))){
            message = message.concat(questionnaireOld.getResidentAmount()+" changed to: \t"+ questionnaireDto.getResidentAmount()+"\n");
        }
        return message;
    }
}