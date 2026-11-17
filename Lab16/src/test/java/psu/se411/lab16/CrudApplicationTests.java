package psu.se411.lab16;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.jdbc.core.JdbcTemplate;
import psu.se411.lab16.repository.CourseRepository;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
class CrudApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void databaseAndRepositoryAreAvailable() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM course", Integer.class);
        assertTrue(count != null && count >= 0);
        assertTrue(courseRepository.findAll().iterator().hasNext());
    }

    @Test
    void homeIsPublic() throws Exception {
        mvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    void dashboardRequiresLogin() throws Exception {
        mvc.perform(get("/dashboard")).andExpect(status().is3xxRedirection());
    }

    @Test
    void userCanOpenDashboard() throws Exception {
        mvc.perform(get("/dashboard").with(user("usera").roles("USER")))
                .andExpect(status().isOk());
    }

    @Test
    void userCannotOpenAdminDashboard() throws Exception {
        mvc.perform(get("/admindashboard").with(user("usera").roles("USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminCanOpenAdminDashboard() throws Exception {
        mvc.perform(get("/admindashboard").with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    void adminCannotOpenUserDashboard() throws Exception {
        mvc.perform(get("/dashboard").with(user("admin").roles("ADMIN")))
                .andExpect(status().isForbidden());
    }

    @Test
    void apiRequiresAuthentication() throws Exception {
        mvc.perform(get("/api/v1/courses")).andExpect(status().is3xxRedirection());
    }

    @Test
    void userCanAccessCourses() throws Exception {
        mvc.perform(get("/api/v1/courses").with(user("usera").roles("USER")))
                .andExpect(status().isOk());
    }

    @Test
    void formLoginWithCsrfWorks() throws Exception {
        mvc.perform(post("/login")
                    .with(csrf())
                    .param("username", "usera")
                    .param("password", "pwd123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void formLoginWithoutCsrfIsRejected() throws Exception {
        mvc.perform(post("/login")
                    .param("username", "usera")
                    .param("password", "pwd123"))
                .andExpect(status().isForbidden());
    }
    @Test
    void authenticatedCourseCrudWorks() throws Exception {
        MvcResult created = mvc.perform(post("/api/v1/courses")
                        .with(user("usera").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"CS301\",\"name\":\"Test Course\",\"description\":\"Demo\",\"credits\":3}"))
                .andExpect(status().isCreated()).andReturn();
        String path = URI.create(created.getResponse().getHeader("Location")).getPath();
        mvc.perform(get(path).with(user("usera").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("CS301"));
        mvc.perform(put(path).with(user("usera").roles("USER")).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"CS302\",\"name\":\"Updated Course\",\"credits\":4}"))
                .andExpect(status().isNoContent());
        mvc.perform(delete(path).with(user("usera").roles("USER")).with(csrf()))
                .andExpect(status().isNoContent());
        mvc.perform(get(path).with(user("usera").roles("USER")))
                .andExpect(status().isNotFound());
    }
}
