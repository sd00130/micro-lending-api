package io.fourfinanceit;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.fourfinanceit.client.LoanControllerClient;
import io.fourfinanceit.query.LoanDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by cons on 16/12/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("itest")
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LoanControllerITest {


    private static String APPLICATION_COMMAND_MAX_AMOUNT = "{\"amount\":\"425\", \"customerEmail\": \"loantaker@fourfinance.it\", \"days\": \"20\"}";
    private static String APPLICATION_COMMAND = "{\"amount\":\"300\", \"customerEmail\": \"loantaker@fourfinance.it\", \"days\": \"20\"}";
    private static String CUSTOMER_EMAIL = "loantaker@fourfinance.it";

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    LoanControllerClient client;

    private ObjectMapper mapper;


    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
        mapper = new ObjectMapper();
    }

    @Test
    public void shouldFailWithMaxAmount() throws Exception {
        client.applyToLoanWith(APPLICATION_COMMAND_MAX_AMOUNT).andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldFailWithMaxAttempt() throws Exception {
        client.applyToLoanWith(APPLICATION_COMMAND).andExpect(status().isCreated());
        client.applyToLoanWith(APPLICATION_COMMAND).andExpect(status().isCreated());
        client.applyToLoanWith(APPLICATION_COMMAND).andExpect(status().isCreated());
        client.applyToLoanWith(APPLICATION_COMMAND).andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldExtendLoan() throws Exception {
        MvcResult result = client.applyToLoanWith(APPLICATION_COMMAND).andExpect(status().isCreated()).andReturn();

        LoanDto loan = mapper.readValue(result.getResponse().getContentAsString(), LoanDto.class);

        client.extendLoan(loan.getId()).andExpect(status().isOk());

        List<LoanDto> loans = client.getLoansForCustomer(CUSTOMER_EMAIL);
        assertThat(loans).hasSize(1);
        assertThat(loans.get(0).getExtensions()).hasSize(1);
    }
}

