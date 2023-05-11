package com.coolbeans.babbysfirstrancher.controller;

import MTestUtils.KeyExtractor;
import MTestUtils.ModelDuplicator;
import com.coolbeans.babbysfirstrancher.controller.exceptions.DisinformationException;
import com.coolbeans.babbysfirstrancher.service.MessageService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;

import static MTestUtils.ModelUtils.*;
import static MTestUtils.RandUtils.getRandomFromList;
import static MTestUtils.RandUtils.rand;
import static MTestUtils.WebMVCHelper.JSON_CONTENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import com.coolbeans.babbysfirstrancher.model.Message;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//@SpringBootTest
//@EnableConfigurationProperties
@WebMvcTest(MessageController.class)
public class MessageControllerTests {
    private static final List<Message> DEFAULT_MESSAGE_LIST;
    private static final Map<Integer, Message> DEFAULT_MESSAGE_MAP;
    private static final ModelDuplicator<Message> M_DUPER;
    private static final KeyExtractor<Integer, Message> K_XTRACTOR;
    private static final String ROUTE = "/msg";


    static {//setting up standard data and utils
        M_DUPER = toDup -> new Message(toDup.getId(), toDup.getMessage());
        K_XTRACTOR = Message::getId;
        DEFAULT_MESSAGE_LIST = Arrays.asList(
                new Message(0, "init msg"),
                new Message(1, "2nd msg"),
                new Message(2, "3rd msg"),
                new Message(3, "4th msg"),
                new Message(4, "5th msg"),
                new Message(5, "6th msg"),
                new Message(6, "7th msg"),
                new Message(7, "8th msg")
        );
        DEFAULT_MESSAGE_MAP = convertListToMap(DEFAULT_MESSAGE_LIST, K_XTRACTOR);

    }

    private static List<Message> dupDefList() {
        return duplicateList(DEFAULT_MESSAGE_LIST, M_DUPER);
    }

    private static Map<Integer, Message> dupDefMap() {
        return duplicateMap(DEFAULT_MESSAGE_MAP, M_DUPER, K_XTRACTOR);
    }

    //private static boolean initialized = false;

    @Autowired
    MockMvc mvc;
    @MockBean
    private MessageService subjectsService;


//    @BeforeEach
//    public void setup() {
//        if (!initialized) {
//            //run once logic here
//            initialized = true;
//        }
//
//    }

    @Test
    void testGetAll() throws Exception {
        when(subjectsService.findAll()).thenReturn(dupDefList());
        MvcResult result = mvc.perform(get(ROUTE)).andExpect(status().isOk()).andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualToIgnoringWhitespace(jsonify(dupDefList()));
    }

    @Test
    void testGetById_HappyPath() throws Exception {
        int numChecks = 40;
        Map<Integer, Message> fakeRepo = dupDefMap();
        List<Message> fakeRepoList = dupDefList();

        when(subjectsService.getMessageById(any(Integer.class))).thenAnswer(
                invocation -> {
                    Message found = fakeRepo.get(invocation.getArgument(0, Integer.class));
                    if (null == found) {
                        throw new EntityNotFoundException();
                    }
                    return found;
                }
        );

        for (int i = 0; i < numChecks; i++) {
            Message currTestMsg = getRandomFromList(fakeRepoList);
            MvcResult result = mvc.perform(get(ROUTE + "/{id}", currTestMsg.getId()))
                    .andExpect(status().isOk())
                    .andReturn();
            assertThat(result.getResponse().getContentAsString()).isEqualToIgnoringWhitespace(jsonify(currTestMsg));
        }
    }

    @Test
    void testGetById_NotFoundSadPath() throws Exception {
        when(subjectsService.getMessageById(any(Integer.class))).thenThrow(new EntityNotFoundException());
        mvc.perform(get(ROUTE + "/{id}", rand.nextInt())).andExpect(status().isNotFound());
    }

    @Test
    void testGetById_DisinfoSadPath() throws Exception {
        int numChecks = 40;
        when(subjectsService.getMessageById(any(Integer.class))).thenThrow(new DisinformationException());

        for (int i = 0; i < numChecks; i++) {
            mvc.perform(get(ROUTE + "/{id}", rand.nextInt())).andExpect(status().isUnavailableForLegalReasons());
        }
    }


