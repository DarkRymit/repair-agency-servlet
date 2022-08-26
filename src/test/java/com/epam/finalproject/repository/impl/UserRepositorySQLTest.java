package com.epam.finalproject.repository.impl;

import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.data.PageRequest;
import com.epam.finalproject.framework.data.jdbc.JdbcTemplate;
import com.epam.finalproject.framework.data.sql.mapping.SqlEntityMapper;
import com.epam.finalproject.framework.data.sql.mapping.annotation.AnnotationSqlDefinitionReader;
import com.epam.finalproject.framework.data.sql.mapping.translators.*;
import com.epam.finalproject.framework.data.sql.query.SqlEntityQueryGenerator;
import com.epam.finalproject.model.entity.Role;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import com.epam.finalproject.model.search.MasterSearch;
import com.epam.finalproject.model.search.UserSearch;
import com.epam.finalproject.support.DatabaseSetupExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.sql.DataSource;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DatabaseSetupExtension.class)
class UserRepositorySQLTest {


    static JdbcTemplate template;
    static SqlEntityMapper entityMapper;
    static SqlEntityQueryGenerator queryGenerator;
    static AnnotationSqlDefinitionReader definitionReader;


    UserRepositorySQL userRepository;


    RoleRepositorySQL roleRepository;

    @BeforeAll
    static void setup() {
        DataSource dataSource = DatabaseSetupExtension.getDataSource();
        template = new JdbcTemplate(dataSource);
        definitionReader = new AnnotationSqlDefinitionReader();
        entityMapper = new SqlEntityMapper(definitionReader,
                List.of(new BigDecimalTranslator(), new InstantTranslator(), new LongTranslator(),
                        new IntegerTranslator(), new ReferenceIdTranslator(definitionReader),
                        new SimpleEnumTranslator(), new StringTranslator()));
        queryGenerator = new SqlEntityQueryGenerator(definitionReader);
    }

    @BeforeEach
    void init() {
        userRepository = new UserRepositorySQL(template, entityMapper, queryGenerator, definitionReader);
        roleRepository = new RoleRepositorySQL(template, entityMapper, queryGenerator, definitionReader);
    }

    @Test
    void findAll() {
        List<User> users = userRepository.findAll();
        assertFalse(users.isEmpty());
    }

    @Test
    void findByUsernameShouldReturnCorrectObjectWhenByExistUsername() {
        User user = userRepository.findByUsername("RedStriker").orElseThrow();
        assertEquals("Red", user.getFirstName());
        assertEquals("Strike", user.getLastName());
    }

    @Test
    void findByEmailShouldReturnCorrectObjectWhenByExistEmail() {
        User user = userRepository.findByEmail("redstrike@gmail.com").orElseThrow();
        assertEquals("Red", user.getFirstName());
        assertEquals("Strike", user.getLastName());
    }

    @Test
    void existsByUsernameShouldReturnCorrectObjectWhenByExistId() {
        assertTrue(userRepository.existsByUsername("RedStriker"));
        assertFalse(userRepository.existsByUsername("NotExist"));
    }
    @Test
    void existsByUsernameShouldReturnTrueWhenByExistUsername() {
        assertTrue(userRepository.existsByUsername("RedStriker"));

    }
    @Test
    void existsByUsernameShouldReturnFalseWhenByNonExistUsername() {
        assertFalse(userRepository.existsByUsername("NotExist"));
    }

    @Test
    void existsByEmailShouldReturnTrueWhenByExistEmail() {
        assertTrue(userRepository.existsByEmail("redstrike@gmail.com"));
    }
    @Test
    void existsByEmailShouldReturnFalseWhenByNonExistEmail() {
        assertFalse(userRepository.existsByEmail("notexist@gmail.com"));
    }

    @Test
    void testFindAllShouldReturnCorrectPageWhenByUserSearch() {
        UserSearch userSearch = new UserSearch(PageRequest.of(0,10),"Striker");
        Page<User> users = userRepository.findAll(userSearch);
        assertFalse(users.getContent().isEmpty());
        assertTrue(users.stream().anyMatch(user -> user.getUsername().equals("CustomerStriker")));
        assertTrue(users.stream().anyMatch(user -> user.getUsername().equals("MasterStriker")));
    }

    @Test
    void testFindAllShouldReturnCorrectPageWhenByMasterSearch() {
        MasterSearch masterSearch = new MasterSearch(PageRequest.of(0,10),"Striker");
        Page<User> users = userRepository.findAll(masterSearch);
        assertFalse(users.stream().anyMatch(user -> user.getUsername().equals("CustomerStriker")));
        assertTrue(users.stream().anyMatch(user -> user.getUsername().equals("MasterStriker")));
    }

    @Test
    void saveShouldReturnUserWithCorrectRoleAndUsername() {
        Role role = roleRepository.findByName(RoleEnum.UNVERIFIED).orElseThrow();
        User user = User.builder()
                .username("NoneStriker")
                .email("nonestrike@gmail.com")
                .password("pass3")
                .firstName("None")
                .lastName("Strike")
                .phone("+380 63 108 7167")
                .roles(Set.of(role))
                .wallets(new HashSet<>())
                .creationDate(Instant.now())
                .lastModifiedDate(Instant.now())
                .build();
        userRepository.save(user);
        roleRepository.addRoleForUser(role,user);
        User createdUser = userRepository.findByUsername("NoneStriker").orElseThrow();
        assertNotNull(user.getWallets());
        assertNotNull(user.getRoles());
        assertTrue(user.getRoles().contains(role));
        assertEquals("None", createdUser.getFirstName());
        assertEquals("Strike", createdUser.getLastName());
    }
}