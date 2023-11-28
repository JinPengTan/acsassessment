package com.assessment.acs.services.impl;

import com.assessment.acs.exception.DuplicateResourceException;
import com.assessment.acs.modal.entity.Borrower;
import com.assessment.acs.repository.BorrowerRepository;
import com.assessment.acs.services.BorrowerServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("BorrowerServices")
public class BorrowerServicesImpl implements BorrowerServices {

    private final BorrowerRepository borrowerRepository;

    public BorrowerServicesImpl(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBorrower(String name, String email) {

        if(Boolean.TRUE.equals(isDuplicateBorrowerEmail(email))) {
            throw new DuplicateResourceException("Existing borrower email");
        }

        var borrower = Borrower.builder()
                .name(name)
                .email(email)
                .build();

        borrowerRepository.saveAndFlush(borrower);
    }

    private boolean isDuplicateBorrowerEmail(String email) {

        return borrowerRepository.findByEmail(email).isPresent();

    }

}
