package dev.pitt.loans.loan_project.services.base.crud;

import dev.pitt.loans.loan_project.services.base.ServiceException;

public interface GenericDeleteService<ID> {
    boolean delete(ID id) throws ServiceException;
}
