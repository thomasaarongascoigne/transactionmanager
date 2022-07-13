package com.gascoigne.thomas.transactionmanager.controller;

import com.gascoigne.thomas.transactionmanager.model.Transaction;
import com.gascoigne.thomas.transactionmanager.service.TransactionService;
import com.gascoigne.thomas.transactionmanager.service.TransactionServiceImpl;
import com.gascoigne.thomas.transactionmanager.service.util.SearchFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    @Autowired
    public TransactionController(TransactionServiceImpl transactionService) {
        this.transactionService = transactionService;
    }

    /**
     *     Should be called by an HTTP client using a POST request with a body in JSON format representing the @Transaction to create. As an example:
     *     http://localhost:8080/transaction/create
     *     {
     *         "type": "instore",
     *         "actor": "Lidl",
     *         "data": {
     *            "key1": "600",
     *            "key2": "109.99",
     *            "key3": "12 months"
     *         }
     *     }
     *
     *     Note that there should not be an id provided for create. This indicates to the server that a new entity should be created.
     * @param transaction
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction newTransaction = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }

    /**
     *     Should be called by an HTTP client using a PUT request with a body in JSON format representing the @Transaction to update. As an example:
     *     http://localhost:8080/transaction/update
     *     {
     *         "id": 5,
     *         "timestamp": "2022-07-04T10:20:37.310+00:00",
     *         "type": "online",
     *         "actor": "Lidl",
     *         "data": {
     *            "key1": "1000",
     *            "key2": "199.99",
     *            "key3": "1 month"
     *         }
     *     }
     *
     *     Note that there should be an id provided for update. This indicates to the server that an existing entity should be updated.
     * @param transaction
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction transaction) {
        Transaction updateTransaction = transactionService.updateTransaction(transaction);
        return new ResponseEntity<>(updateTransaction, HttpStatus.OK);
    }

    /**
     * Should be called by an HTTP client using a DELETE request with no body. As an example:
     *     http://localhost:8080/transaction/delete{id}
     *
     *     Note that there should be an id provided for delete.
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTransactionById(@PathVariable("id") Long id) {
        transactionService.deleteTransactionById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Should be called by an HTTP client using a GET request with no body. As an example:
     *      http://localhost:8080/transaction/all
     *
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.findAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    /**
     *     Should be called by an HTTP client using a GET request with a body in JSON format representing the search values. As an example:
     *     http://localhost:8080/transaction/search
     *     {
     *         "start": "2022-07-04T10:20:37.310+00:00",
     *         "end": "2022-07-12T10:20:37.310+00:00",
     *         "type": "instore",
     *         "actor": "lIDL",
     *         "data": {
     *            "key1": "300",
     *            "key2": "xyz",
     *            "key3": "6 months"
     *         }
     *     }
     *
     * @param searchFields
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<List<Transaction>> searchTransactions(@RequestBody SearchFields searchFields) {
        List<Transaction> transactions = transactionService.searchTransactions(searchFields);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

}
