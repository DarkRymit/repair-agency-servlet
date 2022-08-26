package com.epam.finalproject.repository.impl;

import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.data.PageRequest;
import com.epam.finalproject.framework.data.Sort;
import com.epam.finalproject.framework.data.jdbc.JdbcTemplate;
import com.epam.finalproject.framework.data.sql.mapping.SqlEntityMapper;
import com.epam.finalproject.framework.data.sql.mapping.annotation.AnnotationSqlDefinitionReader;
import com.epam.finalproject.framework.data.sql.mapping.translators.*;
import com.epam.finalproject.framework.data.sql.query.SqlEntityQueryGenerator;
import com.epam.finalproject.model.entity.Receipt;
import com.epam.finalproject.model.entity.ReceiptDelivery;
import com.epam.finalproject.model.entity.ReceiptItem;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import com.epam.finalproject.model.search.ReceiptSearch;
import com.epam.finalproject.model.search.ReceiptWithCustomerSearch;
import com.epam.finalproject.model.search.ReceiptWithMasterSearch;
import com.epam.finalproject.support.DatabaseSetupExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DatabaseSetupExtension.class)
class ReceiptRepositorySQLTest {

    static JdbcTemplate template;
    static SqlEntityMapper entityMapper;
    static SqlEntityQueryGenerator queryGenerator;
    static AnnotationSqlDefinitionReader definitionReader;
    ReceiptRepositorySQL receiptRepository;