    @Test
    void testPost_HappyPath() throws Exception {
        List<Message> fakeRepo = dupDefList();
        int newId = fakeRepo.size();
        String newMessage = "howdy im a totally real and not test message";
        Message testMsg = new Message();
        testMsg.setMessage(newMessage);
        Message expectedMessageResult = new Message(newId, newMessage);
        List<Message> expectedRepoResult = duplicateList(fakeRepo, M_DUPER);
        expectedRepoResult.add(expectedMessageResult);
        //when service is told to save it will add to the fakerepo and respond with the created message
        when(subjectsService.saveMessage(any(Message.class))).thenAnswer(
                invocation -> {
                    Message receivedMsg = invocation.getArgument(0, Message.class);
                    receivedMsg.setId(newId);
                    fakeRepo.add(receivedMsg);
                    return receivedMsg;
                }
        );

        MvcResult result = mvc.perform(post(ROUTE)
                .contentType(JSON_CONTENT)
                .content(jsonify(testMsg))
        ).andExpect(status().isOk()).andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualToIgnoringWhitespace(jsonify(expectedMessageResult));
        assertThat(jsonify(fakeRepo)).isEqualToIgnoringWhitespace(jsonify(expectedRepoResult));
    }
    //TODO - testpost sadpaths:
    // with malformed json
    // with ID clash

    @Test
    void testPut_HappyPath() throws Exception {
        Map<Integer, Message> fakeRepo = dupDefMap();
        String updPrefix = "upd";
        int numChecks = 40;

        when(subjectsService.updateMessage(any(Message.class), any(Integer.class))).thenAnswer(
                invocation -> {
                    int idArg = invocation.getArgument(1, Integer.class);
                    fakeRepo.put(idArg, invocation.getArgument(0, Message.class));
                    return fakeRepo.get(idArg);
                }
        );

        for (int i = 0; i < numChecks; i++) {
            Message prototype = getRandomFromList(DEFAULT_MESSAGE_LIST);
            Message updatingModel = M_DUPER.duplicate(prototype);

            int updAttempt = -1;
            while (0 == updatingModel.getMessage().compareTo(prototype.getMessage())) {
                updAttempt++;
                updatingModel.setMessage(updPrefix + updAttempt + prototype.getMessage());
            }

            MvcResult result = mvc.perform(put(ROUTE + "/{id}", prototype.getId())
                    .contentType(JSON_CONTENT)
                    .content(jsonify(updatingModel))
            ).andExpect(status().isOk()).andReturn();

            Message updatedMessage = fakeRepo.get(prototype.getId());

            //verifies the returned result matches what is in the map
            assertThat(result.getResponse().getContentAsString()).isEqualToIgnoringWhitespace(jsonify(updatedMessage));
            //verifies the returned result matches the intended updated data
            assertThat(result.getResponse().getContentAsString()).isEqualToIgnoringWhitespace(jsonify(updatingModel));
        }

    }

    @Test
    void testPut_NotFoundSadPath() throws Exception {
        Message fakeMessage = new Message(0, "Asdf");
        doThrow(EntityNotFoundException.class).when(subjectsService).updateMessage(any(Message.class),anyInt());
        mvc.perform(put(ROUTE + "/{id}",rand.nextInt())
                .contentType(JSON_CONTENT)
                .content(jsonify(fakeMessage))
        ).andExpect(status().isNotFound());
    }


    @Test
    void testDelete_HappyPath() throws Exception {
        final int[] serviceCallCount = {0};
        doAnswer(i -> {
            serviceCallCount[0]++;
            return null;
        }).when(subjectsService).deleteMessageById(anyInt());
        mvc.perform(delete(ROUTE + "/{id}", rand.nextInt())).andExpect(status().isOk());
        assertThat(serviceCallCount[0]).isEqualTo(1);
    }

    @Test
    void testDelete_NotFoundSadPath() throws Exception {
        doThrow(EntityNotFoundException.class).when(subjectsService).deleteMessageById(anyInt());
        mvc.perform(delete(ROUTE + "/{id}", rand.nextInt())).andExpect(status().isNotFound());
    }

}
