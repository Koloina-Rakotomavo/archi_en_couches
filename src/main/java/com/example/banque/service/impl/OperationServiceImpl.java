package com.example.banque.service.impl;

import com.example.banque.model.Operation;
import com.example.banque.repository.OperationRepository;
import com.example.banque.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OperationServiceImpl implements OperationService {

    private OperationRepository operationRepository;

    @Autowired
    public OperationServiceImpl(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public List<Operation> getAllOperation() {
        return operationRepository.findAll();
    }

    @Override
    public Operation createOperation(Operation operation) {
        return operationRepository.save(operation);
    }

    @Override
    public Operation getOperationById(Long id) {
        return operationRepository.findById(id).orElseThrow();
    }

    @Override
    public Operation updateOperation(Operation operation) {
        return operationRepository.save(operation);
    }

    @Override
    public void deleteOperationById(Long id) {
        operationRepository.findById(id).orElseThrow();
        operationRepository.deleteById(id);
    }
}
