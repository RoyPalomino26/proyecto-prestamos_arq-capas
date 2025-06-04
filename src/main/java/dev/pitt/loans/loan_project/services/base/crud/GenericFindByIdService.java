package dev.pitt.loans.loan_project.services.base.crud;

import dev.pitt.loans.loan_project.services.base.ServiceException;

public interface GenericFindByIdService<T,ID> {
    T findById(ID id) throws ServiceException;
}
