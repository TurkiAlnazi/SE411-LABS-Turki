package psu.edu.ch06.crud04;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class Crud04ApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void anonymousUserCannotAccessProducts() throws Exception {
        mvc.perform(get("/api/v1/products"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void correctDatabaseCredentialsWork() throws Exception {
        mvc.perform(get("/api/v1/products").with(httpBasic("usera", "pwd123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    void incorrectPasswordIsRejected() throws Exception {
        mvc.perform(get("/api/v1/products").with(httpBasic("usera", "wrong")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void unknownUsernameIsRejected() throws Exception {
        mvc.perform(get("/api/v1/products").with(httpBasic("unknown", "pwd123")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void authenticatedRequestCanCreateProductWithoutCsrf() throws Exception {
        mvc.perform(post("/api/v1/products")
                    .with(httpBasic("usera", "pwd123"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\":\"Test item\",\"description\":\"New item\",\"price\":12.50}"))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void missingAuthenticationCannotCreateProduct() throws Exception {
        mvc.perform(post("/api/v1/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\":\"Test item\",\"price\":12.50}"))
                .andExpect(status().isUnauthorized());
    }


    @Test
    void authenticatedProductCrudWorks() throws Exception {
        MvcResult created = mvc.perform(post("/api/v1/products")
                    .with(httpBasic("usera", "pwd123"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\":\"New keyboard\",\"description\":\"Mechanical\",\"price\":120.0}"))
                .andExpect(status().isCreated()).andReturn();
        String path = URI.create(created.getResponse().getHeader("Location")).getPath();
        mvc.perform(get(path).with(httpBasic("usera", "pwd123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New keyboard"));
        mvc.perform(put(path).with(httpBasic("usera", "pwd123"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\":\"Updated keyboard\",\"description\":\"Mechanical\",\"price\":130.0}"))
                .andExpect(status().isNoContent());
        mvc.perform(delete(path).with(httpBasic("usera", "pwd123")))
                .andExpect(status().isNoContent());
        mvc.perform(get(path).with(httpBasic("usera", "pwd123")))
                .andExpect(status().isNotFound());
    }
}