    @BeforeAll
    static void getResources() {
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
    void setUp() {
        receiptRepository = new ReceiptRepositorySQL(template, entityMapper, queryGenerator, definitionReader);
    }

    @Test
    void findByIdShouldReturnCorrectObjectWhenByExistId() {
        Receipt receipt = receiptRepository.findById(1L).orElseThrow();
        assertEquals("CustomerStriker", receipt.getUser().getUsername());
        assertEquals(ReceiptStatusEnum.PAID, receipt.getStatus().getName());
        assertEquals("MasterStriker", receipt.getMaster().getUsername());

        Set<ReceiptItem> receiptItems = receipt.getItems();
        assertFalse(receiptItems.isEmpty());

        ReceiptDelivery receiptDelivery = receipt.getDelivery();
        assertNotNull(receiptDelivery);

        assertEquals("notebook", receipt.getCategory().getKeyName());
        assertThat(BigDecimal.valueOf(84.9)).isEqualByComparingTo(receipt.getTotalPrice());
        assertEquals("USD", receipt.getPriceCurrency().getCode());
        assertEquals("Typical note", receipt.getNote());
        assertEquals(Instant.parse("2022-01-10T14:23:22Z"), receipt.getCreationDate());

    }
    @Test
    void findByIdShouldReturnEqualObjectWhenMultipleByExistId() {
        Receipt receipt = receiptRepository.findById(1L).orElseThrow();
        Receipt receipt1 = receiptRepository.findById(1L).orElseThrow();
        Receipt receipt2 = receiptRepository.findById(1L).orElseThrow();
        assertEquals(receipt,receipt1);
        assertEquals(receipt1,receipt2);
    }

    @Test
    void findAllShouldReturnNonEmptyListWhenSimpleFindAll() {
        List<Receipt> receipts = receiptRepository.findAll();
        assertFalse(receipts.isEmpty());
    }


    @Test
    void existsByIdShouldReturnTrueWhenExist() {
        assertTrue(receiptRepository.existsById(1L));
    }

    @Test
    void existsByIdShouldReturnTrueWhenNotExist() {
        assertFalse(receiptRepository.existsById(404L));
    }

    @Test
    void countShouldReturnLongGreaterThatOne() {
        assertTrue(receiptRepository.count() > 1);
    }

    @Test
    void findAllShouldReturnListSortedByIdASCWhenSortByIdASC() {
        List<Receipt> receipts = receiptRepository.findAll(Sort.by("id"));
        assertEquals(1, receipts.get(0).getId());
        assertEquals(2, receipts.get(1).getId());
        assertEquals(3, receipts.get(2).getId());
    }

    @Test
    void findAllShouldReturnListSortedByIdDESCWhenSortByIdDESC() {
        List<Receipt> receipts = receiptRepository.findAll(Sort.by("id").descending());
        assertEquals(3, receipts.get(receipts.size() - 3).getId());
        assertEquals(2, receipts.get(receipts.size() - 2).getId());
        assertEquals(1, receipts.get(receipts.size() - 1).getId());
    }

    @Test
    void findAllShouldReturnPageSortedByIdACSWithOffset2Sized2WhenByPageRequest() {
        Page<Receipt> receipts = receiptRepository.findAll(PageRequest.of(1, 2, Sort.by("id")));
        assertEquals(2, receipts.getSize());
        assertEquals(3, receipts.getContent().get(0).getId());
        assertEquals(4, receipts.getContent().get(1).getId());
    }


    @Test
    void findAllShouldReturnCorrectPageWhenByReceiptSearch() {
        PageRequest request = PageRequest.of(0, 2, Sort.by("id"));
        ReceiptSearch search = new ReceiptSearch(request,
                Set.of(ReceiptStatusEnum.PAID, ReceiptStatusEnum.DONE, ReceiptStatusEnum.CANCELED), "Striker",
                "Customer");
        Page<Receipt> receipts = receiptRepository.findAll(search);
        assertEquals(2, receipts.getSize());
        assertEquals(1, receipts.getContent().get(0).getId());
        assertEquals(4, receipts.getContent().get(1).getId());
    }

    @Test
    void findAllShouldReturnCorrectPageWhenByReceiptWithMasterSearch() {
        PageRequest request = PageRequest.of(0, 2, Sort.by("id"));
        ReceiptWithMasterSearch search = new ReceiptWithMasterSearch(request,
                Set.of(ReceiptStatusEnum.PAID, ReceiptStatusEnum.DONE, ReceiptStatusEnum.CANCELED), "Customer",
                User.builder().id(3L).build());
        Page<Receipt> receipts = receiptRepository.findAll(search);
        assertEquals(2, receipts.getSize());
        assertEquals(1, receipts.getContent().get(0).getId());
        assertTrue(receipts.getSize() >= receipts.getContent().size());
    }

    @Test
    void findAllShouldReturnCorrectPageWhenByReceiptWithCustomerSearch() {
        PageRequest request = PageRequest.of(0, 2, Sort.by("id"));
        ReceiptWithCustomerSearch search = new ReceiptWithCustomerSearch(request,
                Set.of(ReceiptStatusEnum.PAID, ReceiptStatusEnum.DONE, ReceiptStatusEnum.CANCELED), "Master",
                User.builder().id(4L).build());
        Page<Receipt> receipts = receiptRepository.findAll(search);
        assertEquals(2, receipts.getSize());
        assertEquals(1, receipts.getContent().get(0).getId());
        assertTrue(receipts.getSize() >= receipts.getContent().size());
    }

    @Test
    void saveDeliveryShouldSaveCorrectDelivery() {
        Receipt receipt = receiptRepository.findById(1L).orElseThrow();
        ReceiptDelivery delivery = receipt.getDelivery();
        delivery.setCity("Lviv");
        receiptRepository.saveDelivery(receipt.getDelivery());
        Receipt afterUpdate = receiptRepository.findById(1L).orElseThrow();
        assertEquals(delivery, afterUpdate.getDelivery());
        assertEquals("Lviv", afterUpdate.getDelivery().getCity());
    }

    @Test
    void deleteDeliveryShouldNotThrowException() {
        Receipt receipt = receiptRepository.findById(1L).orElseThrow();
        assertDoesNotThrow(() -> receiptRepository.deleteDelivery(receipt.getDelivery()));
    }

    @Test
    void saveItemShouldSaveCorrectItem() {
        Receipt receipt = receiptRepository.findById(1L).orElseThrow();
        System.out.println(receipt.getItems());
        ReceiptItem item = receipt.getItems().stream().filter(i->i.getId().equals(1L)).findFirst().orElseThrow();
        BigDecimal newPrice = item.getPriceAmount().add(BigDecimal.TEN);
        item.setPriceAmount(newPrice);
        receiptRepository.saveItem(item);
        Receipt afterUpdate = receiptRepository.findById(1L).orElseThrow();
        System.out.println(afterUpdate.getItems());
        ReceiptItem updatedItem = afterUpdate.getItems().stream().filter(i -> i.getPriceAmount().compareTo(item.getPriceAmount())==0).findFirst().orElseThrow();
        assertEquals(newPrice, updatedItem.getPriceAmount());
    }

    @Test
    void deleteItemShouldNotThrowException() {
        Receipt receipt = receiptRepository.findById(1L).orElseThrow();
        ReceiptItem item = receipt.getItems().stream().findFirst().orElseThrow();
        assertDoesNotThrow(() -> receiptRepository.deleteItem(item));
    }
}