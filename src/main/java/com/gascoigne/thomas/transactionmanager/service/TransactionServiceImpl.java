package com.gascoigne.thomas.transactionmanager.service;

import com.gascoigne.thomas.transactionmanager.model.Transaction;
import com.gascoigne.thomas.transactionmanager.repository.TransactionRepository;
import com.gascoigne.thomas.transactionmanager.service.util.SearchFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
//@Transactional
public class TransactionServiceImpl implements TransactionService {


    private final TransactionRepository transactionRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(Transaction transaction) {

        if (transaction.getTimestamp() == null) {
            transaction.setTimestamp(new Timestamp(System.currentTimeMillis()));
        }
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void deleteTransactionById(Long id) {
        transactionRepository.deleteById(id);
    }

    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> searchTransactions(SearchFields searchFields) {

        String nativeQuery = "select t.id, t.timestamp, t.type, t.actor from transaction t";
        boolean whereAdded = false;

        //Add timestamps for start and end if provided
        Timestamp start = searchFields.getStart();
        if (start != null) {
            if (!whereAdded) {
                nativeQuery += " where";
                whereAdded = true;
            } else {
                nativeQuery += " and";
            }
            nativeQuery += " t.timestamp >= '" + start + "'";
        }
        Timestamp end = searchFields.getEnd();
        if (end != null) {
            if (!whereAdded) {
                nativeQuery += " where";
                whereAdded = true;
            } else {
                nativeQuery += " and";
            }
            nativeQuery += " t.timestamp <= '" + end + "'";
        }

        //Add type search to query if provided
        String type = searchFields.getType();
        if (type != null && !type.isEmpty()) {
            if (!whereAdded) {
                nativeQuery += " where";
                whereAdded = true;
            } else {
                nativeQuery += " and";
            }
            nativeQuery += " lower(t.type) like '%" + type.toLowerCase() + "%'";
        }

        //Add actor search to query if provided
        String actor = searchFields.getActor();
        if (actor != null && !actor.isEmpty()) {
            if (!whereAdded) {
                nativeQuery += " where";
                whereAdded = true;
            } else {
                nativeQuery += " and";
            }
            nativeQuery += " lower(t.actor) like '%" + actor.toLowerCase() + "%'";
        }

        nativeQuery += ";";
        Query query = entityManager.createNativeQuery(nativeQuery, Transaction.class);
        List<Transaction> resultList = query.getResultList();

        //Now apply search filters on the data for any search terms provided
        List<Transaction> toRemove = new ArrayList<Transaction>();
        for (Transaction transaction : resultList) {
            label :
            for (String key: searchFields.getData().keySet()) {
                String value = searchFields.getData().get(key);
                //Only apply filter if a value is present for the search term
                if (value != null && !value.isEmpty()) {
                    String valueInEntity = transaction.getData().get(key).toLowerCase();
                    if (!valueInEntity.equals(value.toLowerCase()) && !valueInEntity.contains(value.toLowerCase())) {
                        //Add current Transaction to list to remove from results list
                        toRemove.add(transaction);
                        break label; //Current transaction does not match search so move to next directly
                    }
                }
            }
        }
        //Remove the transactions from the results list to return
        resultList.removeAll(toRemove);
        return resultList;
    }



}
