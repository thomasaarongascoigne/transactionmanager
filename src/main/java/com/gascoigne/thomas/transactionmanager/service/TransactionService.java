package com.gascoigne.thomas.transactionmanager.service;

import com.gascoigne.thomas.transactionmanager.model.Transaction;
import com.gascoigne.thomas.transactionmanager.service.util.SearchFields;

import java.util.List;

public interface TransactionService {

    /**
     * Creates a new @Transaction entity. This should be called with a null id indicating that a new entity should be created.
     * @param transaction the @Transaction entity to create
     * @return
     */
    Transaction createTransaction(Transaction transaction);

    /**
     * Updates an existing @Transaction entity. The entity passed as parameter should have an id indicating that an existing entity
     * should be updated.
     * @param transaction
     * @return
     */
    Transaction updateTransaction(Transaction transaction);


    /**
     * Deletes a @Transaction entity by id.
     * @param id
     */
    void deleteTransactionById(Long id);


    /**
     * Finds all @Transaction entities.
     * @return
     */
    List<Transaction> findAllTransactions();

    /**
     * Searches for @Transaction entities that match the search fields provided as parameter.
     * The search functionality allows 2 timestamp values to be provided, start and end. These allow searching for transactions
     * created after start and before end. All fields of a transaction can be used in the search except the id since
     * this has no business meaning. All values in the data map can be provided as search terms and all must match for a transaction
     * to be returned.
     * @param searchFields
     * @return
     */
    List<Transaction> searchTransactions(SearchFields searchFields);

}
