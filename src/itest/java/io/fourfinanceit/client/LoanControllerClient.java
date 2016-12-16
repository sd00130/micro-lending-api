package io.fourfinanceit.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.fourfinanceit.query.LoanDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by cons on 16/12/16.
 */
@Service
public class LoanControllerClient {

    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @Autowired
    public LoanControllerClient(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
        mapper = new ObjectMapper();
    }

    public ResultActions applyToLoanWith(String content) throws Exception {
        return mockMvc.perform(post("/api/loans/apply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
    }

    public ResultActions extendLoan(Long id) throws Exception {
        return mockMvc.perform(get("/api/loans/"+ id +"/extend")
                .contentType(MediaType.APPLICATION_JSON));
    }

    public List<LoanDto> getLoansForCustomer(String customerEmail) throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/api/customers/" + customerEmail + "/loans"))
                .andReturn().getResponse();
        return mapper.readValue(response.getContentAsString(), new TypeReference<List<LoanDto>>(){
        });
    }
}
