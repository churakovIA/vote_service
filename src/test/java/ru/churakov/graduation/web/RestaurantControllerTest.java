package ru.churakov.graduation.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.churakov.graduation.model.Menu;
import ru.churakov.graduation.model.Restaurant;
import ru.churakov.graduation.repository.VoteRepository;
import ru.churakov.graduation.service.MenuService;
import ru.churakov.graduation.service.RestaurantService;
import ru.churakov.graduation.to.MenuTo;
import ru.churakov.graduation.web.json.JsonUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.churakov.graduation.TestData.*;
import static ru.churakov.graduation.TestUtil.*;

class RestaurantControllerTest extends AbstractControllerTest {
    private static final String REST_URL = RestaurantController.REST_URL + '/';

    @Autowired
    RestaurantService service;

    @Autowired
    MenuService menuService;

    @Autowired
    VoteRepository voteRepository;

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(service.getAll())
                        .isEqualTo(List.of(RESTAURANT_100003, RESTAURANT_100004, RESTAURANT_100002, RESTAURANT_100005)));

    }

    @Test
    void testGet() throws Exception {
        final int id = RESTAURANT_100003.getId();
        mockMvc.perform(get(REST_URL + id)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(service.get(id)).isEqualTo(RESTAURANT_100003));
    }

    @Test
    void testCreateWithLocation() throws Exception {
        Restaurant created = getCreatedRestaurant();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isCreated());
        Restaurant returned = readFromJsonResultActions(action, Restaurant.class);
        created.setId(returned.getId());

        assertThat(returned).isEqualTo(created);
        assertThat(service.getAll())
                .isEqualTo(List.of(RESTAURANT_100003, created, RESTAURANT_100004, RESTAURANT_100002, RESTAURANT_100005));
    }

    @Test
    void testUpdate() throws Exception {
        Restaurant updated = getUpdatedRestaurant();
        mockMvc.perform(put(REST_URL + RESTAURANT_100003.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        assertThat(service.get(RESTAURANT_100003.getId())).isEqualTo(updated);
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "100005")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThat(service.getAll()).isEqualTo(List.of(RESTAURANT_100003, RESTAURANT_100004, RESTAURANT_100002));
    }

    @Test
    void getMenu() throws Exception {
        mockMvc.perform(get(REST_URL + RESTAURANT_100003.getId() + "/menu")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(readFromJsonMvcResult(result, Menu.class)).isEqualTo(MENU_100009));
    }

    @Test
    void updateMenu() throws Exception {
        MenuTo updated = getUpdatedMenuTo();
        mockMvc.perform(put(REST_URL + RESTAURANT_100003.getId() + "/menu")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
//        assertThat(menuService.getWithRestaurantAndDishes(null, RESTAURANT_100003.getId()).getDishes())
//                .usingElementComparatorIgnoringFields("id", "menu")
//                .isEqualTo(updated.getDishes());
    }

    @Test
    void vote() throws Exception {
        mockMvc.perform(put(REST_URL + RESTAURANT_100003.getId() + "/vote")
                .with(userHttpBasic(USER)))
                .andExpect(status().isCreated());
//        assertThat(voteRepository.findByUserIdAndDateWithMenu(USER_ID, LocalDate.now()).orElse(null))
//                .isEqualToComparingFieldByField(VOTE);
    }

    @Test
    void testGetAllForbidden() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

}