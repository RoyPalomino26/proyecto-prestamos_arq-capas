package dev.pitt.loans.loan_project.services.base.crud;

import dev.pitt.loans.loan_project.services.base.ServiceException;

public interface GenericSaveService<D, R> {

    R save(D d) throws ServiceException;

}
